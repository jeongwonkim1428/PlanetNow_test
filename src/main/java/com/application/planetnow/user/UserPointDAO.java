package com.application.planetnow.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPointDAO {

    Long getUserTotalPoint(Long userId);

    void userPointSave(UserPointDTO userPointDTO);

}
