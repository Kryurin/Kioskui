public class Equipment {
    private String name;
    private int id;
    private String description;

    public Equipment(String name, int id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ID: " + id + ". Name: " + name;
    }

}
