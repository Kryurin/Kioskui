public class Membership {
        private String name;
        private double value;

        public Membership(String name, double value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Name: " + name + " Value: " + value;
        }

    }

