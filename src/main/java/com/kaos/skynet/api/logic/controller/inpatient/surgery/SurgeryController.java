package com.kaos.skynet.api.logic.controller.inpatient.surgery;

import java.util.List;

import com.kaos.skynet.api.data.his.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDeptPrivMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryEmplPrivMapper;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
     * 科室信息缓存
     */
    @Autowired
    DawnOrgDeptCache dawnOrgDeptCache;

    /**
     * 查询已经授权的手术清单
     * 
     * @return
     */
    @RequestMapping(value = "queryGrantedSurgerys", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryGrantedSurgerys.RspBody> queryGrantedSurgerys() {
        // 检索有效手术字典
        var surgerys = surgeryDictMapper.querySurgeryDicts(SurgeryDictMapper.Key.builder().valid(true).build());

        var rspBuilder = QueryGrantedSurgerys.RspBody.builder();
        var deptBuilder = SurgeryDeptPrivMapper.Key.builder().valid(true);
        var emplBuilder = SurgeryEmplPrivMapper.Key.builder().valid(true);
        return surgerys.stream().map(x -> {
            rspBuilder.OPR_ID(x.getIcdCode());
            rspBuilder.OPR_NAME(x.getSurgeryName());
            rspBuilder.OPR_LEVEL(x.getSurgeryLevel());
            // 科室信息
            var deptInfos = surgeryDeptPrivMapper.querySurgeryDeptPrivs(deptBuilder.icdCode(x.getIcdCode()).build());
            rspBuilder.DEPT_CODE = String.join(",", deptInfos.stream().map(y -> {
                return y.getDeptCode();
            }).toList());
            rspBuilder.DEPT_NAME = String.join(",", deptInfos.stream().map(y -> {
                var dept = dawnOrgDeptCache.get(y.getDeptCode());
                if (dept != null) {
                    return dept.getDeptName();
                } else {
                    return y.getDeptCode();
                }
            }).toList());
            // 术者信息
            var emplInfos = surgeryEmplPrivMapper.querySurgeryEmplPrivs(emplBuilder.icdCode(x.getIcdCode()).build());
            rspBuilder.OPR_DOC = String.join(",", emplInfos.stream().map(y -> {
                return y.getEmplCode();
            }).toList());
            return rspBuilder.build();
        }).toList();
    }

    static class QueryGrantedSurgerys {
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
             * 科室编码
             */
            String DEPT_CODE;

            /**
             * 科室名称
             */
            String DEPT_NAME;

            /**
             * 拼音码
             */
            String PYM;

            /**
             * 五笔码
             */
            String WBM;

            /**
             * 手术医生
             */
            String OPR_DOC;
        }
    }
}
