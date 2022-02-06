package com.example.drawer_activity;

public class TaskModel
{
    String id;
    String name;
    String taskdescription;
    Boolean taskStatus;

    public TaskModel(String id, String name, String taskdescription,Boolean taskStatus) {
        this.id = id;
        this.name = name;
        this.taskdescription = taskdescription;
        this.taskStatus = taskStatus;
    }

    public Boolean getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return taskdescription;
    }

    public void setDescription(String description) {
        this.taskdescription = description;
    }
}
