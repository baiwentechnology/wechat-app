package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IOperationFlowMapper;
import com.baiwen.business.model.OperationFlow;
import com.baiwen.business.service.IOperationFlowService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class OperationFlowServiceImpl implements IOperationFlowService {

    @Autowired
    private IOperationFlowMapper operationFlowMapper;

    @Override
    public void addOperationFlow(OperationFlow flow) {
        if(flow == null){
            return;
        }
        operationFlowMapper.addOperationFlow(flow);
    }
}
