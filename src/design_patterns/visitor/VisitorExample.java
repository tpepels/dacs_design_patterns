package design_patterns.visitor;

// Example usage
public class VisitorExample {
    public static void main(String[] args) {
        Manager manager = new Manager("John Doe", 50000);
        Engineer engineer = new Engineer("Jane Smith", 60000, 3);

        PayrollCalculator payrollCalculator = new PayrollCalculator();

        manager.accept(payrollCalculator);
        engineer.accept(payrollCalculator);

        System.out.println("Total payroll: $" + payrollCalculator.getTotalPayroll());
    }
}
