package ru.zenicko.reqres.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
/*
{
            "name": "morpheus",
            "job": "leader",
            "id": "254",
            "createdAt": "2022-01-19T23:41:18.899Z"
    }
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
