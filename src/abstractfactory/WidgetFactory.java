package abstractfactory;

// Define the abstract factory interface
public interface WidgetFactory {
    Button createButton();

    TextBox createTextBox();
}