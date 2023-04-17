package design_patterns.visitor;

// Define the ConcreteVisitor class
class PayrollCalculator implements PayrollVisitor {
    private double totalPayroll = 0;

    public void visit(Manager manager) {
        totalPayroll += manager.getSalary();
    }

    public void visit(Engineer engineer) {
        totalPayroll += engineer.getSalary() + (1000 * engineer.getYearsOfExperience());
    }

    public double getTotalPayroll() {
        return totalPayroll;
    }

}
