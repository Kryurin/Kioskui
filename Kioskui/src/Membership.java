import javax.swing.*;

public class Membership {
        private String name;
        private int id;
        private double value;
        private String description;

        public Membership(String name, double value, String description) {
            this.name = name;
            this.value = value;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public int getId() {return id;}

        public double getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
        public String toString() {
            return "Name: " + name + " Value: " + value;
        }

    }

