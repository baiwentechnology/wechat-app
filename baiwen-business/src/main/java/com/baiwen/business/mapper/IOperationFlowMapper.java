package com.baiwen.business.mapper;

import com.baiwen.business.model.OperationFlow;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IOperationFlowMapper {

    void addOperationFlow(OperationFlow flow);

    //List<OperationFlow> getFlowList(Map params);
}
