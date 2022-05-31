package com.kaos.skynet.config;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.Converter;
import com.kaos.skynet.core.type.converter.ConverterFactory;
import com.kaos.skynet.core.type.converter.enums.string.ValueEnumToStringConverter;
import com.kaos.skynet.core.type.converter.string.enums.ValueStringToEnumConverterFactory;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MybatisEnumTypeHandler<E extends Enum> extends BaseTypeHandler<E> {
    /**
     * 字符串转枚举的转换器工厂
     */
    final ConverterFactory<String, Enum> stringToEnumConverterFactory = new ValueStringToEnumConverterFactory();

    /**
     * 字符串转枚举的转换器
     */
    Converter<String, E> stringToEnumConverter = null;

    /**
     * 枚举转字符串的转换器
     */
    final Converter<E, String> enumToStringConverter = new ValueEnumToStringConverter<>();

    /**
     * 构造函数
     * @param typeOfE
     */
    public MybatisEnumTypeHandler(Class<E> classOfE) {
        stringToEnumConverter = stringToEnumConverterFactory.getConverter(classOfE);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, enumToStringConverter.convert(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) == null) {
            return null;
        }
        return stringToEnumConverter.convert(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        return stringToEnumConverter.convert(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        return stringToEnumConverter.convert(cs.getString(columnIndex));
    }
}
