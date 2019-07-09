package com.baiwen.business.mapper;

import com.baiwen.business.model.SysConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IsysMapper {
    /**
     * 根据系统的配置id 查找系统的配置的值
     * @param configType
     * @return
     */
    SysConfig getSysConfigById(@Param("configType") int configType);
}
