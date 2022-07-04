package com.kaos.skynet.api.logic.controller.inpatient.surgery;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDeptPrivMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryEmplPrivMapper;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@CrossOrigin
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
     * @return
     */
    @PassToken
    @ApiName("查询指定科室的授权手术")
    @RequestMapping(value = "queryGrantedSurgerys", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryGrantedSurgerys.RspBody> queryGrantedSurgerys(@RequestBody @Valid QueryGrantedSurgerys.ReqBody reqBody) {
        // 检索出该科室的所有手术
        var deptBuilder = SurgeryDeptPrivMapper.Key.builder();
        deptBuilder.deptCode(reqBody.deptCode);
        deptBuilder.valid(true);
        var surgeryDeptPrivs = surgeryDeptPrivMapper.querySurgeryDeptPrivs(deptBuilder.build());

        // 过滤出ICD编码
        var icdCodes = surgeryDeptPrivs.stream().map(x -> {
            return x.getIcdCode();
        }).distinct();

        // 检索有效手术字典
        var surgerys = icdCodes.map(x -> {
            var v = surgeryDictMapper.querySurgeryDict(x);
            if (!v.getValid()) {
                return null;
            }
            return v;
        }).filter(Objects::nonNull);

        var rspBuilder = QueryGrantedSurgerys.RspBody.builder();
        var emplBuilder = SurgeryEmplPrivMapper.Key.builder().valid(true);
        return surgerys.map(x -> {
            rspBuilder.OPR_ID(x.getIcdCode());
            rspBuilder.OPR_NAME(x.getSurgeryName());
            rspBuilder.OPR_LEVEL(x.getSurgeryLevel());
            rspBuilder.PYM(x.getTeleprompter());
            // 术者信息
            var emplInfos = surgeryEmplPrivMapper.querySurgeryEmplPrivs(emplBuilder.icdCode(x.getIcdCode()).build());
            rspBuilder.OPR_DOC = String.join(",", emplInfos.stream().map(y -> {
                return y.getEmplCode();
            }).toList());
            // 科室信息
            rspBuilder.DEPT_CODE = reqBody.deptCode;
            return rspBuilder.build();
        }).toList();
    }

    static class QueryGrantedSurgerys {
        static class ReqBody {
            /**
             * 拟申请手术科室
             */
            String deptCode;
        }

        @Builder
        static class RspBody {
            /**
             * 手术编码
             */
            String OPR_ID;

            /**
             * 手术名称
             */
            String OPR_NAME;

            /**
             * 手术分级
             */
            SurgeryLevelEnum OPR_LEVEL;

            /**
             * 拼音码
             */
            String PYM;

            /**
             * 术者
             */
            String OPR_DOC;

            /**
             * 科室编码
             */
            String DEPT_CODE;
        }
    }

    /**
     * 查询手术字典
     * 
     * @param reqBody
     * @return
     */
    @ApiName("获取手术字典")
    @RequestMapping(value = "getDictionary", method = RequestMethod.POST, produces = MediaType.JSON)
    public List<GetDictionary.RspBody> getDictionary(@RequestBody @Valid GetDictionary.ReqBody reqBody) {
        // 检索数据库
        var keyBuilder = SurgeryDictMapper.Key.builder();
        keyBuilder.level(reqBody.level);
        keyBuilder.valid(true);
        var surgeries = surgeryDictMapper.querySurgeryDicts(keyBuilder.build());

        return surgeries.stream().map(x -> {
            var rspBuilder = GetDictionary.RspBody.builder();
            rspBuilder.icdCode(x.getIcdCode());
            rspBuilder.name(x.getSurgeryName());
            rspBuilder.level(x.getSurgeryLevel());
            rspBuilder.teleprompter(x.getTeleprompter());
            return rspBuilder.build();
        }).toList();
    }

    static class GetDictionary {
        static class ReqBody {
            /**
             * 手术等级
             */
            SurgeryLevelEnum level;
        }

        @Builder
        static class RspBody {
            /**
             * ICD-9编码
             */
            String icdCode;

            /**
             * 手术名
             */
            String name;

            /**
             * 手术等级
             */
            SurgeryLevelEnum level;

            /**
             * 提词器
             */
            String teleprompter;
        }
    }
}
