package com.kaos.his.service;

import com.google.common.base.Optional;
import com.kaos.his.entity.config.ParamList.Param;
import com.kaos.his.mapper.config.ParamListMapper;
import com.kaos.his.mapper.config.ParamMapper;
import com.kaos.his.mapper.config.SwitchMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    /**
     * 开关接口
     */
    @Autowired
    SwitchMapper switchMapper;

    /**
     * 参数接口
     */
    @Autowired
    ParamMapper paramMapper;

    /**
     * 列表接口
     */
    @Autowired
    ParamListMapper listMapper;

    /**
     * 测试开关状态
     * 
     * @param switchName
     * @return
     */
    public Boolean TestSwitch(String switchName) {
        // 根据开关名获取开关值
        var swt = this.switchMapper.QuerySwitch(switchName, true);
        if (swt == null) {
            return false;
        }

        // 响应开关状态
        return Optional.fromNullable(swt.state).or(false);
    }

    /**
     * 测试项目是否在列表中
     * 
     * @param listName
     * @param item
     * @return
     */
    public Boolean TestListItem(String listName, String item) {
        // 查询列表
        var lst = this.listMapper.QueryParamList(listName, true);
        if (lst == null) {
            return false;
        }

        // 轮训
        for (Param param : lst.params) {
            if (param.value.equals(item)) {
                return true;
            }
        }

        return false;
    }
}
