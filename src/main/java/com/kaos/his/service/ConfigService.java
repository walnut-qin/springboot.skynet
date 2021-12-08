package com.kaos.his.service;

import com.google.common.base.Optional;
import com.kaos.his.mapper.config.VariableListMapper;
import com.kaos.his.mapper.config.VariableMapper;
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
    VariableMapper paramMapper;

    /**
     * 列表接口
     */
    @Autowired
    VariableListMapper listMapper;

    /**
     * 测试开关状态
     * 
     * @param switchName
     * @return
     */
    public Boolean TestSwitch(String switchName) {
        // 根据开关名获取开关值
        var swt = this.switchMapper.QuerySwitch(switchName);
        if (swt == null || !swt.valid) {
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
        var lst = this.listMapper.QueryVariableList(listName);
        if (lst == null) {
            return false;
        }

        // 轮训
        for (var variable : lst.variables) {
            if (variable.valid && variable.value.equals(item)) {
                return true;
            }
        }

        return false;
    }
}
