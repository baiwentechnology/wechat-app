package com.baiwen.business.mapper;

import com.baiwen.business.model.DayDrop;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IDayDropMapper {

    DayDrop getUserDayDrop(Map params);

    void addDayDrop(DayDrop dayDrop);

    void updateDayDrop(DayDrop dayDrop);
}
