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
            List<String> signByUserId = isignMapper.getSignByUserId(userId);
            for (int i = 0; i <signByUserId.size() ; i++) {
                if (signByUserId.get(i).equals( new DateTime().toString(DateUtils.YYYYMMDD_W))){
                    jsonObject.put("success",false);
                    jsonObject.put("message","签到失败,一天内只能签到一次");
                    return jsonObject;
                }
            }
            isignMapper.addUserSign(objectObjectHashMap);
            jsonObject.put("success",true);
            jsonObject.put("totalDate",signByUserId.size());
//            获得随机数量的金币或者是水滴
            jsonObject.put("rewardType",1);
            jsonObject.put("rewordCount",1);
            jsonObject.put("message","签到成功");
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("success",false);
            jsonObject.put("message","签到失败,请联系管理员");
            return jsonObject;
        }
    }
}
