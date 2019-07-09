package com.baiwen.business.service;

import com.alibaba.fastjson.JSONObject;

public interface IsignService {
    JSONObject getSignData(int userId);

    JSONObject makeSign(int userId);
}
