package com.kaos.skynet.api.logic.controller.inpatient.surgery;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "/api/inpatient/surgery", "/ms/inpatient/surgery" })
public class SurgeryController {

}
