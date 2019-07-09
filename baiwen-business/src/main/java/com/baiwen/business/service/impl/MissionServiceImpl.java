package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IMissionMapper;
import com.baiwen.business.model.Mission;
import com.baiwen.business.service.IMissionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 **/
@Service
@Data
public class MissionServiceImpl implements IMissionService {

    @Autowired
    private IMissionMapper missionMapper;

    @Override
    public List<Mission> getMissionList() {
        return missionMapper.getMissionList();
    }
}
