import java.util.concurrent.atomic.AtomicLong;

/**
 * This represents a department
 */
public class Department {
  /**
   * This is used to generate department ID
   */
  private final static AtomicLong sequenceNumber = new AtomicLong();

  /**
   * Basic information of a department
   */
  private final String id;
  private String name;
  private int numberOfStaff;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getNumberOfStaff() {
    return numberOfStaff;
  }

  public void incrementNumberOfStaff() { this.numberOfStaff++; }
  public void decrementNumberOfStaff() { this.numberOfStaff--; }

  /**
   * Department's generic constructor, set to private for now
   */
  private Department(
    String name,
    int numberOfStaff
  ) {
    this.name = name;
    this.numberOfStaff = numberOfStaff;

    // generate an id
    this.id = String.format("D-%03d", sequenceNumber.incrementAndGet());
  }

  /**
   * Department's public constructor, which only accept name parameter
   */
  public Department(String name) {
    this(name, 0);
  }

  public String toString() {
    return (
      "Department name: "
      + getName()
      + ", number of staffs: "
      + getNumberOfStaff()
    );
  }
}