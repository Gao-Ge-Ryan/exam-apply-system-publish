package com.exam.config;


import com.exam.common.enums.EnumCode;
import com.exam.common.enums.EnumEntity;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举--数据库数据类型    转换映射配置
 *
 * @author gaoge
 * @since 2021-11-07 13:42:39
 */
public class EnumValueTypeHandler extends BaseTypeHandler<EnumCode> {
    private Class<EnumCode> type;

    public EnumValueTypeHandler(Class<EnumCode> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumCode parameter, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, (int) parameter.getCode());
    }

    @Override
    public EnumEntity getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = rs.getInt(columnName);

        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位IEnum子类
            return locateEnumCode(i);
        }
    }

    @Override
    public EnumEntity getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位IEnum子类
            return locateEnumCode(i);
        }
    }

    @Override
    public EnumEntity getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位IEnum子类
            return locateEnumCode(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     *
     * @param key 数据库中存储的自定义code属性
     * @return code对应的枚举类
     */
    private EnumEntity locateEnumCode(int key) {
        EnumCode[] enums = type.getEnumConstants();
        if (enums == null)
            throw new IllegalArgumentException(type.getSimpleName()
                    + " does not represent an enum type.");
        for (EnumCode status : enums) {
            if (status.getCode() == key) {
                EnumEntity enumEntity = new EnumEntity();
                enumEntity.setEnumCode(status);
                enumEntity.setCode(status.getCode());
                enumEntity.setMsg(status.getMsg());
                return enumEntity;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型：" + key + ",请核对" + type.getSimpleName());
    }
}
