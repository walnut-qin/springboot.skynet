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
import com.kaos.skynet.api.data.his.tunnel.SurgeryNameTunnel;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.config.spring.net.RspWrapper;

import org.apache.ibatis.javassist.NotFoundException;
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
@RequestMapping("/api/inpatient/surgery/apply")
class ApplyController {
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
    SurgeryNameTunnel surgeryNameTunnel;

    /**
     * 检索手术申请单信息
     * 
     * @param req
     * @return
     */
    @ApiName("查询手术申请记录")
    @RequestMapping(value = "querySurgeryInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    RspWrapper<QuerySurgeryInfo.RspBody> querySurgeryInfo(@RequestBody @Valid QuerySurgeryInfo.ReqBody reqBody) {
        try {
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

    static class QuerySurgeryInfo {
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
    @ApiName("querySurgeryInfos")
    @RequestMapping(value = "querySurgeryInfos", method = RequestMethod.POST, produces = MediaType.JSON)
    public RspWrapper<List<QuerySurgeryInfos.RspBody>> querySurgeryInfos(
            @RequestBody @Valid QuerySurgeryInfos.ReqBody reqBody) {
        try {
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
                builder.surgeryName(surgeryNameTunnel.tunneling(x.getOperationNo()));
                builder.icuFlag(x.getIcuFlag());
                return builder.build();
            }).toList());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    static class QuerySurgeryInfos {
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
