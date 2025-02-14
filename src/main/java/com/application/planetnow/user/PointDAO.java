package com.application.planetnow.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointDAO {
    List<PointDTO> getPointList();
    PointDTO getReplyPoint();
	PointDTO getLikePoint();
}
