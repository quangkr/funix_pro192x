/**
 * Manager, as the name suggests, is a manager-type staff
 */
public class Manager extends Staff {
  private final ManagerTitles title;

  public Manager(
    String name,
    int age,
    String joinDate,
    String departmentId,
    double salaryMultiplier,
    ManagerTitles title
  ) {
    super(name, age, joinDate, departmentId, salaryMultiplier);

    this.title = title;
  }

  @Override
  public void displayInformation() {
    System.out.println();
    System.out.println("ID: " + getId());
    System.out.println("Name: " + getName());
    System.out.println("Age: " + getAge());
    System.out.println("Join Date: " + getJoinDate());
    System.out.println("Department ID: " + getDepartmentId());
    System.out.println("Salary multiplier: " + getSalaryMultiplier());
    System.out.println("Title: " + getTitle());
  }

  public ManagerTitles getTitle() {
    return title;
  }

  @Override
  public double calculateSalary() {
    int extraSalary = switch (this.title) {
      case BUSINESS_LEADER -> 8000000;
      case PROJECT_LEADER -> 5000000;
      case TECHNICAL_LEADER -> 6000000;
    };

    return (getSalaryMultiplier() * 5000000.0) + extraSalary;
  }
}