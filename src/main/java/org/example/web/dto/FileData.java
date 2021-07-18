package org.example.web.dto;

public class FileData {
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
