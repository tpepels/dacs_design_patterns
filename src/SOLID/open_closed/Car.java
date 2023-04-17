package SOLID.open_closed;

/*
 * In this example, the NotificationSender class violates the Open/Closed
 * Principle because when we want to add a new notification type, we need to
 * modify the sendNotification method to account for the new type.
 */
class BadNotification {
    public String type;
}

class BadEmailNotification extends BadNotification {
    public String recipient;
    public String subject;
    public String message;

    public BadEmailNotification(String recipient, String subject, String message) {
        this.type = "email";
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }
}

class BadSMSNotification extends BadNotification {
    public String phoneNumber;
    public String text;

    public BadSMSNotification(String phoneNumber, String text) {
        this.type = "sms";
        this.phoneNumber = phoneNumber;
        this.text = text;
    }
}

class BadNotificationSender {
    public void sendNotification(BadNotification notification) {
        if (notification.type.equals("email")) {
            BadEmailNotification email = (BadEmailNotification) notification;
            System.out.println("Sending email to " + email.recipient + " with subject: " + email.subject
                    + " and message: " + email.message);
        } else if (notification.type.equals("sms")) {
            BadSMSNotification sms = (BadSMSNotification) notification;
            System.out.println("Sending SMS to " + sms.phoneNumber + " with text: " + sms.text);
        } else {
            throw new IllegalArgumentException("Invalid notification type");
        }
    }
}

/*
 * In this solution, we define a Notification interface with a send method. Each
 * notification class (EmailNotification, SMSNotification, etc.) implements the
 * Notification interface and provides its own implementation of the send
 * method. The NotificationSender class no longer needs to be modified when
 * adding new notification types, as it simply calls the send method on the
 * provided Notification instance, respecting the Open/Closed Principle.
 */
interface Notification {
    void send();
}

class EmailNotification implements Notification {
    private String recipient;
    private String subject;
    private String message;

    public EmailNotification(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public void send() {
        System.out.println("Sending email to " + recipient + " with subject: " + subject + " and message: " + message);
    }
}

class SMSNotification implements Notification {
    private String phoneNumber;
    private String text;

    public SMSNotification(String phoneNumber, String text) {
        this.phoneNumber = phoneNumber;
        this.text = text;
    }

    @Override
    public void send() {
        System.out.println("Sending SMS to " + phoneNumber + " with text: " + text);
    }
}

class NotificationSender {
    public void sendNotification(Notification notification) {
        notification.send();
    }
}
