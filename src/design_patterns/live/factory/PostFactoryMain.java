package design_patterns.live.factory;

import java.util.Objects;

// --- Interfaces and Concrete "Product" Classes (Same as before) ---

interface INotifier {
    void send(String userId, String message);
}

class EmailNotifier implements INotifier {
    // ... (Implementation remains exactly the same as above) ...
    private final String smtpServer;
    private final int port;

    public EmailNotifier(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
        System.out.println("Initialized EmailNotifier (Server: " + this.smtpServer + ":" + this.port + ")");
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("--- Sending EMAIL ---");
        System.out.println("To User ID: " + userId);
        String userEmail = userId + "@example.com";
        System.out.println("Resolved Email: " + userEmail);
        System.out.println("Subject: Notification");
        System.out.println("Body: " + message);
        System.out.println("Connecting to " + smtpServer + "...");
        System.out.println("Email SENT successfully to " + userEmail + ".");
        System.out.println("---------------------");
    }
}

class SmsNotifier implements INotifier {
    // ... (Implementation remains exactly the same as above) ...
    private final String accountSid;
    private final String authToken;

    public SmsNotifier(String accountSid, String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        System.out.println("Initialized SmsNotifier (Account SID: " + this.accountSid + ")");
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("--- Sending SMS ---");
        System.out.println("To User ID: " + userId);
        String userPhone = "+1" + String.format("%010d", Math.abs(userId.hashCode()) % 10000000000L);
        System.out.println("Resolved Phone: " + userPhone);
        System.out.println("Message: " + message);
        System.out.println("Authenticating with SID " + accountSid + "...");
        System.out.println("SMS SENT successfully to " + userPhone + ".");
        System.out.println("-------------------");
    }
}

// --- The Factory ---

/**
 * The Simple Factory class encapsulates the object creation logic.
 * It isolates the client from concrete product classes.
 */
class NotifierFactory {

    /**
     * The factory method. It hides the instantiation logic from the client.
     * It's responsible for creating the correct Notifier based on the type
     * and handling the specific configuration needed for each type.
     *
     * @param notificationType The type of notifier requested (e.g., "EMAIL",
     *                         "SMS").
     * @return An instance of INotifier, or null/throws exception if type is
     *         unsupported.
     */
    public static INotifier createNotifier(String notificationType) {
        if (notificationType == null || notificationType.isEmpty()) {
            return null; // Or throw IllegalArgumentException("Notification type cannot be empty");
        }

        switch (notificationType.toUpperCase()) {
            case "EMAIL":
                // Configuration fetching is now centralized here
                String smtpServer = System.getProperty("smtp.server", "smtp.example.com");
                int port = Integer.parseInt(System.getProperty("smtp.port", "587"));
                return new EmailNotifier(smtpServer, port);

            case "SMS":
                // Configuration fetching is now centralized here
                String sid = System.getenv("SMS_ACCOUNT_SID");
                String token = System.getenv("SMS_AUTH_TOKEN");
                if (sid == null)
                    sid = "ACxxxxxxxx_DEFAULT_SID";
                if (token == null)
                    token = "yyyyyyyyy_DEFAULT_TOKEN";
                return new SmsNotifier(sid, token);

            // *** EXTENSION POINT ***
            // To add PUSH notification:
            // 1. Create PushNotifier implements INotifier.
            // 2. Add a case here:
            // case "PUSH":
            // String apiKey = System.getenv("PUSH_API_KEY");
            // if (apiKey == null) apiKey = "zzzzzzzzz_DEFAULT_KEY";
            // return new PushNotifier(apiKey); // Assuming PushNotifier exists

            default:
                // Option 1: Return null
                System.err.println("WARN: Unsupported notification type in Factory: " + notificationType);
                return null;
            // Option 2: Throw an exception
            // throw new IllegalArgumentException("Unknown notification type: " +
            // notificationType);
        }
    }
}

// --- Client Code (Refactored) ---

/**
 * The Client code now uses the factory to create notifier objects.
 * It is decoupled from the concrete product classes.
 */
class NotificationServiceRefactored {

    // The service now depends on the Factory, not the concrete notifiers.
    public void sendNotification(String notificationType, String userId, String message) {

        // *** CREATION LOGIC DELEGATED TO THE FACTORY ***
        INotifier notifier = NotifierFactory.createNotifier(notificationType);
        // *** PROBLEM AREA REMOVED ***

        // Use the created notifier (same logic as before)
        if (notifier != null) {
            System.out
                    .println("\nAttempting to send " + notificationType.toUpperCase() + " notification via Factory...");
            try {
                notifier.send(userId, message);
                System.out.println("Notification attempt complete.");
            } catch (Exception e) {
                System.err.println("Failed to send notification: " + e.getMessage());
                // Handle exception appropriately
            }
        } else {
            System.out.println("No notifier created by Factory for type: " + notificationType);
        }

        // Benefits Demonstrated by Refactoring (Recap):
        // 1. Decoupling: NotificationServiceRefactored no longer knows about
        // EmailNotifier or SmsNotifier. It only knows the INotifier interface and the
        // NotifierFactory.
        //
        // 2. Open/Closed Principle: To add a new notification type (PushNotifier), you
        // create the new class and modify only the NotifierFactory. The
        // NotificationServiceRefactored class remains closed for modification.
        //
        // 3. Single Responsibility Principle: NotifierFactory now has the single
        // responsibility of creating notifiers. NotificationServiceRefactored focuses
        // on the process of sending notifications (get notifier, use notifier).
        //
        // 4. Centralization: All logic related to which concrete class to instantiate
        // and how to configure it is centralized in NotifierFactory, making it easier
        // to manage, update, and test.
    }
}

// --- Main Application / Usage (Using Refactored Service) ---
public class PostFactoryMain {
    public static void main(String[] args) {
        // Now uses the refactored service
        NotificationServiceRefactored service = new NotificationServiceRefactored();

        String user1 = "alice";
        String user2 = "bob";
        String user3 = "charlie";
        String user4 = "david"; // For push example

        service.sendNotification("EMAIL", user1, "Your monthly invoice is available.");
        service.sendNotification("SMS", user2, "Alert: Your server load is high!");

        // Still indicates unsupported type, but the handling is inside the factory
        service.sendNotification("PUSH", user3, "This notification type is not supported yet.");

        // If we were to add PushNotifier and update the Factory, this call would
        // start working without ANY changes to NotificationServiceRefactored.
        // service.sendNotification("PUSH", user4, "New feature released!");
    }
}
