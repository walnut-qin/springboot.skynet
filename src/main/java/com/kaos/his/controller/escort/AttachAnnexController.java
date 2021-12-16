package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;

import com.kaos.his.service.EscortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class AttachAnnexController {
    /**
     * 陪护服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 获取患者的陪护人信息
     * 
     * @param cardNo 患者就诊卡号
     * @return
     */
    @RequestMapping(value = "attachAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void Run(@RequestParam("helperCardNo") String helperCardNo, @RequestParam("picUrl") String picUrl) {
        // 入参检查
        if (helperCardNo == null || helperCardNo.isEmpty()) {
            throw new InvalidParameterException("陪护人卡号不能为空");
        }

        // 执行业务
        this.escortService.AttachAnnex(helperCardNo, picUrl);
    }
}
