/**
 * Manager, as the name suggests, is a manager-type staff
 */
public class Manager extends Staff {
  /**
   * Manager must have a title
   */
  private final ManagerTitles title;

  /**
   * Constructor
   */
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

  /**
   * getter
   */
  public ManagerTitles getTitle() {
    return title;
  }

  /**
   * Implement super's abstract method
   */
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


  /**
   * Implement ICalculator interface
   */
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