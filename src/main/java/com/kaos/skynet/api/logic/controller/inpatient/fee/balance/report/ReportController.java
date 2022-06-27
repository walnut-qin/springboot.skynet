package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.report;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.json.adapter.EnumTypeAdapter_Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@PassToken
@Validated
@RestController
@RequestMapping("/api/inpatient/fee/balance/report")
class ReportController {
    /**
     * 费用明细表
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    /**
     * 查询新医保统筹日结
     * 
     * @param reqBody
     * @return
     */
    @ApiName("查询新医保日结")
    @RequestMapping(value = "queryNewYbDayBalanceCost", method = RequestMethod.POST, produces = MediaType.JSON)
    Double queryNewYbDayBalanceCost(@RequestBody @Valid QueryNewYbDayBalanceCost.ReqBody reqBody) {
        // 检索所有结算记录
        var keyBuilder = FinIpbBalanceHeadMapper.Key.builder();
        keyBuilder.balanceOperCode(reqBody.balancer);
        keyBuilder.beginBalanceDate(reqBody.beginDate);
        keyBuilder.endBalanceDate(reqBody.endDate);
        keyBuilder.pactCode("18");
        var balances = balanceHeadMapper.queryBalanceHeads(keyBuilder.build());

        // 算和
        Double cost = balances.stream().mapToDouble(x -> {
            return Optional.fromNullable(switch (reqBody.type) {
                case PUB -> x.getPubCost();
                case PAY -> x.getPayCost();
            }).or(0.0);
        }).sum();

        return cost;
    }

    static class QueryNewYbDayBalanceCost {
        static class ReqBody {
            /**
             * 结算员
             */
            @NotBlank(message = "结算员不能为空")
            String balancer;

            /**
             * 开始时间
             */
            @NotNull(message = "开始时间不能为空")
            LocalDateTime beginDate;

            /**
             * 截至时间
             */
            @NotNull(message = "截止时间不能为空")
            LocalDateTime endDate;

            /**
             * 类型
             */
            @NotNull(message = "查询类型不能为空")
            @JsonAdapter(EnumTypeAdapter_Value.class)
            TypeEnum type;

            /**
             * 类型
             */
            @Getter
            @AllArgsConstructor
            enum TypeEnum implements Enum {
                PUB("1", "统筹"),
                PAY("2", "账户");

                /**
                 * 数据库存值
                 */
                String value;

                /**
                 * 描述存值
                 */
                String description;
            }
        }
    }
}
