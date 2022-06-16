package com.kaos.skynet.api.logic.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Validated
@RestController
@RequestMapping("api/test")
public class TestController {
    /**
     * Excel测试
     */
    @RequestMapping(value = "excelTest", method = RequestMethod.GET)
    public void excelTest(HttpServletResponse response) {
        List<Pojo> data = Lists.newArrayList();
        data.add(new Pojo("张三", "第一条"));
        data.add(new Pojo("李四", "第二条"));

        ExportParams params = new ExportParams();
        params.setTitle("测试文件");
        params.setSheetName("表单1");
        params.setType(ExcelType.XSSF);
        params.setCreateHeadRows(true);

        var workBook = ExcelExportUtil.exportExcel(params, Pojo.class, data);
        if (workBook == null) {
            return;
        }

        response.reset();

        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        try {
            OutputStream output = response.getOutputStream();
            workBook.write(output);
            output.close();
        } catch (Exception e) {

        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Pojo {
        /**
         * 姓名
         */
        @Excel(name = "姓名", orderNum = "0", width = 15)
        private String name;

        /**
         * 备注
         */
        @Excel(name = "备注", orderNum = "1", width = 30)
        private String remark;
    }
}
