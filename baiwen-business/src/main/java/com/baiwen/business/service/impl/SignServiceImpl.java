package com.baiwen.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.business.mapper.IsignMapper;
import com.baiwen.business.service.IsignService;
import com.baiwen.common.util.DateUtils;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
@Service
@Data
public class SignServiceImpl implements IsignService {
    @Autowired
    private IsignMapper isignMapper;
    @Override
    public JSONObject getSignData(int userId) {
        JSONObject jsonObject = new JSONObject();
        boolean flag=false;
       List<String> userSignList= isignMapper.getSignByUserId(userId);
        jsonObject.put("total",userSignList.size());
        String s = new DateTime().toString(DateUtils.YYYYMMDD_W);
        for (int i = 0; i <userSignList.size() ; i++) {
            if(userSignList.get(i).equals(s)){
                flag=true;
            }
        }
        jsonObject.put("signed",flag);
        return jsonObject;
    }
    @Transactional
    @Override
    public JSONObject makeSign(int userId) {
        JSONObject jsonObject=new JSONObject();
        try{
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("userId",userId);
        objectObjectHashMap.put("signDate",new DateTime().toString(DateUtils.YYYYMMDD_W));
        isignMapper.addUserSign(objectObjectHashMap);
            List<String> signByUserId = isignMapper.getSignByUserId(userId);
            jsonObject.put("success",true);
            jsonObject.put("totalDate",signByUserId.size());
//            获得随机数量的金币或者是水滴
            jsonObject.put("reward",1);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("success",false);
            jsonObject.put("data","签到失败");
        }
return jsonObject;

        }
}
