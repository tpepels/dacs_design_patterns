package design_patterns.abstractfactory;

// Define the abstract factory interface
public interface WidgetFactory {
    Button createButton();

    TextBox createTextBox();
}