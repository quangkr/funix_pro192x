/**
 * An enum represents different titles that a manager can have
 */
public enum ManagerTitles {
  BUSINESS_LEADER("Business Leader"),
  PROJECT_LEADER("Project Leader"),
  TECHNICAL_LEADER("Technical Leader");

  private final String value;

  ManagerTitles(String v) { this.value = v; }

  public String value() { return value; }
}
