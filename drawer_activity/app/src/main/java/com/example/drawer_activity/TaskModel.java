package com.example.drawer_activity;

public class TaskModel
{
    String id;
    String task;
    String description;

    public TaskModel(String id, String task, String description) {
        this.id = id;
        this.task = task;
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id='" + id + '\'' +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
