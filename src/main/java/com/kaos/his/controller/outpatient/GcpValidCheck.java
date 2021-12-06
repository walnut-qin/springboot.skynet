package com.kaos.his.controller.outpatient;

import com.kaos.his.service.OutpatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class GcpValidCheck {
    /**
     * 门诊业务
     */
    @Autowired
    OutpatientService outpatientService;

    /**
     * 检查科室是否为GCP允许的科室；检查患者是否为GCP患者
     * 
     * @param deptCode
     * @param clinicCode
     * @return
     */
    @RequestMapping(value = "gcpValidCheck", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String Run(@RequestParam("deptCode") String deptCode, @RequestParam("clinicCode") String clinicCode) {
        if (deptCode == null || clinicCode == null) {
            return Boolean.toString(false);
        }
        return this.outpatientService.CheckGcpValid(deptCode, clinicCode).toString();
    }
}
