package com.baiwen.business.service;

import com.baiwen.business.model.DayDrop;

import java.util.Map;

public interface IDayDropService {

    DayDrop getUserDayDrop(Map params);

    void addOrUpdate(DayDrop dayDrop);
}
