package architectures.layered.controler;

import architectures.layered.model.User;
import architectures.layered.view.UserView;

public class UserController {
    private User model;
    private UserView view;

    public UserController(User model, UserView view) {
        this.model = model;
        this.view = view;
    }

    public void setUserName(String firstName, String lastName) {
        model.setFirstName(firstName);
        model.setLastName(lastName);
    }

    public String getUserFirstName() {
        return model.getFirstName();
    }

    public String getUserLastName() {
        return model.getLastName();
    }

    public void updateUserView() {
        view.printUserDetails(model.getFirstName(), model.getLastName());
    }
}
