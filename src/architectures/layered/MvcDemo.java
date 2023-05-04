package architectures.layered;

import architectures.layered.controler.UserController;
import architectures.layered.model.User;
import architectures.layered.view.UserView;

/**
 * User represents the Model, which stores the data and state of the
 * application.
 * UserView represents the View, which is responsible for displaying the user
 * data to the user.
 * UserController represents the Controller, which manages the communication
 * between the Model and View. It updates the Model with new data and instructs
 * the View to display the updated data.
 */
public class MvcDemo {
    public static void main(String[] args) {
        // Create a User model
        User model = new User("John", "Doe");

        // Create a UserView
        UserView view = new UserView();

        // Create a UserController and associate the model and view
        UserController controller = new UserController(model, view);

        // Display the initial user information
        controller.updateUserView();

        // Update the user information through the controller
        controller.setUserName("Jane", "Doe");

        // Display the updated user information
        controller.updateUserView();
    }
}
