package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortLock;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortPool;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.annex.EscortAnnexCheckCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.annex.EscortAnnexInfoCache;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo.InStateEnum;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;
import com.kaos.skynet.api.data.his.tunnel.PatientNameTunnel;
import com.kaos.skynet.api.logic.service.inpatient.escort.AnnexService;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.converter.StringToBooleanConverter;
import com.kaos.skynet.core.util.thread.lock.LockExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import lombok.Builder;
import net.coobird.thumbnailator.Thumbnails;

@PassToken
@Validated
@RestController
@RequestMapping("api/inpatient/escort/annex")
public class AnnexController {
    /**
     * 陪护锁
     */
    @Autowired
    EscortPool escortPool;

    /**
     * 陪护锁
     */
    @Autowired
    EscortLock escortLock;

    /**
     * 附件服务
     */
    @Autowired
    AnnexService annexService;

    /**
     * 陪护服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 附件缓存
     */
    @Autowired
    EscortAnnexInfoMapper annexInfoMapper;

    /**
     * 陪护主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 附件信息缓存
     */
    @Autowired
    EscortAnnexInfoCache annexInfoCache;

    /**
     * 附件审核缓存
     */
    @Autowired
    EscortAnnexCheckCache annexCheckCache;

    /**
     * 字符串转Boolean对象
     */
    StringToBooleanConverter stringToBooleanConverter = new StringToBooleanConverter("1", "0");

    /**
     * 患者姓名转换器
     */
    @Autowired
    PatientNameTunnel patientNameTunnel;

    /**
     * 上传附件
     * 
     * @param reqBody
     * @return
     */
    @ApiName("上传附件")
    @RequestMapping(value = "uploadAnnex", method = RequestMethod.POST, produces = MediaType.JSON)
    String uploadAnnex(@RequestBody @Valid UploadAnnex.ReqBody reqBody) {
        // 调用服务 - 不存在冲突，无需加锁
        var annexNo = annexService.uploadAnnex(reqBody.cardNo, reqBody.url);

        // 查询所有关联陪护证
        var escortBuilder = EscortMainInfoMapper.Key.builder();
        escortBuilder.helperCardNo(reqBody.cardNo);
        escortBuilder.states(Lists.newArrayList(
                StateEnum.无核酸检测结果,
                StateEnum.等待院内核酸检测结果,
                StateEnum.等待院外核酸检测结果审核,
                StateEnum.生效中,
                StateEnum.其他));
        var escorts = escortMainInfoMapper.queryEscortMainInfos(escortBuilder.build());
        if (!escorts.isEmpty()) {
            for (var escort : escorts) {
                escortPool.getStateMgr().execute(() -> {
                    var stateLock = escortLock.getStateLock().grant(escort.getEscortNo());
                    // 更新关联陪护状态
                    LockExecutor.execute(stateLock, () -> {
                        escortService.updateState(escort.getEscortNo(), null, "WebApi", null);
                    });
                });
            }
        }

        return annexNo;
    }

    static class UploadAnnex {
        static class ReqBody {
            /**
             * 陪护人卡号
             */
            @NotBlank(message = "卡号不能为空")
            @Size(min = 10, max = 10, message = "卡号长度异常")
            String cardNo;

            /**
             * 附件链接
             */
            @NotBlank(message = "附件不能为空")
            String url;
        }
    }

    @ApiName("审核附件")
    @RequestMapping(value = "checkAnnex", method = RequestMethod.POST, produces = MediaType.JSON)
    String checkAnnex(@RequestBody @Valid CheckAnnex.ReqBody reqBody) {
        // 加状态操作锁，防止同时操作同一个陪护证
        LockExecutor.execute(escortLock.getAnnexLock().grant(reqBody.annexNo), () -> {
            annexService.checkAnnex(reqBody.annexNo, reqBody.checker, reqBody.negative, reqBody.inspectDate);
        });

        // 查询所有关联陪护证
        var escortBuilder = EscortMainInfoMapper.Key.builder();
        escortBuilder.helperCardNo(annexInfoCache.get(reqBody.annexNo).getCardNo());
        escortBuilder.states(Lists.newArrayList(
                StateEnum.无核酸检测结果,
                StateEnum.等待院内核酸检测结果,
                StateEnum.等待院外核酸检测结果审核,
                StateEnum.生效中,
                StateEnum.其他));
        var escorts = escortMainInfoMapper.queryEscortMainInfos(escortBuilder.build());
        if (!escorts.isEmpty()) {
            for (var escort : escorts) {
                escortPool.getStateMgr().execute(() -> {
                    var stateLock = escortLock.getStateLock().grant(escort.getEscortNo());
                    // 更新关联陪护状态
                    LockExecutor.execute(stateLock, () -> {
                        escortService.updateState(escort.getEscortNo(), null, "WebApi", "患者上传外院报告");
                    });
                });
            }
        }

        return reqBody.annexNo;
    }

