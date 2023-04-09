package ca.mcmaster.cas.se2aa4.a4.pathfinder;

/**
 * Properties that can be added onto Nodes and Edges
 */
public class Property {
    /**
     * Builder to make property
     */
    static class PropertyBuilder {
        private String key;
        private String value;

        public PropertyBuilder setKey(String key) {
            this.key = key;
            return this;
        }

        public PropertyBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public Property build() {
            return new Property(key, value);
        }
    }

    public static PropertyBuilder newBuilder() {
        return new PropertyBuilder();
    }

    private final String key;
    private final String value;

    protected Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Property{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
