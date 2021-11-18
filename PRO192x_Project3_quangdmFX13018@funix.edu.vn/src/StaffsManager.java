import java.util.*;

/**
 * This class is responsible for all the task related to managing staffs
 * and departments, including add, remove, get staff/department
 */
public class StaffsManager {
  private final List<Staff> staffs = new ArrayList<>();
  private final List<Department> departments = new ArrayList<>();

  public List<Staff> getAllStaffs() { return Collections.unmodifiableList(staffs); }
  public List<Department> getAllDepartments() { return Collections.unmodifiableList(departments); }

  public Optional<Staff> getStaffById(String id) {
    return staffs.stream().filter(s -> s.getId().equals(id)).findAny();
  }
  public Optional<Department> getDepartmentById(String id) {
    return departments.stream().filter(d -> d.getId().equals(id)).findAny();
  }

  public List<Staff> getStaffByName(String name) {
    return staffs.stream().filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())).toList();
  }
  public List<Department> getDepartmentByName(String name) {
    return departments.stream().filter(d -> d.getName().toLowerCase().contains(name.toLowerCase())).toList();
  }

  public void addDepartment(Department department) { departments.add(department); }
  public void removeDepartment(Department department) {
    if (department == null) { return; }
    if (department.getNumberOfStaff() > 0) {
      throw new IllegalArgumentException("Departments have to have no staffs before removal");
    }

    departments.remove(department);
  }

  public void addStaff(Staff staff) {
    Optional<Department> department = getDepartmentById(staff.getDepartmentId());
    if (department.isEmpty()) {
      throw new IllegalArgumentException("Invalid Department ID provided");
    }

    staffs.add(staff);
    department.get().incrementNumberOfStaff();
  }
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
