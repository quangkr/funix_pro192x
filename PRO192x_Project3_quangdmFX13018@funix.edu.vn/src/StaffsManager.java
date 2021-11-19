import java.util.*;

/**
 * This class is responsible for all the task related to managing staffs
 * and departments, including add, remove, get staff/department
 */
public class StaffsManager {
  /**
   * Store departments and staffs in ArrayLists
   */
  private final List<Staff> staffs = new ArrayList<>();
  private final List<Department> departments = new ArrayList<>();

  /**
   * @return a list of all the staffs
   */
  public List<Staff> getAllStaffs() { return Collections.unmodifiableList(staffs); }
  /**
   * @return a list of all the departments
   */
  public List<Department> getAllDepartments() { return Collections.unmodifiableList(departments); }

  /**
   * Take a staff's ID and return an (optional) staff instance
   * @param id the ID of the staff to get
   * @return an Optional represent the staff found
   */
  public Optional<Staff> getStaffById(String id) {
    return staffs.stream().filter(s -> s.getId().equals(id)).findAny();
  }
  /**
   * Take a department's ID and return an (optional) department instance
   * @param id the ID of the department to get
   * @return an Optional represent the department found
   */
  public Optional<Department> getDepartmentById(String id) {
    return departments.stream().filter(d -> d.getId().equals(id)).findAny();
  }

  /**
   * Take a staff's name and return list of staff instance
   * @param name the name of the staff to get
   * @return a list of the staffs whose name contains the provided name
   */
  public List<Staff> getStaffByName(String name) {
    return staffs.stream().filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())).toList();
  }
  /**
   * Take a department's name and return list of department instance
   * @param name the name of the department to get
   * @return a list of the departments whose name contains the provided name
   */
  public List<Department> getDepartmentByName(String name) {
    return departments.stream().filter(d -> d.getName().toLowerCase().contains(name.toLowerCase())).toList();
  }

  /**
   * add a new department
   */
  public void addDepartment(Department department) { departments.add(department); }
  /**
   * remove a department if it has no staffs
   */
  public void removeDepartment(Department department) {
    if (department == null) { return; }
    if (department.getNumberOfStaff() > 0) {
      throw new IllegalArgumentException("Departments have to have no staffs before removal");
    }

    departments.remove(department);
  }

  /**
   * add a new staff and update the department number of staff accordingly
   */
  public void addStaff(Staff staff) {
    Optional<Department> department = getDepartmentById(staff.getDepartmentId());
    if (department.isEmpty()) {
      throw new IllegalArgumentException("Invalid Department ID provided");
    }

    staffs.add(staff);
    department.get().incrementNumberOfStaff();
  }
  /**
   * remove a staff and update the department number of staff accordingly
   */
  public void removeStaff(Staff staff) {
    if (staff == null) { return; }
    Optional<Department> department = getDepartmentById(staff.getDepartmentId());
    if (!staffs.contains(staff)) {
      throw new IllegalArgumentException("Invalid Staff instance provided");
    }

    staffs.remove(staff);
    department.ifPresent(Department::decrementNumberOfStaff);
  }
}
