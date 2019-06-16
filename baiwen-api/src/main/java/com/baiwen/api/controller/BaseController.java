package com.baiwen.api.controller;

import com.baiwen.api.message.Result;
import com.baiwen.common.util.DateUtils;
import com.baiwen.common.util.JsonUtil;
import com.baiwen.common.util.MapUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.formula.functions.T;
import java.util.*;

public class BaseController {


    protected String[] language = {"zh", "CN"};

    /**
     * 国际化
     *暂时没用到
     * @param str
     * @return
     */
    protected String getString(String... str) {
        ResourceBundle resourceBundle;
        resourceBundle = ResourceBundle.getBundle("language.string", new Locale(language[0], language[1]));
        String home = "";
        try {
            home = resourceBundle.getString(str[0]);
        } catch (Exception e) {
            home = str[0];
        }
        if (StringUtils.isEmpty(home)) {
            if (str.length > 1) {
                home = str[1];
            } else {
                home = str[0];
            }
        } else {
            /*try {
                home=new String(home.getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
        }

        return home;
    }

    protected String setResult(Boolean success,Integer msgid,String message,Object data){
        Result result = new Result();
        result.setFlag(success);
        result.setCode(msgid);
        result.setMessage(message);
        result.setData(data);
        return JsonUtil.toJson(result);
    }

    /**
     * 列表数据转换
     * @param pageInfo
     */
    protected List<Map<String, Object>> convertList(PageInfo<T> pageInfo){
        return convertList(pageInfo.getList());
    }

    /**
     * 列表数据转换
     * @param datas
     */
    protected List<Map<String, Object>> convertList(List datas){
        List<Map<String, Object>> list=new ArrayList<>();
        for (Object object:datas) {
            HashMap<String, Object> map= convert(object);
            list.add(map);
        }
        return list;
    }

    /**
     * 数据转换
     * @param object
     */
    protected HashMap<String, Object> convert(Object object){
        HashMap<String, Object> hasMap=null;
        if(object instanceof Map) {
            hasMap = (HashMap<String, Object>) object;
        }else {
            hasMap = (HashMap) MapUtils.toMap(object);
        }

        //类型转换
        for (String key:hasMap.keySet()) {
            Object obj=hasMap.get(key);
            //格式化时间
            if(obj instanceof Date){
                String str= DateUtils.convertToString((Date) obj,DateUtils.YYYYMMDDHHMMSS_W_C);
                hasMap.put(key,str);
            }
            //防止long 失真
            if(obj instanceof Long){
                hasMap.put(key,obj==null?"":obj.toString());
            }
        }
        return hasMap;
    }





}
