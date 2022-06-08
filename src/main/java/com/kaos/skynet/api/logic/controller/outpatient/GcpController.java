package com.kaos.skynet.api.logic.controller.outpatient;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "api/outpatient/gcp", "/ms/inpatient/escort/annex", "/ms/inpatient/escort" })
public class GcpController {
    
}