    static class CheckAnnex {
        static class ReqBody {
            /**
             * 附件号
             */
            @NotBlank(message = "附件号不能为空")
            String annexNo;

            /**
             * 审核员
             */
            @NotBlank(message = "审核员不能为空")
            String checker;

            /**
             * 阴性标识
             */
            @NotNull(message = "阴性标识不能为空")
            Boolean negative;

            /**
             * 核酸检测时间
             */
            @NotNull(message = "核酸检测时间不能为空")
            LocalDateTime inspectDate;
        }
    }

    @ApiName("查询科室的陪护信息")
    @RequestMapping(value = "queryAnnexs", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryAnnexs.RspBody> queryAnnexs(@RequestBody @Valid QueryAnnexs.ReqBody reqBody) {
        // 检索该科室所有患者
        var inMainInfoBuilder = FinIprInMainInfoMapper.Key.builder();
        inMainInfoBuilder.deptCode(reqBody.deptCode);
        inMainInfoBuilder.states(Lists.newArrayList(InStateEnum.病房接诊));
        var inMainInfos = inMainInfoMapper.queryInMainInfos(inMainInfoBuilder.build());

        // 查询所有有效的陪护人
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNos(inMainInfos.stream().map(x -> {
                    return x.getCardNo();
                }).toList())
                .states(Lists.newArrayList(StateEnum.无核酸检测结果,
                        StateEnum.等待院内核酸检测结果,
                        StateEnum.等待院外核酸检测结果审核,
                        StateEnum.生效中,
                        StateEnum.其他))
                .build());

        // 轮询陪护记录
        Multimap<String, String> escortRelation = ArrayListMultimap.create();
        for (EscortMainInfo escortMainInfo : escortInfos) {
            // 登记陪护关系
            escortRelation.put(escortMainInfo.getHelperCardNo(), escortMainInfo.getPatientCardNo());
        }

        // 构造所有附件信息
        List<EscortAnnexInfo> annexInfos = Lists.newArrayList();
        for (String helperCardNo : escortRelation.keySet()) {
            annexInfos.addAll(annexInfoMapper.queryAnnexInfos(EscortAnnexInfoMapper.Key.builder()
                    .cardNo(helperCardNo)
                    .checked(reqBody.checked).build()));
        }

        // 映射到响应体
        var builder = QueryAnnexs.RspBody.builder();
        var urlPrefix = "http://172.16.100.252:8025/api/inpatient/escort/annex/getPic?refer=";
        return annexInfos.stream().map(x -> {
            builder.annexNo(x.getAnnexNo());
            builder.helperName(patientNameTunnel.tunneling(x.getCardNo()));
            builder.picUrl(urlPrefix.concat(x.getAnnexNo()));
            builder.patientNames(Set.copyOf(escortRelation.get(x.getCardNo())).stream().map(y -> {
                return patientNameTunnel.tunneling(y);
            }).toList());
            // 若查询审核内容，则添加审核信息
            if (reqBody.checked) {
                builder.negative(annexCheckCache.get(x.getAnnexNo()).getNegative());
                builder.inspectDate(annexCheckCache.get(x.getAnnexNo()).getInspectDate());
            }
            return builder.build();
        }).toList();
    }

    static class QueryAnnexs {
        static class ReqBody {
            @NotBlank(message = "科室编码不能为空")
            String deptCode;

            @NotNull(message = "审核结果不能为空")
            Boolean checked;
        }

        @Builder
        static class RspBody {
            /**
             * 附件编码
             */
            String annexNo;

            /**
             * 陪护人姓名
             */
            String helperName;

            /**
             * 附件链接
             */
            String picUrl;

            /**
             * 被陪护患者列表
             */
            List<String> patientNames;

            /**
             * 审核结果
             */
            Boolean negative;

            /**
             * 检验日期
             */
            LocalDateTime inspectDate;
        }
    }

    @RequestMapping(value = "getPic", method = RequestMethod.GET, produces = MediaType.JPEG)
    BufferedImage getPic(@NotNull(message = "键值不能为空") String refer) throws IOException {
        // 根据refer号获取annexUrl
        var rec = annexInfoCache.get(refer);
        if (rec == null) {
            throw new RuntimeException("未查询到附件记录");
        }

        // 读取图片并返回
        return Thumbnails.of(new URL(rec.getAnnexUrl())).scale(1.0f).asBufferedImage();
    }
}
