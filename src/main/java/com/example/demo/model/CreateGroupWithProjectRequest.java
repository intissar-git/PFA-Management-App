package com.example.demo.model;

import lombok.*;

import java.util.List;

public class CreateGroupWithProjectRequest {
    // Getters & Setters
    @Getter
    private String groupName;
    @Getter
    private String projectTitle;
    @Getter
    private String projectDescription;
    private int filiereId;
    @Getter
    private int encadrantId;
    @Getter
    private List<Integer> studentIds;

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public long getFiliereId() {
        return filiereId;
    }

    public void setFiliereId(int filiereId) {
        this.filiereId = filiereId;
    }

    public void setEncadrantId(int encadrantId) {
        this.encadrantId = encadrantId;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
