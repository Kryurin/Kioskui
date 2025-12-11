public class Membership {
    private int id;
    private String name;
    private double value;
    private String description;

    public Membership(String name, double value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getValue() { return value; }
    public String getDescription() {
        return description;
    }


    protected void setId(int id) { this.id = id; }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Value: " + value;
    }
}
