package com.kaos.his.controller.cache.common;

import javax.validation.constraints.NotNull;

import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.inf.ICache;
import com.kaos.inf.ICache.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/feeCode")
public class FinComFeeCodeStatCacheController {
    /**
     * 实体信息服务
     */
    @Autowired
    ICache<ReportTypeEnum, ICache<MinFeeEnum, FinComFeeCodeStat>> feeCodeStatCache;

    /**
     * 检索开关变量的值
     */
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<MinFeeEnum, FinComFeeCodeStat> show(@NotNull(message = "报表类型不能为空") ReportTypeEnum reportType) {
        return this.feeCodeStatCache.getValue(reportType).show();
    }

    /**
     * 刷新缓存
     */
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(@NotNull(message = "报表类型不能为空") ReportTypeEnum reportType,
            @NotNull(message = "最小费用编码不能为空") MinFeeEnum minFee) {
        this.feeCodeStatCache.getValue(reportType).refresh(minFee);
        return String.format("更新缓存<%s, %s>成功", reportType.getDescription(), minFee.getDescription());
    }

    /**
     * 清空缓存
     */
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.feeCodeStatCache.invalidateAll();
        return "清空缓存成功";
    }
}
