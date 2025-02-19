package com.application.planetnow.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {
    private Long pointId;
    private String action;
    private Long pointValue;

}
