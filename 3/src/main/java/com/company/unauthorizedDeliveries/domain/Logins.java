package com.company.unauthorizedDeliveries.domain;

import javax.persistence.*;

@Entity
@Table(name = "loginsFile")
public class Logins {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String application;
    private String appAccountName;
    private boolean active;
    private String jobTitle;
    private String department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application.trim();
    }

    public String getAppAccountName() {
        return appAccountName;
    }

    public void setAppAccountName(String appAccountName) {
        this.appAccountName = appAccountName.trim();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department.trim();
    }
}
