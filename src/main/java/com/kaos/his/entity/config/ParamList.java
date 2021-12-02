package com.kaos.his.entity.config;

import java.util.List;

/**
 * 集合参数
 */
public class ParamList {
    /**
     * 参数列表
     */
    public static class Param {
        /**
         * 参数状态
         */
        public String value = null;

        /**
         * 参数有效性
         */
        public Boolean valid = null;

        /**
         * 参数备注
         */
        public String remark = null;
    }

    /**
     * 参数名称
     */
    public String name = null;

    /**
     * 参数列表
     */
    public List<Param> params = null;
}
