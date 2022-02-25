package com.kaos.his.controller.impl.cache.common;

import javax.validation.constraints.NotBlank;

import com.kaos.his.controller.inf.cache.CacheController;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.inf.ICache;
import com.kaos.inf.ICache.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/dept")
public class DawnOrgDeptCacheControllerImpl implements CacheController<String, DawnOrgDept> {
    /**
     * 实体信息服务
     */
    @Autowired
    ICache<String, DawnOrgDept> deptCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<String, ?> show() {
        return this.deptCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(@NotBlank(message = "键值不能为空") String key) {
        this.deptCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refreshAll() {
        this.deptCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.deptCache.invalidateAll();
        return "清空缓存成功";
    }
}
