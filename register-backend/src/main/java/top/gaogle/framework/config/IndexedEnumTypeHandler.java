package top.gaogle.framework.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import top.gaogle.framework.pojo.IndexedEnum;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@MappedTypes(IndexedEnum.class)
public class IndexedEnumTypeHandler<E extends IndexedEnum<Serializable>> extends BaseTypeHandler<E> {
    private final Class<E> type;
    //记录枚举值和枚举的对应关系
    private Map<Integer, E> enumMap;

    public IndexedEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        //配置到 <typeHandler> 初始化时，这里的 type 只是一个接口，并不是枚举，所以要特殊判断
        //下面除了第一个 setNonNullParameter 赋值不需要 enumMap，其他 3 个都需要，
        //由于正常情况下实体类不会使用 LabelValue 接口类型，所以这里没有对 null 进行特殊处理
        if (enums != null) {
            //这里将枚举值和枚举类型存入 enumMap，方便后续快速取值
            this.enumMap = new HashMap<>(enums.length);
            for (E anEnum : enums) {
                this.enumMap.put((Integer) anEnum.value(), anEnum);
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, (Integer) parameter.value());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                //取出值对应的枚举类
                return enumMap.get(i);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                //取出值对应的枚举类
                return enumMap.get(i);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            try {
                //取出值对应的枚举类
                return enumMap.get(i);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
            }
        }
    }

}
