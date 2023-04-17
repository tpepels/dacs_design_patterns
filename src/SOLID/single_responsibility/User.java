package SOLID.single_responsibility;

/*
 * In this example, the User class has two responsibilities: adding a user to
 * the database and sending a welcome email to the user. This violates the SRP
 * because the class has more than one reason to change.
 */
class BadUser {

    public void addUser() {
        // Code to add a user to the database
    }

    public void sendWelcomeEmail() {
        // Code to send a welcome email to the user
    }
}

/*
 * To solve this problem, we can create a separate EmailService class that is
 * responsible for sending welcome emails. The User class is now only
 * responsible for adding users to the database, and the EmailService class is
 * responsible for sending welcome emails. This ensures that each class has only
 * one reason to change.
 */

class User {
    public void addUser() {
        // Code to add a user to the database
    }
}

class EmailService {
    public void sendWelcomeEmail() {
        // Code to send a welcome email to the user
    }
}
