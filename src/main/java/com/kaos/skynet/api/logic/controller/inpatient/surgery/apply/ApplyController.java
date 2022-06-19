package com.kaos.skynet.api.logic.controller.inpatient.surgery.apply;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.his.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.api.data.his.router.SurgeryNameRouter;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.http.RspWrapper;
import com.kaos.skynet.core.json.Json;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/api/inpatient/surgery/apply")
class ApplyController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 手术申请单接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 住院实体缓存
     */
    @Autowired
    FinIprInMainInfoCache inMainInfoCache;

    /**
     * 手术名称转换器
     */
    @Autowired
    SurgeryNameRouter surgeryNameConverter;

    /**
     * 检索手术申请单信息
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "querySurgeryInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    RspWrapper<QuerySurgeryInfo.RspBody> querySurgeryInfo(@RequestBody @Valid QuerySurgeryInfo.ReqBody reqBody) {
        try {
            // 入参记录
            log.info("查询手术申请记录: ".concat(json.toJson(reqBody)));

            // 检索手术信息
            var surgeryInfo = metOpsApplyMapper.queryApply(reqBody.applyNo);
            if (surgeryInfo == null) {
                throw new NotFoundException("未查询到手术");
            }

            // 构造响应
            var rspBuilder = QuerySurgeryInfo.RspBody.builder();
            rspBuilder.icuFlag(surgeryInfo.getIcuFlag());

            return RspWrapper.wrapSuccessResponse(rspBuilder.build());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    class QuerySurgeryInfo {
        static class ReqBody {
            /**
             * 手术申请单号
             */
            @NotBlank(message = "手术申请单号不能为空")
            String applyNo;
        }

        @Builder
        static class RspBody {
            /**
             * 计划转入ICU标识
             */
            Boolean icuFlag;
        }
    }

    /**
     * 检索手术申请单信息
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "querySurgeryInfos", method = RequestMethod.POST, produces = MediaType.JSON)
    public RspWrapper<List<QuerySurgeryInfos.RspBody>> querySurgeryInfos(
            @RequestBody @Valid QuerySurgeryInfos.ReqBody reqBody) {
        try {
            // 入参记录
            log.info("查询手术申请记录列表: ".concat(json.toJson(reqBody)));

            // 检索手术信息
            var keyBuilder = MetOpsApplyMapper.Key.builder();
            keyBuilder.beginPreDate(reqBody.beginPreDate);
            keyBuilder.endPreDate(reqBody.endPreDate);
            keyBuilder.deptOwn(reqBody.deptOwn);
            keyBuilder.valid(ValidEnum.VALID);
            var surgeryInfos = metOpsApplyMapper.queryApplies(keyBuilder.build());

            // 构造响应
            var builder = QuerySurgeryInfos.RspBody.builder();
            return RspWrapper.wrapSuccessResponse(surgeryInfos.stream().map(x -> {
                builder.patientNo(x.getPatientNo());
                var inMainInfo = inMainInfoCache.get("ZY01".concat(x.getPatientNo()));
                if (inMainInfo != null) {
                    builder.name(inMainInfo.getName());
                }
                builder.surgeryName(surgeryNameConverter.route(x.getOperationNo()));
                builder.icuFlag(x.getIcuFlag());
                return builder.build();
            }).toList());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    class QuerySurgeryInfos {
        static class ReqBody {
            /**
             * 申请时间左值
             */
            @NotNull(message = "申请时间左值不能为空")
            LocalDateTime beginPreDate;

            /**
             * 申请时间右值
             */
            @NotNull(message = "申请时间右值不能为空")
            LocalDateTime endPreDate;

            /**
             * 院区
             */
            @NotNull(message = "院区标识不能为空")
            DeptOwnEnum deptOwn;
        }

        @Builder
        static class RspBody {
            /**
             * 住院号
             */
            String patientNo;

            /**
             * 患者姓名
             */
            String name;

            /**
             * 手术名称
             */
            String surgeryName;

            /**
             * ICU标识
             */
            Boolean icuFlag;
        }
    }
}
