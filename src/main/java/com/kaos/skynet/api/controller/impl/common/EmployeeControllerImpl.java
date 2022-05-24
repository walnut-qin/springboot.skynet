package com.kaos.skynet.api.controller.impl.common;

import com.kaos.skynet.api.data.mapper.common.DawnOrgEmplMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/employee")
public class EmployeeControllerImpl {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(EmployeeControllerImpl.class);

    @Autowired
    DawnOrgEmplMapper emplMapper;

    // @Override
    // @RequestMapping(value = "queryValidEmployees", method = RequestMethod.GET, produces = MediaType.JSON)
    // public List<EmployeeInfo> queryValidEmployees() {
    //     // 检索原始有效数据
    //     var rets = this.emplMapper.queryEmpls(null, Lists.newArrayList(ValidEnum.VALID));

    //     return rets.stream().map((x) -> {
    //         EmployeeInfo info = new EmployeeInfo();
    //         info.emplCode = x.getEmplCode();
    //         info.emplName = x.getEmplName();
    //         info.inputCode = x.getEmplNameSpellCode();
    //         info.emplType = x.getEmplType();
    //         info.deptCode = x.getDeptCode();
    //         info.createDate = LocalDateTime.now();
    //         return info;
    //     }).toList();
    // }
}
