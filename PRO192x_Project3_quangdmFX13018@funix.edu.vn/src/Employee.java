/**
 * Employee is an ordinary staff
 */
public class Employee extends Staff {
  /**
   * Ordinary employees may have extra hours
   */
  int extraHours;

  public Employee(
    String name,
    int age,
    String joinDate,
    String departmentId,
    double salaryMultiplier,
    int extraHours
  ) {
    super(name, age, joinDate, departmentId, salaryMultiplier);

    this.extraHours = extraHours;
  }

  public int getExtraHours() {
    return extraHours;
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
  }

  @Override
  public double calculateSalary() {
    return (getSalaryMultiplier() * 3000000.0) + (getExtraHours() * 200000.0);
  }
}