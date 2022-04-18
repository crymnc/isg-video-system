package com.experiment.streaming.model;

import com.experiment.streaming.model.base.BaseModel;
import lombok.Data;

@Data
public class UserSummary extends BaseModel {
    private String name,lastName;
    private String email;
    private boolean isActive = true;
    private String roleName;
    private Long roleId;
    private String companyName;
    private Long companyId;
}
