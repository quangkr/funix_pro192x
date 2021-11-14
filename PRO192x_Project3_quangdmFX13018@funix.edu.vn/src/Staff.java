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
   * Basic information of any staff
   */
  private final String id;
  private String name, joinDate, departId;
  private int age;
  private double salaryMultiplier;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getJoinDate() {
    return joinDate;
  }

  public String getDepartId() {
    return departId;
  }

  public int getAge() {
    return age;
  }

  public double getSalaryMultiplier() {
    return salaryMultiplier;
  }

  public Staff(
    String name,
    int age,
    String joinDate,
    String departId,
    double salaryMultiplier
  ) {
    this.name = name;
    this.age = age;
    this.joinDate = joinDate;
    this.departId = departId;
    this.salaryMultiplier = salaryMultiplier;

    // generate an id
    this.id = String.format("S-%08d", sequenceNumber.incrementAndGet());
  }

  public abstract void displayInformation();
}