package com.kaos.skynet.config;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kaos.skynet.config.converter.EnumTypeConverter;
import com.kaos.skynet.enums.Enum;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MybatisEnumTypeHandler<E extends Enum> extends BaseTypeHandler<E> {
    /**
     * 枚举转换器
     */
    EnumTypeConverter<E> enumTypeConverter = null;

    public MybatisEnumTypeHandler(Class<E> typeOfE) {
        // 构造转换器实体
        this.enumTypeConverter = new EnumTypeConverter<>(typeOfE, true);
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
        return this.enumTypeConverter.convert(index);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        String index = rs.getString(columnIndex);
        return this.enumTypeConverter.convert(index);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        String index = cs.getString(columnIndex);
        return this.enumTypeConverter.convert(index);
    }
}
