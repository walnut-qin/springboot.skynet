package com.kaos.skynet.api.logic.controller.inpatient.surgery.dictionary;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;
import com.kaos.skynet.api.logic.service.inpatient.surgery.dictionary.SurgeryDictionaryService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.thread.lock.LockExecutor;
import com.kaos.skynet.core.util.thread.lock.LockGuardian;

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
@RequestMapping("/api/inpatient/surgery/dictionary")
public class DictionaryController {
    /**
     * 手术字典锁
     */
    final LockGuardian surgeryLockGuardian = new LockGuardian("手术字典锁", 50);

    /**
     * 手术字典接口
     */
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    /**
     * 手术字典业务
     */
    @Autowired
    SurgeryDictionaryService surgeryDictionaryService;

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

    @ApiName("添加手术字典")
    @RequestMapping(value = "addDictionary", method = RequestMethod.POST, produces = MediaType.JSON)
    public Object addDictionary(@RequestBody @Valid AddDictionary.ReqBody reqBody) {
        // 执行业务
        LockExecutor.execute(Lists.newArrayList(surgeryLockGuardian.grant(reqBody.icdCode)), () -> {
            surgeryDictionaryService.insertSurgeryDict(reqBody.icdCode,
                    reqBody.name,
                    reqBody.level,
                    reqBody.teleprompter);
        });

        return ObjectUtils.emptyObject;
    }

    static class AddDictionary {
        static class ReqBody {
            /**
             * ICD-9编码
             */
            @NotBlank(message = "ICD-9编码不能为空")
            String icdCode;

            /**
             * 手术名
             */
            @NotBlank(message = "手术名称不能为空")
            String name;

            /**
             * 手术等级
             */
            @NotNull(message = "手术分级不能为空")
            SurgeryLevelEnum level;

            /**
             * 提词器
             */
            @NotBlank(message = "提词器不能为空")
            String teleprompter;
        }
    }

    @ApiName("修改手术字典")
    @RequestMapping(value = "modifyDictionary", method = RequestMethod.POST, produces = MediaType.JSON)
    public Object modifyDictionary(@RequestBody @Valid ModifyDictionary.ReqBody reqBody) {
        // 执行业务
        LockExecutor.execute(Lists.newArrayList(surgeryLockGuardian.grant(reqBody.icdCode)), () -> {
            surgeryDictionaryService.updateSurgeryDict(reqBody.icdCode,
                    reqBody.name,
                    reqBody.level,
                    reqBody.teleprompter);
        });

        return ObjectUtils.emptyObject;
    }

    static class ModifyDictionary {
        static class ReqBody {
            /**
             * ICD-9编码
             */
            @NotBlank(message = "ICD-9编码不能为空")
            String icdCode;

            /**
             * 手术名
             */
            @NotBlank(message = "手术名称不能为空")
            String name;

            /**
             * 手术等级
             */
            @NotNull(message = "手术分级不能为空")
            SurgeryLevelEnum level;

            /**
             * 提词器
             */
            @NotBlank(message = "提词器不能为空")
            String teleprompter;
        }
    }

    @ApiName("删除手术字典")
    @RequestMapping(value = "removeDictionary", method = RequestMethod.POST, produces = MediaType.JSON)
    public Object removeDictionary(@RequestBody @Valid RemoveDictionary.ReqBody reqBody) {
        // 执行业务
        LockExecutor.execute(Lists.newArrayList(surgeryLockGuardian.grant(reqBody.icdCode)), () -> {
            surgeryDictionaryService.deleteSurgeryDict(reqBody.icdCode);
        });

        return ObjectUtils.emptyObject;
    }

    static class RemoveDictionary {
        static class ReqBody {
            /**
             * ICD-9编码
             */
            @NotBlank(message = "ICD-9编码不能为空")
            String icdCode;
        }
    }
}
