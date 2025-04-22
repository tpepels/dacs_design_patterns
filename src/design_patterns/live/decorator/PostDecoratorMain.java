package design_patterns.live.decorator;

// --- Base Interface and Concrete Notifiers (Same as Pre-Pattern) ---

interface INotifier {
    void send(String userId, String message);
}

class EmailNotifier implements INotifier {
    // ... (Implementation remains exactly the same as Pre-Pattern) ...
    private final String smtpServer;
    private final int port;

    public EmailNotifier(String s, int p) {
        this.smtpServer = s;
        this.port = p;
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("--- Sending EMAIL ---");
        System.out.println("To User ID: " + userId + " (Email: " + userId + "@example.com)");
        System.out.println("Body:\n" + message);
        System.out.println("---------------------");
    }
}

class SmsNotifier implements INotifier {
    // ... (Implementation remains exactly the same as Pre-Pattern) ...
    private final String accountSid;
    private final String authToken;

    public SmsNotifier(String s, String t) {
        this.accountSid = s;
        this.authToken = t;
    }

    @Override
    public void send(String userId, String message) {
        String userPhone = "+1" + String.format("%010d", Math.abs(userId.hashCode()) % 10000000000L);
        System.out.println("--- Sending SMS ---");
        System.out.println("To User ID: " + userId + " (Phone: " + userPhone + ")");
        System.out.println("Message: " + message);
        System.out.println("-------------------");
    }
}

// --- Decorator Pattern Implementation ---

/**
 * The base Decorator class follows the same interface as the components it
 * decorates.
 * The primary purpose of this class is to define the wrapping interface for all
 * concrete decorators. The default implementation of the wrapping code might
 * include
 * a field for storing a wrapped component and the means to initialize it.
 */
abstract class NotifierDecorator implements INotifier {
    protected INotifier wrappedNotifier; // The component being decorated

    public NotifierDecorator(INotifier notifier) {
        this.wrappedNotifier = notifier;
    }

    /**
     * The Decorator delegates work to the wrapped component.
     * Concrete decorators will override this method to add their behavior.
     */
    @Override
    public void send(String userId, String message) {
        wrappedNotifier.send(userId, message); // Default behavior is just delegation
    }
}

/**
 * Concrete Decorator: Adds a high-priority marker to the message.
 */
class PriorityNotifierDecorator extends NotifierDecorator {

    public PriorityNotifierDecorator(INotifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String userId, String message) {
        String priorityMessage = "[HIGH PRIORITY] " + message;
        System.out.println("PriorityDecorator: Adding priority marker.");
        // Delegate to the wrapped notifier with the modified message
        wrappedNotifier.send(userId, priorityMessage);
    }
}

/**
 * Concrete Decorator: Adds a standard signature to the message.
 */
class SignatureNotifierDecorator extends NotifierDecorator {
    private final String signature = "\n---\nSent by NotificationSystem Inc.";

    public SignatureNotifierDecorator(INotifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String userId, String message) {
        String signedMessage = message + signature;
        System.out.println("SignatureDecorator: Appending signature.");
        // Delegate to the wrapped notifier with the modified message
        wrappedNotifier.send(userId, signedMessage);
    }
}

/**
 * Concrete Decorator: Adds a specific prefix (e.g., for SMS alerts).
 */
class PrefixNotifierDecorator extends NotifierDecorator {
    private final String prefix;

    public PrefixNotifierDecorator(INotifier notifier, String prefix) {
        super(notifier);
        this.prefix = prefix;
    }

    @Override
    public void send(String userId, String message) {
        String prefixedMessage = prefix + message;
        System.out.println("PrefixDecorator: Adding prefix '" + prefix + "'.");
        // Delegate to the wrapped notifier with the modified message
        wrappedNotifier.send(userId, prefixedMessage);
    }
}

// --- Client Code / Main Application (Using Decorators) ---

public class PostDecoratorMain {
    public static void main(String[] args) {
        // Base Notifiers (could also come from a Factory)
        INotifier basicEmail = new EmailNotifier("smtp.example.com", 587);
        INotifier basicSms = new SmsNotifier("AC123", "XYZ");

        String userId = "bob";
        String reportMessage = "Your Q3 financial report is attached.";
        String alertMessage = "Database connection lost!";

        System.out.println("### Sending Decorated Notifications ###");

        // 1. Send a high-priority email
        System.out.println("\n--- Decorating Email with Priority ---");
        INotifier priorityEmail = new PriorityNotifierDecorator(basicEmail);
        priorityEmail.send(userId, reportMessage);

        // 2. Send an urgent SMS (using Prefix decorator)
        System.out.println("\n--- Decorating SMS with URGENT Prefix ---");
        INotifier urgentSms = new PrefixNotifierDecorator(basicSms, "URGENT: ");
        urgentSms.send(userId, alertMessage);

        // 3. Send an email that is BOTH high-priority AND has a signature
        // Demonstrates composition by wrapping decorators!
        System.out.println("\n--- Decorating Email with Priority AND Signature ---");
        INotifier prioritySignedEmail = new SignatureNotifierDecorator( // Outer wrapper
                new PriorityNotifierDecorator( // Inner wrapper
                        basicEmail // Core component
                ));
        // The call looks the same to the client, regardless of decoration layers
        prioritySignedEmail.send(userId, reportMessage);

        // 4. Send an SMS that is URGENT and (hypothetically) has a signature
        // Shows flexibility - can apply decorators to different base types
        System.out.println("\n--- Decorating SMS with URGENT Prefix AND Signature ---");
        INotifier urgentSignedSms = new SignatureNotifierDecorator(
                new PrefixNotifierDecorator(basicSms, "URGENT: "));
        urgentSignedSms.send(userId, alertMessage);
    }
}
