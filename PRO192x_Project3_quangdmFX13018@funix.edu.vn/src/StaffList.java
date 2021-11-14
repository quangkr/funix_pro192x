import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class StaffList {
  private final List<Staff> list = new ArrayList<>();

  public List<Staff> getAll() {
    return Collections.unmodifiableList(list);
  }

  public Optional<Staff> getStaffById(String id) {
    return list.stream().filter(s -> s.getId().equals(id)).findAny();
  }

  public List<Staff> getStaffByName(String name) {
    return list.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
  }

  public void add (Staff staff) { list.add(staff); }
  public void remove (Staff staff) { list.remove(staff); }
}
