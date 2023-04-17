package design_patterns.visitor;

class Engineer implements Employee {
    private String name;
    private double salary;
    private int yearsOfExperience;

    public Engineer(String name, double salary, int yearsOfExperience) {
        this.name = name;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void accept(PayrollVisitor visitor) {
        visitor.visit(this);
    }
}
