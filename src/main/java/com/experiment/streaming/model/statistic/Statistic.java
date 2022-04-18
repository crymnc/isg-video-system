package com.experiment.streaming.model.statistic;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic extends BaseModel {
    private Long userId;
    private Boolean isContentFinished;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date finishedAt;
}
