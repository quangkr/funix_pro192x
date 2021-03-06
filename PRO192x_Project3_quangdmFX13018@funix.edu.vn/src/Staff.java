import java.util.concurrent.atomic.AtomicLong;

/**
 * Staff is an abstract class represent every kind of staff
 */
public abstract class Staff implements ICalculator {
  /**
   * This is used to generate staff ID
   */
  private final static AtomicLong sequenceNumber = new AtomicLong();

  /**
   * Basic information of a staff
   */
  private final String id;
  private String name;
  private String joinDate;
  private String departmentId;
  private int age;
  private int daysOff;
  private double salaryMultiplier;

  /**
   * Basic getters
   */
  public String getId() { return id; }
  public String getName() { return name; }
  public String getJoinDate() { return joinDate; }
  public String getDepartmentId() { return departmentId; }
  public int getAge() { return age; }
  public int getDaysOff() { return daysOff; }
  public double getSalaryMultiplier() { return salaryMultiplier; }

 /**
  * Constructor
  */
  public Staff(
    String name,
    int age,
    String joinDate,
    String departmentId,
    int daysOff,
    double salaryMultiplier
  ) {
    this.name = name;
    this.age = age;
    this.joinDate = joinDate;
    this.departmentId = departmentId;
    this.daysOff = daysOff;
    this.salaryMultiplier = salaryMultiplier;

    // generate an id
    this.id = String.format("S-%08d", sequenceNumber.incrementAndGet());
  }

  /**
   * Print staff information to the output
   */
  public abstract void displayInformation();
}