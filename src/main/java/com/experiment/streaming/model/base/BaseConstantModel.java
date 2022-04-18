package com.experiment.streaming.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseConstantModel extends BaseModel {
    private String name, dsc;
}
