package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.FinIprPrepayInCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortMainInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortStateRecCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortVipCache;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.data.his.tunnel.BedNoTunnel;
import com.kaos.skynet.api.data.his.tunnel.DeptNameTunnel;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortLock;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;
import com.kaos.skynet.core.util.converter.StringToEnumConverterFactory;
import com.kaos.skynet.core.util.json.adapter.BooleanTypeAdapter_10;
import com.kaos.skynet.core.util.json.adapter.EnumTypeAdapter_Value;
import com.kaos.skynet.core.util.thread.lock.LockExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.extern.log4j.Log4j;

@PassToken
@Log4j
@Validated
@RestController
@RequestMapping("/api/inpatient/escort")
public class EscortController {
    /**
     * 陪护锁
     */
    @Autowired
    EscortLock escortLock;

    /**
     * 陪护服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 住院主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护状态表接口
     */
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 陪护主表缓存
     */
    @Autowired
    EscortMainInfoCache escortMainInfoCache;

    /**
     * 陪护主表缓存
     */
    @Autowired
    EscortStateRecCache escortStateRecCache;

    /**
     * VIP缓存
     */
    @Autowired
    EscortVipCache escortVipCache;

    /**
     * 患者信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 住院证缓存
     */
    @Autowired
    FinIprPrepayInCache prepayInCache;

    /**
     * 枚举值转换器
     */
    StringToEnumConverterFactory valueStringToEnumConverterFactory = new StringToEnumConverterFactory(true);

    /**
     * 枚举值转换器
     */
    StringToEnumConverterFactory descriptionStringToEnumConverterFactory = new StringToEnumConverterFactory(false);

    /**
     * 床号转换器
     */
    @Autowired
    BedNoTunnel bedNoTunnel;

    /**
     * 科室名称转换器
     */
    @Autowired
    DeptNameTunnel deptNameTunnel;

    /**
     * 患者索引转卡号的转换器
     */
    final Converter<String, String> patientIdxToCardNoConverter = new Converter<String, String>() {
        @Override
        public String convert(String patientIdx) {
            // 尝试检索住院实体
            FinIprInMainInfo inMainInfo = inMainInfoMapper.queryInMainInfo("ZY01".concat(patientIdx));
            if (inMainInfo != null) {
                return inMainInfo.getCardNo();
            }
            return patientIdx;
        };
    };

    @ApiName("绑定陪护人")
    @RequestMapping(value = "bind", method = RequestMethod.POST, produces = MediaType.JSON)
    String bind(@RequestBody @Valid Bind.ReqBody reqBody) throws Exception {
        // 获取就诊卡号
        String patientCardNo = patientIdxToCardNoConverter.convert(reqBody.patientIdx);

        // 带锁运行业务
        var patientLock = escortLock.getHelperLock().grant(patientCardNo);
        var helperLock = escortLock.getHelperLock().grant(reqBody.helperCardNo);
        return LockExecutor.execute(Lists.newArrayList(patientLock, helperLock), () -> {
            return escortService.register(patientCardNo,
                    reqBody.helperCardNo,
                    reqBody.emplCode,
                    reqBody.regByWindow);
        });
    }

    static class Bind {
        static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "患者索引<住院号or就诊卡号>不能为空")
            @Size(message = "患者索引<住院号or就诊卡号>长度异常", min = 10, max = 10)
            String patientIdx = null;

            /**
             * 陪护人卡号
             */
            @NotBlank(message = "陪护人卡号不能为空")
            @Size(message = "陪护人卡号长度异常", min = 10, max = 10)
            String helperCardNo = null;

            /**
             * 操作员编码
             */
            @NotBlank(message = "操作员编码不能为空")
            String emplCode = null;

