package com.experiment.streaming.model;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseModel {
    private String name;
    private String externalId;
    private String address;
    private String description;
}
