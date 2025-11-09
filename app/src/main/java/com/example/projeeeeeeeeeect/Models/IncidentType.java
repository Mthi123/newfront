package com.example.projeeeeeeeeeect.Models;

public class IncidentType {
    int id;
    String name;

    public IncidentType() { }

    public IncidentType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
