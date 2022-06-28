package com.kaos.skynet.api.logic.controller.inpatient.surgery;

import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDeptPrivMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryEmplPrivMapper;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@PassToken
@Validated
@RestController
@RequestMapping("/api/inpatient/surgery")
public class SurgeryController {
    /**
     * 科室授权接口
     */
    @Autowired
    SurgeryDeptPrivMapper surgeryDeptPrivMapper;

    /**
     * 人员授权接口
     */
    @Autowired
    SurgeryEmplPrivMapper surgeryEmplPrivMapper;

    /**
     * 手术字典接口
     */
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    /**
     * 查询已经授权的手术清单
     * 
     * @param reqBody
     * @return
     */
    @RequestMapping(value = "queryGrantedSurgerys", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryGrantedSurgerys.RspBody> queryGrantedSurgerys(@RequestBody @Valid QueryGrantedSurgerys.ReqBody reqBody) {
        // 读取授权科室
        var deptKeyBuilder = SurgeryDeptPrivMapper.Key.builder();
        deptKeyBuilder.deptCode(reqBody.deptCode);
        deptKeyBuilder.valid(true);
        var deptIcdCodes = surgeryDeptPrivMapper.querySurgeryDeptPrivs(deptKeyBuilder.build()).stream().map(x -> {
            return x.getIcdCode();
        });

        // 读取授权医生
        var emplKeyBuilder = SurgeryEmplPrivMapper.Key.builder();
        emplKeyBuilder.emplCode(reqBody.emplCode);
        emplKeyBuilder.valid(true);
        var emplIcdCodes = surgeryEmplPrivMapper.querySurgeryEmplPrivs(emplKeyBuilder.build()).stream().map(x -> {
            return x.getIcdCode();
        });

        // 取交集的手术编码
        Stream<String> icdCodes = deptIcdCodes.filter(emplIcdCodes.toList()::contains);

        // 转结果
        return icdCodes.map(x -> {
            // 查询数据
            var surgery = surgeryDictMapper.querySurgeryDict(x);

            // 赋值
            var builder = QueryGrantedSurgerys.RspBody.builder();
            builder.icdCode(surgery.getIcdCode());
            builder.surgeryName(surgery.getSurgeryName());
            builder.surgeryLevel(surgery.getSurgeryLevel());
            return builder.build();
        }).toList();
    }

    static class QueryGrantedSurgerys {
        static class ReqBody {
            /**
             * 员工编码
             */
            @NotBlank(message = "术者编码不能为空")
            String emplCode;

            /**
             * 科室编码
             */
            @NotBlank(message = "科室编码不能为空")
            String deptCode;
        }

        @Builder
        static class RspBody {
            /**
             * 手术编码
             */
            String icdCode;

            /**
             * 手术名称
             */
            String surgeryName;

            /**
             * 手术分级
             */
            SurgeryLevelEnum surgeryLevel;
        }
    }
}
