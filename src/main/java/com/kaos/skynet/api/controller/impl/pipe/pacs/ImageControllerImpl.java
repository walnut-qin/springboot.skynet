package com.kaos.skynet.api.controller.impl.pipe.pacs;

import java.awt.image.BufferedImage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.gson.Gson;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.pipe.pacs.ImageController;
import com.kaos.skynet.api.service.inf.pipe.pacs.ImageService;
import com.kaos.skynet.util.Gsons;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 图片业务
     */
    @Autowired
    ImageService imageService;

    /**
     * 图片缓存
     */
    @Autowired
    Cache<String, BufferedImage> imageRecCache;

    @Override
    @RequestMapping(value = "get", method = RequestMethod.POST, produces = MediaType.JSON)
    public GetRsp get(@RequestBody @Valid GetReq req) {
        // 记录日志
        this.logger.info(String.format("获取检查的胶片: req = %s", this.gson.toJson(req)));

        // 调用业务
        var refers = this.imageService.crop(req.checkNo);

        // 构造响应
        GetRsp rsp = new GetRsp();
        rsp.size = refers.size();
        rsp.images = refers;
        return rsp;
    }

    @Override
    public BufferedImage show(@PathVariable @NotNull(message = "图片索引不能为空") String key) {
        return this.imageRecCache.getValue(key);
    }
}
