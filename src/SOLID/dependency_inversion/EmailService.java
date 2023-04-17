package SOLID.dependency_inversion;

/*
 * In this example, the UserService class depends on the EmailService class to
 * send welcome messages to new users. However, let's suppose that the EmailService is tightly
 * coupled to an external library and may change frequently, causing the
 * UserService to break. Moreover, we are now assuming that users will only be sent e-mails and that this 
 * will never change.
 */

class BadEmailService {
    public void sendEmail(String message, String recipient) {
        // send email using external library
    }
}

class BadUserService {
    private BadEmailService emailService;

    public BadUserService() {
        emailService = new BadEmailService();
    }

    public void registerUser(String name, String email) {
        // register user
        String welcomeMessage = "Welcome " + name + "!";
        emailService.sendEmail(welcomeMessage, email);
    }
}

/*
 * To solve this problem, we can use an interface MessageService to define the
 * sendMessage() method and have the EmailService implement it. We can then
 * inject the MessageService into the UserService constructor, allowing for
 * easier testing and replacing of the implementation.
 */
interface MessageService {
    void sendMessage(String message, String recipient);
}

class EmailService implements MessageService {
    public void sendMessage(String message, String recipient) {
        // send email using external library
    }
}

class UserService {
    private MessageService messageService;

    public UserService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void registerUser(String name, String email) {
        // register user
        String welcomeMessage = "Welcome " + name + "!";
        messageService.sendMessage(welcomeMessage, email);
    }
}
