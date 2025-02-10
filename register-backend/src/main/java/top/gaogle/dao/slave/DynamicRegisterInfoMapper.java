package top.gaogle.dao.slave;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.DynamicRegisterInfoModel;
import top.gaogle.pojo.param.DynamicRegisterInfoQueryParam;

import java.util.List;
import java.util.Map;

@Repository
public interface DynamicRegisterInfoMapper {

    int insertDynamic(String tableName, List<String> keys, List<Object> values);

    int deleteDynamic();

    int updateDynamic(@Param("tableName") String tableName, @Param("setClauses") List<Map<String, Object>> setClauses,
                      @Param("conditions") List<Map<String, Object>> conditions);

    List<Map<String, Object>> selectDynamic(@Param("tableName") String tableName, @Param("fields") List<String> fields,
                                            @Param("conditions") List<Map<String, Object>> conditions);

    List<Map<String, Object>> selectDynamicByQueryParam(@Param("tableName") String tableName, @Param("fields") List<String> fields,
                                                        @Param("conditions") List<Map<String, Object>> conditions, @Param("queryParam") DynamicRegisterInfoQueryParam queryParam);

    DynamicRegisterInfoModel obtainAdmissionTicket(@Param("tableName") String tableName, @Param("createBy") String createBy);

    int createTableDynamic(@Param("tableName") String tableName,
                           @Param("columns") List<Map<String, Object>> columns);

    List<DynamicRegisterInfoModel> selectBaseInfoByRegisterPublishId(@Param("tableName") String tableName, @Param("registerPublishId") String registerPublishId);

    int updateScoreByUnicode(@Param("tableName") String tableName, @Param("score") String score, @Param("updateBy") String updateBy, @Param("updateAt") Long updateAt,
                             @Param("id") String id, @Param("idNumber") String idNumber, @Param("admissionTicketNumber") String admissionTicketNumber);

    int updateStatusByCreateBy(@Param("tableName") String tableName, @Param("createBy") String createBy, @Param("status") int status);
}
