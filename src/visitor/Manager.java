package visitor;

// Define the ConcreteVisitable classes
class Manager implements Employee {
    private String name;
    private double salary;

    public Manager(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void accept(PayrollVisitor visitor) {
        visitor.visit(this);
    }
}