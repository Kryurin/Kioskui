public class Membership {
    private int id;
    private String name;
    private double value;

    public Membership(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getValue() { return value; }

    protected void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Value: " + value;
    }
}
