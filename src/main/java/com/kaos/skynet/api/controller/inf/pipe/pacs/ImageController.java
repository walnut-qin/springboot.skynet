package com.kaos.skynet.api.controller.inf.pipe.pacs;

import java.awt.image.BufferedImage;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ImageController {
    /**
     * 查询胶片图片
     * 
     * @param req
     * @return
     */
    GetRsp get(@Valid GetReq req);

    public static class GetReq {
        /**
         * 检查号
         */
        public String testNo = null;
    }

    public static class GetRsp {
        /**
         * 图片数量
         */
        public Integer size = null;

        /**
         * 图片内容
         */
        public List<String> images = null;
    }

    /**
     * 获取图片
     * 
     * @param key
     * @return
     */
    BufferedImage show(@NotNull(message = "图片索引不能为空") String key);
}
