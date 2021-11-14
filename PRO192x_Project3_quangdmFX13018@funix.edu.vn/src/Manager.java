/**
 * Manager, as the name suggests, is a manager-type staff
 */
public class Manager extends Staff {
  /**
   * ManagerTitles is an enum represents different titles that
   * a manager can have
   */
  public enum ManagerTitles {
    BUSINESS_LEADER("Business Leader"),
    PROJECT_LEADER("Project Leader"),
    TECHNICAL_LEADER("Technical Leader");

    private final String value;
    ManagerTitles(String v) {
      this.value = v;
    }

    public String value() {
      return value;
    }
  }
  private final ManagerTitles title;

  public Manager(
    String name,
    int age,
    String joinDate,
    String depart,
    double salaryMultiplier,
    ManagerTitles title
  ) {
    super(name, age, joinDate, depart, salaryMultiplier);

    this.title = title;
  }

  @Override
  public void displayInformation() {
    System.out.println();
    System.out.println("ID: " + getId());
    System.out.println("Name: " + getName());
    System.out.println("Age: " + getAge());
    System.out.println("Join Date: " + getJoinDate());
    System.out.println("Department ID: " + getDepartId());
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