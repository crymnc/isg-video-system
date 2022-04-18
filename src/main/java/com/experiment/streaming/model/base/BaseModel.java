package com.experiment.streaming.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public abstract class BaseModel {
    private Long id;
    @JsonIgnore
    private Long createdBy,updatedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date discontinueDate;
    @JsonIgnore
    private Date createdAt,updatedAt;
}
