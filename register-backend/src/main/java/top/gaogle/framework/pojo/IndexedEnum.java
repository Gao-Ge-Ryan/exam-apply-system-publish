package top.gaogle.framework.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.gaogle.framework.config.IndexedEnumSerializer;

import java.io.Serializable;

@JsonSerialize(using = IndexedEnumSerializer.class)
public interface IndexedEnum<V extends Serializable> extends Serializable {
    String name();

    V value();

    String title();
}
