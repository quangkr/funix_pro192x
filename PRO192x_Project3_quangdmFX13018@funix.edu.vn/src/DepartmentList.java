import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class DepartmentList {
  private final List<Department> list = new ArrayList<>();

  public List<Department> getAll() {
    return Collections.unmodifiableList(list);
  }

  public Optional<Department> getDepartmentById(String id) {
    return list.stream().filter(d -> d.getId().equals(id)).findAny();
  }

  public List<Department> getDepartmentByName(String name) {
    return list.stream().filter(d -> d.getName().equals(name)).collect(Collectors.toList());
  }

  public void add (Department department) { list.add(department); }
  public void remove (Department department) { list.remove(department); }
}
