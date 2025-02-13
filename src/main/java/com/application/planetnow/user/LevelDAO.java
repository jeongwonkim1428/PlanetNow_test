package com.application.planetnow.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LevelDAO {
    List<LevelDTO> getLevelList();
}
