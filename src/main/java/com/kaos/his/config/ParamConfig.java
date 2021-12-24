package com.kaos.his.config;

import com.kaos.his.config.converter.DateConverter;
import com.kaos.his.enums.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ParamConfig extends WebMvcConfigurationSupport {

    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();

        // Date格式化
        f.addConverter(new DateConverter());

        // 自定义枚举格式化
        f.addConverter(new DeptOwnEnum.EnumConverter());
        f.addConverter(new DrugItemGradeEnum.EnumConverter());
        f.addConverter(new DrugShiftTypeEnum.EnumConverter());
        f.addConverter(new DrugValidStateEnum.EnumConverter());
        f.addConverter(new EscortActionEnum.EnumConverter());
        f.addConverter(new EscortStateEnum.EnumConverter());
        f.addConverter(new InpatientStateEnum.EnumConverter());
        f.addConverter(new OutpatientStateEnum.EnumConverter());
        f.addConverter(new PreinCardStateEnum.EnumConverter());
        f.addConverter(new SexEnum.EnumConverter());
        f.addConverter(new TransTypeEnum.EnumConverter());

        return f;
    }

}