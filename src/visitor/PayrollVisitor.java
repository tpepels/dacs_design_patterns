package visitor;

// Define the interface for the Visitor
interface PayrollVisitor {
    void visit(Manager manager);

    void visit(Engineer engineer);
}
