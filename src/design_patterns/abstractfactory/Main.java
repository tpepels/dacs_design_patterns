package design_patterns.abstractfactory;

// Example usage
public class Main {
    public static void main(String[] args) {
        WidgetFactory factory1 = new WindowsWidgetFactory();
        Button button1 = factory1.createButton();
        button1.paint();
        TextBox textBox1 = factory1.createTextBox();
        textBox1.paint();

        WidgetFactory factory2 = new MacWidgetFactory();
        Button button2 = factory2.createButton();
        button2.paint();
        TextBox textBox2 = factory2.createTextBox();
        textBox2.paint();
    }
}