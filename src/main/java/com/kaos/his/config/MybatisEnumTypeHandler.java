package com.kaos.his.config;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kaos.his.enums.IEnum;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MybatisEnumTypeHandler<E extends IEnum> extends BaseTypeHandler<E> {
    /**
     * 枚举值数组
     */
    private E[] enums;

    public MybatisEnumTypeHandler() {
    }

    public MybatisEnumTypeHandler(Class<E> typeOfE) {
        if (typeOfE == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        // this.enumType = typeOfE;
        this.enums = typeOfE.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(typeOfE.getName() + " does not represent an enum type.");
        }
    }

    private E loadEnum(String index) {
        for (E e : enums) {
            if (e.getValue().equals(index)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) == null) {
            return null;
        }
        String index = rs.getString(columnName);
        return loadEnum(index);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        String index = rs.getString(columnIndex);
        return loadEnum(index);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        String index = cs.getString(columnIndex);
        return loadEnum(index);
    }
}