            /**
             * 是否为窗口操作
             */
            Boolean regByWindow = false;
        }
    }

    /**
     * 更新状态
     * 
     * @param escortNo
     * @param state
     * @param emplCode
     */
    @ApiName("更新陪护证状态")
    @RequestMapping(value = "updateState", method = RequestMethod.POST, produces = MediaType.JSON)
    void updateState(@RequestBody @Valid UpdateState.ReqBody reqBody) {
        // 加状态操作锁，防止同时操作同一个陪护证
        LockExecutor.execute(escortLock.getStateLock().grant(reqBody.escortNo), () -> {
            escortService.updateState(reqBody.escortNo, reqBody.state, reqBody.emplCode, "收到客户端请求");
        });
    }

    static class UpdateState {
        static class ReqBody {
            /**
             * 陪护证号
             */
            @NotBlank(message = "陪护证号不能为空")
            String escortNo;

            /**
             * 待设置状态
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            EscortStateRec.StateEnum state;

            /**
             * 操作员
             */
            @NotBlank(message = "操作员不能为空")
            String emplCode;
        }
    }

    /**
     * 记录动作
     * 
     * @param escortNo
     * @param action
     */
    @ApiName("记录陪护证行为")
    @RequestMapping(value = "recordAction", method = RequestMethod.POST, produces = MediaType.JSON)
    void recordAction(@RequestBody @Valid RecordAction.ReqBody reqBody) {
        // 加状态操作锁，防止同时操作同一个陪护证
        LockExecutor.execute(escortLock.getActionLock().grant(reqBody.escortNo), () -> {
            escortService.recordAction(reqBody.escortNo, reqBody.action, "收到客户端请求");
        });
    }

    static class RecordAction {
        static class ReqBody {
            /**
             * 陪护证号
             */
            @NotBlank(message = "陪护证号不能为空")
            String escortNo;

            /**
             * 待设置状态
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            EscortActionRec.ActionEnum action;
        }
    }

    /**
     * 查询陪护状态
     * 
     * @param escortNo
     * @return
     */
    @ApiName("查询陪护证状态")
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    QueryStateInfo.RspBody queryStateInfo(@RequestBody @Valid QueryStateInfo.ReqBody reqBody) {
        // 调用业务
        var escortInfo = escortMainInfoCache.get(reqBody.escortNo);
        if (escortInfo == null) {
            log.error(String.format("不存在的陪护号", reqBody.escortNo));
            throw new RuntimeException("不存在的陪护号".concat(reqBody.escortNo));
        }

        // 构造响应体
        var rspBuilder = QueryStateInfo.RspBody.builder();
        rspBuilder.patientCardNo(escortInfo.getPatientCardNo());
        rspBuilder.helperCardNo(escortInfo.getHelperCardNo());
        rspBuilder.regDate(escortStateRecMapper.queryFirstEscortStateRec(reqBody.escortNo).getRecDate());
        rspBuilder.state(escortStateRecMapper.queryLastEscortStateRec(reqBody.escortNo).getState());

        return rspBuilder.build();
    }

    static class QueryStateInfo {
        static class ReqBody {
            /**
             * 患者卡号
             */
            @NotBlank(message = "陪护证号不能为空")
            String escortNo;
        }

        @Builder
        static class RspBody {
            /**
             * 患者卡号
             */
            String patientCardNo;

            /**
             * 陪护人卡号
             */
            @SerializedName("escortCardNo")
            String helperCardNo;

            /**
             * 注册时间
             */
            LocalDateTime regDate;

            /**
             * 当前状态<枚举值>
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            StateEnum state;
        }
    }

    /**
     * 根据患者查陪护
     * 
     * @param helperCardNo
     * @return
     */
    @ApiName("查询陪护患者信息")
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryPatientInfo.RspBody> queryPatientInfo(@RequestBody @Valid QueryPatientInfo.ReqBody reqBody) {
        // 调用业务
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .helperCardNo(reqBody.helperCardNo)
                        .states(Lists.newArrayList(
                                StateEnum.无核酸检测结果,
                                StateEnum.等待院内核酸检测结果,
                                StateEnum.等待院外核酸检测结果审核,
                                StateEnum.生效中,
                                StateEnum.其他))
                        .build());
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        return escortInfos.stream().map(x -> {
            var rsp = QueryPatientInfo.RspBody.builder();
            rsp.cardNo = x.getPatientCardNo();
            var patient = patientInfoCache.get(rsp.cardNo);
            if (patient != null) {
                rsp.name = patient.getName();
                rsp.sex = patient.getSex();
                rsp.age = Period.between(patient.getBirthday().toLocalDate(), LocalDate.now());
            }
            var inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                    .cardNo(x.getPatientCardNo())
                    .happenNo(x.getHappenNo())
                    .build());
            if (inMainInfos != null && inMainInfos.size() == 1) {
                var inMainInfo = inMainInfos.get(0);
                rsp.deptName = deptNameTunnel.tunneling(inMainInfo.getDeptCode());
                rsp.bedNo = bedNoTunnel.tunneling(inMainInfo.getBedNo());
                rsp.patientNo = inMainInfo.getPatientNo();
            } else {
                var builder = FinIprPrepayInCache.Key.builder();
                builder.cardNo(x.getPatientCardNo());
                builder.happenNo(x.getHappenNo());
                var prepayIn = prepayInCache.get(builder.build());
                if (prepayIn != null) {
                    rsp.deptName = deptNameTunnel.tunneling(prepayIn.getPreDeptCode());
                    rsp.bedNo = bedNoTunnel.tunneling(prepayIn.getBedNo());
                }
            }
            var vip = escortVipCache.get(
                    EscortVipCache.Key.builder()
                            .cardNo(x.getPatientCardNo())
                            .happenNo(x.getHappenNo()).build());
            if (vip != null) {
                rsp.freeFlag = Boolean.valueOf(StringUtils.equals(vip.getHelperCardNo(), x.getHelperCardNo()));
            }
            rsp.escortNo = x.getEscortNo();
            rsp.states = escortStateRecCache.get(x.getEscortNo());
            rsp.states.sort((a, b) -> {
                return IntegerUtils.compare(a.getRecNo(), b.getRecNo());
            });
            rsp.actions = Lists.newArrayList();
            return rsp.build();
        }).toList();
    }

    static class QueryPatientInfo {
        static class ReqBody {
            /**
             * 陪护人卡号
             */
            @NotBlank(message = "陪护人卡号不能为空")
            String helperCardNo;
        }

        @Builder
        static class RspBody {
            /**
             * 就诊卡号
             */
            String cardNo;

            /**
             * 姓名
             */
            String name;

            /**
             * 性别
             */
            SexEnum sex;

            /**
             * 年龄
             */
            Period age;

            /**
             * 科室
             */
            String deptName;

            /**
             * 床号
             */
            String bedNo;

            /**
             * 住院号
             */
            String patientNo;

            /**
             * 免费标识
             */
            @JsonAdapter(value = BooleanTypeAdapter_10.class)
            Boolean freeFlag;

            /**
             * 陪护证号
             */
            String escortNo;

            /**
             * 状态列表
             */
            List<EscortStateRec> states;

            /**
             * 动作列表
             */
            List<EscortActionRec> actions;
        }
    }

    @ApiName("查询患者陪护人信息")
    @RequestMapping(value = "queryHelperInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryHelperInfo.RspBody> queryHelperInfo(@RequestBody @Valid QueryHelperInfo.ReqBody reqBody) {
        // 调用业务
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .patientCardNo(reqBody.patientCardNo)
                        .states(Lists.newArrayList(
                                StateEnum.无核酸检测结果,
                                StateEnum.等待院内核酸检测结果,
                                StateEnum.等待院外核酸检测结果审核,
                                StateEnum.生效中,
                                StateEnum.其他))
                        .build());
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        return escortInfos.stream().map(x -> {
            var rsp = QueryHelperInfo.RspBody.builder();
            rsp.cardNo = x.getHelperCardNo();
            var helper = patientInfoCache.get(x.getHelperCardNo());
            if (helper != null) {
                rsp.name = helper.getName();
                rsp.sex = helper.getSex();
                rsp.age = Period.between(helper.getBirthday().toLocalDate(), LocalDate.now());
            }
            var vip = escortVipCache.get(
                    EscortVipCache.Key.builder()
                            .cardNo(x.getPatientCardNo())
                            .happenNo(x.getHappenNo()).build());
            if (vip != null) {
                rsp.freeFlag = Boolean.valueOf(StringUtils.equals(vip.getHelperCardNo(), x.getHelperCardNo()));
            }
            rsp.escortNo = x.getEscortNo();
            rsp.states = escortStateRecCache.get(x.getEscortNo());
            rsp.states.sort((a, b) -> {
                return IntegerUtils.compare(a.getRecNo(), b.getRecNo());
            });
            rsp.actions = Lists.newArrayList();
            return rsp.build();
        }).toList();
    }

    static class QueryHelperInfo {
        static class ReqBody {
            /**
             * 患者卡号
             */
            @NotBlank(message = "患者卡号不能为空")
            String patientCardNo;
        }

        @Builder
        static class RspBody {
            /**
             * 就诊卡号
             */
            String cardNo;

            /**
             * 姓名
             */
            String name;

            /**
             * 性别
             */
            SexEnum sex;

            /**
             * 年龄
             */
            Period age;

            /**
             * 免费标识
             */
            @JsonAdapter(value = BooleanTypeAdapter_10.class)
            Boolean freeFlag;

            /**
             * 陪护证号
             */
            String escortNo;

            /**
             * 状态列表
             */
            List<EscortStateRec> states;

            /**
             * 行为列表
             */
            List<EscortActionRec> actions;
        }
    }
}
