package com.kaos.skynet.core.config.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.converter.EnumToStringConverter;
import com.kaos.skynet.core.util.converter.StringToEnumConverterFactory;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.convert.converter.Converter;

public class EnumTypeHandler<E extends Enum> extends BaseTypeHandler<E> {
    /**
     * 枚举转字符串的转换器
     */
    final EnumToStringConverter<E> enumToStringConverter = new EnumToStringConverter<>(true);

    /**
     * 字符串转枚举的转换器工厂
     */
    final StringToEnumConverterFactory stringToEnumConverterFactory = new StringToEnumConverterFactory(true);

    /**
     * 字符串转枚举的转换器
     */
    final Converter<String, E> stringToEnumConverter;

    /**
     * 构造函数
     * 
     * @param typeOfE
     */
    public EnumTypeHandler(Class<E> classOfE) {
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
