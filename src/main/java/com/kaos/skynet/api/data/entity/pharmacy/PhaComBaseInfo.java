package com.kaos.skynet.api.data.entity.pharmacy;

import com.kaos.skynet.core.type.Entity;
import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PhaComBaseInfo extends Entity {
    @Getter
    @AllArgsConstructor
    public enum DrugQualityEnum implements Enum {
        麻醉药("1", "麻醉药"), 保管药("2", "保管药"), 普药("3", "普药"), 输液("4", "输液"), 精神药品("5", "精神药品"), 毒药("6", "毒药"), 医保药("7", "医保药"),
        易制毒("8", "易制毒"), 放射性药品("9", "放射性药品"), 计划生育药品("E", "计划生育药品"), 其它("E3", "其它"), 一类精神药品("P1", "一类精神药品"),
        二类精神药品("P2", "二类精神药品"), 毒麻药("S", "毒麻药"), 大输液("T", "大输液"), 贵重药("V", "贵重药");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum DrugTypeEnum implements Enum {
        中草药("C", "中草药"), 卫生材料("D", "卫生材料"), 自制药品("E", "自制药品"), 化验药品("F", "化验药品"), 制剂用品("G", "制剂用品"), 西药("P", "西药"),
        中成药("Z", "中成药");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }
}
