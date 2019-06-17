package com.baiwen.business.mapper;

import com.baiwen.business.model.Mission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 **/
@Repository
public interface IMissionMapper {
    List<Mission> getMissionList();
}
