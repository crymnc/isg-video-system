package com.experiment.streaming.model;

import com.experiment.streaming.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    private String name,lastName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean isActive = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastLoginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date entryDate;
    private Long roleId;
    private Long companyId;
}
