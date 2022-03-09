package com.kaos.skynet.api.service.impl.pipe.pacs;

import java.util.List;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.pipe.pacs.PacsCropImageRecMapper;
import com.kaos.skynet.api.service.inf.pipe.pacs.ImageService;
import com.kaos.skynet.entity.pipe.pacs.PacsCropImageRec;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class ImageServiceImpl implements ImageService {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(ImageServiceImpl.class);

    /**
     * 切割记录接口
     */
    @Autowired
    PacsCropImageRecMapper cropImageRecMapper;

    /**
     * 原始图像缓存
     */
    @Autowired
    Cache<String, BufferedImage> pacsImageCache;

    @Override
    public List<String> crop(String checkNo) {
        // 获取原始路径与切割数量
        var url = "";
        var wCnt = 1;
        var hCnt = 1;

        // 构造响应体
        List<String> ret = Lists.newArrayList();

        // 切割图片
        try {
            // 截取原图尺寸
            var img = this.pacsImageCache.getValue(url);
            var w = img.getWidth() / wCnt;
            var h = img.getHeight() / hCnt;

            // 切割
            for (int i = 0; i < wCnt; i++) {
                for (int j = 0; j < hCnt; j++) {
                    // 插入记录映射表
                    PacsCropImageRec rec = new PacsCropImageRec();
                    rec.url = url;
                    rec.x = i * w;
                    rec.y = j * h;
                    rec.w = w;
                    rec.h = h;
                    this.cropImageRecMapper.insertRec(rec);
                    // 插入结果集
                    ret.add(rec.refer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ret;
    }
}
