package com.experiment.streaming.model;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContent extends BaseModel {

    @NotEmpty
    private String name;
    private String description;
    private String shortName;
    @NotEmpty
    private String title;
    private Double duration;
    private String path;
    @NotNull
    private Long contentTypeId;
    @NotNull
    private Long companyId;
    private String companyName;
    private boolean isWatched = false;
    private int watchedRate = 0;
}
