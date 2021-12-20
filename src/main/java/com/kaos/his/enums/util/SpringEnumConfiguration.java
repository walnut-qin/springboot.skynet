package com.kaos.his.enums.util;

import com.kaos.his.enums.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 枚举配置，注册相关枚举的转换器，以便入参解析器解析
 */
@Configuration
public class SpringEnumConfiguration extends WebMvcConfigurationSupport {
    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();
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
