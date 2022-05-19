package com.kaos.skynet.api.controller.common.dictionary.common;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.impl.AbstractController;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/common/dictionary/common")
public class QueryData extends AbstractController {
    /**
     * 查询公共字典
     */
    @RequestMapping(value = "queryData", method = RequestMethod.GET, produces = MediaType.JSON)
    public ResponseBody queryData(@NotNull(message = "字典名为空") String classType) {
        // 入参记录
        log.info(String.format("查询枚举字典: %s", classType));

        try {
            // 反射得到类型
            var targetClass = Class.forName("com.kaos.skynet.enums.common.".concat(classType));

            // 获取枚举项列表
            var enumList = Lists.newArrayList(targetClass.getEnumConstants());

            // 构造响应
            ResponseBody body = new ResponseBody();
            body.size = enumList.size();
            body.data = enumList;

            return body;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("字典不存在(common -> %s)", classType));
        }
    }

    /**
     * ResponseBody
     */
    public class ResponseBody {
        /**
         * 数量
         */
        @Setter
        private Integer size = null;

        /**
         * 院区
         */
        @Setter
        private List<?> data = null;
    }
}
