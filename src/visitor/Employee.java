package visitor;

// Define the interface for the Visitable
interface Employee {
    void accept(PayrollVisitor visitor);
}
