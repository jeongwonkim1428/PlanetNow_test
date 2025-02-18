package com.application.planetnow.user;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@Data
public class PointDTO {
    private Long pointId;
    private String action;
    private Long pointValue;

    public PointDTO() {}

    public PointDTO(Long pointId, String action, Long pointValue) {
        this.pointId = pointId;
        this.action = action;
        this.pointValue = pointValue;
    }

}
