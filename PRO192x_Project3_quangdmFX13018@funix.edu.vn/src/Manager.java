/**
 * Manager, as the name suggests, is a manager-type staff
 */
public class Manager extends Staff {
  /**
   * Manager must have a title
   */
  private ManagerTitles title;

  /**
   * Constructor
   */
  public Manager(
    String name,
    int age,
    String joinDate,
    String departmentId,
    int daysOff,
    double salaryMultiplier,
    ManagerTitles title
  ) {
    super(name, age, joinDate, departmentId, daysOff, salaryMultiplier);

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
    String fmtStr = "|%11s|%22s|%4d|%11s|%20s|%9s|%18.1f|%25s|\n";
    System.out.format(
      fmtStr,
      getId(),
      getName(),
      getAge(),
      getJoinDate(),
      getDepartmentId(),
      getDaysOff(),
      getSalaryMultiplier(),
      getTitle().value()
    );
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

  public void displaySalary() {
    String fmtStr = "|%11s|%22s|%4d|%11s|%20s|%9s|%18.1f|%25s|%,25.1f|\n";
    System.out.format(
      fmtStr,
      getId(),
      getName(),
      getAge(),
      getJoinDate(),
      getDepartmentId(),
      getDaysOff(),
      getSalaryMultiplier(),
      getTitle().value(),
      calculateSalary()
    );
  }
}