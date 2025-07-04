package org.edukos.carchargerds.model;

public class EventInfo {
    private String id;  // Using event name as ID
    private String name;
    private String description;

    public EventInfo(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}