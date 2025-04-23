package design_patterns.lecture.decorator;

// --- Base Interface and Concrete Notifiers (Assume these exist from Factory example) ---

interface INotifier {
    void send(String userId, String message);
}

class EmailNotifier implements INotifier {
    private final String smtpServer;
    private final int port;

    public EmailNotifier(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
        // System.out.println("Initialized EmailNotifier..."); // Keep output clean for
        // demo
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("--- Sending EMAIL ---");
        System.out.println("To User ID: " + userId + " (Email: " + userId + "@example.com)");
        System.out.println("Body:\n" + message);
        System.out.println("---------------------");
        // Simulate sending
    }
}

class SmsNotifier implements INotifier {
    private final String accountSid;
    private final String authToken;

    public SmsNotifier(String accountSid, String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        // System.out.println("Initialized SmsNotifier..."); // Keep output clean for
        // demo
    }

    @Override
    public void send(String userId, String message) {
        String userPhone = "+1" + String.format("%010d", Math.abs(userId.hashCode()) % 10000000000L);
        System.out.println("--- Sending SMS ---");
        System.out.println("To User ID: " + userId + " (Phone: " + userPhone + ")");
        System.out.println("Message: " + message);
        System.out.println("-------------------");
        // Simulate sending
    }
}

// --- Client Code / Main Application ---

public class PreDecoratorMain {
    public static void main(String[] args) {
        // Basic Notifiers
        INotifier emailNotifier = new EmailNotifier("smtp.example.com", 587);
        INotifier smsNotifier = new SmsNotifier("AC123", "XYZ");

        String userId = "alice";
        String reportMessage = "Your monthly report is ready.";
        String alertMessage = "Server CPU is high!";

        System.out.println("### Sending Basic Notifications ###");
        emailNotifier.send(userId, reportMessage);
        smsNotifier.send(userId, alertMessage);

        // *** PROBLEM AREA START ***
        // How to add features like "High Priority" or "Signature"?
        // Approach 1: Manual modification in client code (BAD)

        System.out.println("\n### Sending with Manual 'Decorations' ###");

        // Send high-priority email with signature
        String highPriorityPrefix = "[HIGH PRIORITY] ";
        String signature = "\n---\nSent by NotificationSystem Inc.";
        String decoratedEmailMessage = highPriorityPrefix + reportMessage + signature;
        System.out.println("Client manually preparing decorated email...");
        emailNotifier.send(userId, decoratedEmailMessage);

        // Send urgent SMS
        String urgentPrefix = "URGENT: ";
        String decoratedSmsMessage = urgentPrefix + alertMessage;
        System.out.println("\nClient manually preparing decorated SMS...");
        smsNotifier.send(userId, decoratedSmsMessage);

        // PROBLEMS:
        // 1. Repetitive code if sending many decorated messages.
        // 2. Logic for decoration is scattered in the client.
        // 3. Adding new decorations (e.g., encryption) means more manual steps
        // everywhere.
        // 4. Doesn't compose well (e.g., priority + signature requires manual
        // concatenation).
        // 5. Violates SRP for the client code doing the sending.
        // *** PROBLEM AREA END ***
    }
}