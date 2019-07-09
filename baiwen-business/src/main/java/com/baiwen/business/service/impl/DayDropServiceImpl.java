package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IDayDropMapper;
import com.baiwen.business.model.DayDrop;
import com.baiwen.business.service.IDayDropService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Data
public class DayDropServiceImpl implements IDayDropService {

    @Autowired
    private IDayDropMapper dayDropMapper;

    @Override
    public DayDrop getUserDayDrop(Map params) {
        if(params.isEmpty()){
            return null;
        }
        DayDrop dayDrop = dayDropMapper.getUserDayDrop(params);
        return dayDrop;
    }

    @Override
    public void addOrUpdate(DayDrop dayDrop) {
        if(dayDrop == null){
            return;
        }
        if(dayDrop.getId() > 0){
            dayDropMapper.updateDayDrop(dayDrop);
        }else{
            dayDropMapper.addDayDrop(dayDrop);
        }
    }
}
