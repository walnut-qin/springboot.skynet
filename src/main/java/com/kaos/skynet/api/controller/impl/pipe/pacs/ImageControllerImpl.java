package com.kaos.skynet.api.controller.impl.pipe.pacs;

import java.awt.image.BufferedImage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.pipe.pacs.ImageController;
import com.kaos.skynet.util.Gsons;

import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/pipe/pacs/image")
public class ImageControllerImpl implements ImageController {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(ImageControllerImpl.class);

    /**
     * gson工具
     */
    Gson gson = Gsons.newGson();

    @Override
    @RequestMapping(value = "get", method = RequestMethod.POST, produces = MediaType.JSON)
    public GetRsp get(@RequestBody @Valid GetReq req) {
        // 查询原图路径
        String orgUrl = "http://fake.com";

        // 构图
        ApiParam param = new ApiParam();
        param.url = orgUrl;
        param.x = 0;
        param.y = 0;
        param.w = 10;
        param.h = 10;
        var cipherText = DigestUtils.md5DigestAsHex(this.gson.toJson(param).getBytes());
        String dstUrl = String.format("http://172.16.100.252:8025/ms/pipe/pacs/image/%s", cipherText);

        // 构造响应
        GetRsp rsp = new GetRsp();
        rsp.size = 1;
        rsp.images = Lists.newArrayList();
        rsp.images.add(dstUrl);
        return rsp;
    }

    public static class ApiParam {
        /**
         * 原图路径
         */
        public String url = null;

        /**
         * 切割起点x
         */
        public Integer x = null;

        /**
         * 切割起点y
         */
        public Integer y = null;

        /**
         * 切割宽度
         */
        public Integer w = null;

        /**
         * 切割高度
         */
        public Integer h = null;
    }

    @Override
    public BufferedImage show(@PathVariable @NotNull(message = "图片索引不能为空") String key) {
        // 获取明文

        return null;
    }
}
