package com.kaos.skynet.api.service.inf.pipe.pacs;

import java.util.List;

public interface ImageService {
    /**
     * 切割指定检查的胶片
     * 
     * @param checkNo 检查号
     * @return 映射号列表
     */
    List<String> crop(String checkNo);
}
