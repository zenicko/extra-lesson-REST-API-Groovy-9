package ru.zenicko.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*
{
        "name": "morpheus",
        "job": "leader"
    }
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String job;

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "{\"name\": \"" + getName() + "\", \"job\": \"" + getJob() + "\"}";
    }
}
