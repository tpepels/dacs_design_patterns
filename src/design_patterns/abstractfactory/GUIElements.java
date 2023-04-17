package design_patterns.abstractfactory;

// Define the abstract product interface for buttons
interface Button {
    void paint();
}

// Define concrete button implementations for Windows and Mac
class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting a Windows button.");
    }
}

class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting a Mac button.");
    }
}

// Define the abstract product interface for text boxes
interface TextBox {
    void paint();
}

// Define concrete text box implementations for Windows and Mac
class WindowsTextBox implements TextBox {
    @Override
    public void paint() {
        System.out.println("Painting a Windows text box.");
    }
}

class MacTextBox implements TextBox {
    @Override
    public void paint() {
        System.out.println("Painting a Mac text box.");
    }
}
