package com.kaos.skynet.api.controller.impl.common;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.common.EmployeeController;
import com.kaos.skynet.api.mapper.common.DawnOrgEmplMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/employee")
public class EmployeeControllerImpl implements EmployeeController {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(EmployeeControllerImpl.class);

    @Autowired
    DawnOrgEmplMapper emplMapper;

    @Override
    @RequestMapping(value = "queryAllValidEmployees", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<EmployeeInfo> queryAllValidEmployees() {
        // 检索原始有效数据
        var rets = this.emplMapper.queryValidEmployees();

        return rets.stream().map((x) -> {
            EmployeeInfo info = new EmployeeInfo();
            info.emplCode = x.emplCode;
            info.emplName = x.emplName;
            info.inputCode = x.emplNameSpellCode;
            info.deptCode = x.deptCode;
            info.createDate = LocalDateTime.now();
            return info;
        }).toList();
    }
}
