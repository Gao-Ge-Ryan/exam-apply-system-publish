package top.gaogle.framework.util;


import top.gaogle.common.RegisterConst;
import top.gaogle.framework.config.SnowflakeIdGenerator;

public class UniqueUtil {

    private UniqueUtil() {
        throw new IllegalStateException(RegisterConst.PROHIBIT_INSTANTIATION);
    }

    public static final long WORKER_ID = 1;

    private static final long DATACENTER_ID = 1;

    private static final SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(WORKER_ID, DATACENTER_ID);

    public static String getUniqueId() {
        return String.valueOf(snowflakeIdGenerator.nextId());
    }
}
