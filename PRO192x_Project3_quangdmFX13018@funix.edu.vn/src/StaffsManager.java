import java.util.*;

/**
 * This class is responsible for all the task related to managing staffs
 * and departments, including add, remove, get staff/department
 */
public class StaffsManager {
  private final Map<String, Staff> staffs = new HashMap<>();
  private final Map<String, Department> departments = new HashMap<>();

  public List<Staff> getAllStaffs() { return new ArrayList<>(staffs.values()); }
  public List<Department> getAllDepartments() { return new ArrayList<>(departments.values()); }

  public Optional<Staff> getStaffById(String id) { return Optional.ofNullable(staffs.get(id)); }
  public Optional<Department> getDepartmentById(String id) { return Optional.ofNullable(departments.get(id)); }

  public List<Staff> getStaffByName(String name) {
    return staffs.values().stream().filter(s -> s.getName().equals(name)).toList();
  }
  public List<Department> getDepartmentByName(String name) {
    return departments.values().stream().filter(s -> s.getName().equals(name)).toList();
  }

  public void addDepartment(Department department) { departments.put(department.getId(), department); }
  public void removeDepartment(Department department) {
    if (department == null) { return; }
    if (department.getNumberOfStaff() > 0) {
      throw new IllegalArgumentException("Departments have to have no staffs before removal");
    }

    departments.remove(department.getId());
  }

  public void addStaff(Staff staff) {
    String departmentId = staff.getDepartmentId();
    if (!departments.containsKey(departmentId)) {
      throw new IllegalArgumentException("Invalid Department ID provided");
    }

    staffs.put(staff.getId(), staff);
    departments.get(departmentId).incrementNumberOfStaff();
  }
  public void removeStaff(Staff staff) {
    if (staff == null) { return; }
    if (!staffs.containsKey(staff.getId())) {
      throw new IllegalArgumentException("Invalid Staff instance provided");
    }

    staffs.remove(staff.getId());
    departments.get(staff.getDepartmentId()).decrementNumberOfStaff();
  }
}
