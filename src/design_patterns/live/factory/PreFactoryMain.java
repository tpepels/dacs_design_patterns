package design_patterns.live.factory;

import java.util.Objects;

// --- Interfaces and Concrete "Product" Classes ---

/**
 * The Product interface declares the operations that all concrete products must
 * implement.
 */
interface INotifier {
    void send(String userId, String message);
}

/**
 * Concrete Products provide various implementations of the Product interface.
 */
class EmailNotifier implements INotifier {
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
        // Simulate finding user's email address
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
    private final String accountSid;
    private final String authToken; // Be careful with real tokens!

    public SmsNotifier(String accountSid, String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        System.out.println("Initialized SmsNotifier (Account SID: " + this.accountSid + ")");
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("--- Sending SMS ---");
        System.out.println("To User ID: " + userId);
        // Simulate finding user's phone number
        String userPhone = "+1" + String.format("%010d", Math.abs(userId.hashCode()) % 10000000000L); // Generate fake
                                                                                                      // number
        System.out.println("Resolved Phone: " + userPhone);
        System.out.println("Message: " + message);
        System.out.println("Authenticating with SID " + accountSid + "...");
        System.out.println("SMS SENT successfully to " + userPhone + ".");
        System.out.println("-------------------");
    }
}

// --- Client Code ---

/**
 * The Client code works with an instance of a concrete notifier, albeit through
 * its base interface. As long as the client keeps working with the notifier via
 * the base interface, you can pass it any notifier's subclass.
 * BUT, the client currently IS responsible for CREATING the concrete notifier.
 */
class NotificationService {

    // *** PROBLEM AREA START ***
    // This method handles the creation logic based on the type.
    public void sendNotification(String notificationType, String userId, String message) {
        INotifier notifier = null;

        // PROBLEM 1: Tightly coupled to concrete Notifier classes (EmailNotifier,
        // SmsNotifier).
        // PROBLEM 2: Violates Open/Closed Principle (OCP). Adding a new type (e.g.,
        // Push)
        // requires modifying this `if/else if` block.
        // PROBLEM 3: Complex instantiation logic might clutter this method.
        // Fetching config (API keys, server addresses) is mixed with the service's core
        // logic.
        if (Objects.equals(notificationType, "EMAIL")) {
            // Simulate fetching configuration
            String smtpServer = System.getProperty("smtp.server", "smtp.example.com");
            int port = Integer.parseInt(System.getProperty("smtp.port", "587"));
            notifier = new EmailNotifier(smtpServer, port);

        } else if (Objects.equals(notificationType, "SMS")) {
            // Simulate fetching configuration (use dummy values for demo)
            String sid = System.getenv("SMS_ACCOUNT_SID"); // Often from environment variables
            String token = System.getenv("SMS_AUTH_TOKEN");
            if (sid == null)
                sid = "ACxxxxxxxx_DEFAULT_SID";
            if (token == null)
                token = "yyyyyyyyy_DEFAULT_TOKEN";
            notifier = new SmsNotifier(sid, token);

        }
        // To add PUSH notification, we would need another 'else if' block HERE.
        // else if (Objects.equals(notificationType, "PUSH")) {
        // String apiKey = System.getenv("PUSH_API_KEY");
        // notifier = new PushNotifier(apiKey); // Assuming PushNotifier exists
        // }
        else {
            System.err.println("ERROR: Unsupported notification type: " + notificationType);
            // In a real app, might throw an IllegalArgumentException
        }

        // *** PROBLEM AREA END ***

        // Use the created notifier
        if (notifier != null) {
            System.out.println("\nAttempting to send " + notificationType + " notification...");
            try {
                notifier.send(userId, message);
                System.out.println("Notification attempt complete.");
            } catch (Exception e) {
                System.err.println("Failed to send notification: " + e.getMessage());
                // Handle exception appropriately
            }
        } else {
            System.out.println("No notifier created for type: " + notificationType);
        }
    }
}

// --- Main Application / Usage ---
public class PreFactoryMain {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        String user1 = "alice";
        String user2 = "bob";
        String user3 = "charlie";

        service.sendNotification("EMAIL", user1, "Your monthly invoice is available.");
        service.sendNotification("SMS", user2, "Alert: Your server load is high!");
        service.sendNotification("PUSH", user3, "This notification type is not supported yet."); // Example of
                                                                                                 // unsupported type

        // Example showing config might change (though not dynamically in this simple
        // version)
        // System.setProperty("smtp.server", "smtp.internal.example.com");
        // service.sendNotification("EMAIL", user3, "Internal system update.");

    }
}