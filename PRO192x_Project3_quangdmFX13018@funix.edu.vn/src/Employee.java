/**
 * Employee is an ordinary staff
 */
public class Employee extends Staff {
  /**
   * Ordinary employees may have extra hours
   */
  private double extraHours;

  /**
   * Constructor
   */
  public Employee(
    String name,
    int age,
    String joinDate,
    String departmentId,
    int daysOff,
    double salaryMultiplier,
    double extraHours
  ) {
    super(name, age, joinDate, departmentId, daysOff, salaryMultiplier);

    this.extraHours = extraHours;
  }

  /**
   * getter
   */
  public double getExtraHours() {
    return extraHours;
  }

  /**
   * Implement super's abstract method
   */
  @Override
  public void displayInformation() {
    String fmtStr = "|%11s|%22s|%4d|%11s|%20s|%9s|%18.1f|%25.1f|\n";
    System.out.format(
      fmtStr,
      getId(),
      getName(),
      getAge(),
      getJoinDate(),
      getDepartmentId(),
      getDaysOff(),
      getSalaryMultiplier(),
      getExtraHours()
    );
  }

  /**
   * Implement ICalculator interface
   */
  @Override
  public double calculateSalary() {
    return (getSalaryMultiplier() * 3000000.0) + (getExtraHours() * 200000.0);
  }

  public void displaySalary() {
    String fmtStr = "|%11s|%22s|%4d|%11s|%20s|%9s|%18.1f|%25.1f|%,25.1f|\n";
    System.out.format(
      fmtStr,
      getId(),
      getName(),
      getAge(),
      getJoinDate(),
      getDepartmentId(),
      getDaysOff(),
      getSalaryMultiplier(),
      getExtraHours(),
      calculateSalary()
    );
  }
}