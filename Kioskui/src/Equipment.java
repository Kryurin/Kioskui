public class Equipment {
    private int id;
    private String name;
    private String description;

    public Equipment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    protected void setId(int id) { this.id = id; } // needed for DB-assigned IDs

    @Override
    public String toString() {
        return "ID: " + id + ". Name: " + name;
    }
}
