package com.kaos.skynet.api.logic.controller.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.his.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.his.converter.SurgeryNameConverter;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "/api/inpatient/surgery", "/ms/inpatient/surgery" })
public class SurgeryController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 
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
    SurgeryNameConverter surgeryNameConverter;

    /**
     * 检索手术申请单信息
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "querySurgeryApply", method = RequestMethod.POST, produces = MediaType.JSON)
    public QuerySurgeryApplyResponse querySurgeryApply(@RequestBody @Valid QuerySurgeryApplyRequest req) {
        // 入参记录
        log.info(String.format("查询手术申请记录: %s", json.toJson(req)));

        // 检索手术信息
        var surgeryInfo = metOpsApplyMapper.queryApply(req.getSurgeryNo());
        if (surgeryInfo == null) {
            return null;
        }

        // 构造响应
        var rspBuilder = QuerySurgeryApplyResponse.builder();
        rspBuilder.icuFlag(surgeryInfo.getIcuFlag());

        return rspBuilder.build();
    }

    /**
     * 请求消息Body
     */
    @Getter
    public static class QuerySurgeryApplyRequest {
        /**
         * 登入科室编码
         */
        @NotNull(message = "手术号不能为空")
        private String surgeryNo;
    }

    /**
     * 响应消息Body
     */
    @Getter
    @Builder
    public static class QuerySurgeryApplyResponse {
        /**
         * 计划转入ICU标识
         */
        private Boolean icuFlag;
    }

    /**
     * 检索手术申请单信息
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "querySurgeryApplies", method = RequestMethod.POST, produces = MediaType.JSON)
    public QuerySurgeryAppliesResponse querySurgeryApplies(@RequestBody @Valid QuerySurgeryAppliesRequest req) {
        // 入参记录
        log.info(String.format("查询手术申请记录列表: %s", json.toJson(req)));

        // 检索手术信息
        var keyBuilder = MetOpsApplyMapper.Key.builder();
        keyBuilder.beginPreDate(req.getBeginPreDate());
        keyBuilder.endPreDate(req.getEndPreDate());
        keyBuilder.deptOwn(req.getDeptOwn());
        keyBuilder.valid(ValidEnum.VALID);
        var surgeryInfos = metOpsApplyMapper.queryApplies(keyBuilder.build());

        // 构造响应
        var rspBuilder = QuerySurgeryAppliesResponse.builder();
        rspBuilder.size(surgeryInfos.size());
        rspBuilder.data(surgeryInfos.stream().map(x -> {
            var itemBuilder = QuerySurgeryAppliesResponse.Item.builder();
            itemBuilder.patientNo(x.getPatientNo());
            var inMainInfo = inMainInfoCache.get("ZY01".concat(x.getPatientNo()));
            if (inMainInfo != null) {
                itemBuilder.name(inMainInfo.getName());
            }
            itemBuilder.surgeryName(surgeryNameConverter.convert(x.getOperationNo()));
            itemBuilder.icuFlag(x.getIcuFlag());
            return itemBuilder.build();
        }).toList());

        return rspBuilder.build();
    }

    @Getter
    public static class QuerySurgeryAppliesRequest {
        /**
         * 申请时间左值
         */
        @NotNull(message = "申请时间左值不能为空")
        private LocalDateTime beginPreDate;

        /**
         * 申请时间右值
         */
        @NotNull(message = "申请时间右值不能为空")
        private LocalDateTime endPreDate;

        /**
         * 院区
         */
        @NotNull(message = "院区标识不能为空")
        public DeptOwnEnum deptOwn;
    }

    /**
     * 响应消息Body
     */
    @Getter
    @Builder
    public static class QuerySurgeryAppliesResponse {
        /**
         * 手术数量
         */
        private Integer size;

        /**
         * 数据内容
         */
        private List<Item> data;

        /**
         * 数据元素
         */
        @Getter
        @Builder
        private static class Item {
            /**
             * 住院号
             */
            private String patientNo;

            /**
             * 患者姓名
             */
            private String name;

            /**
             * 手术名称
             */
            private String surgeryName;

            /**
             * ICU标识
             */
            private Boolean icuFlag;
        }
    }
}
