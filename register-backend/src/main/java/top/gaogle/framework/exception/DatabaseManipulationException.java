package top.gaogle.framework.exception;

public class DatabaseManipulationException extends RuntimeException {

    /**
     * 功能模块
     */
    private String module;

    /**
     * 功能单元
     */
    private String unit;

    /**
     * 对应的参数
     */
    private Object[] args;

    public DatabaseManipulationException(String message) {
        super(message);
    }

    public DatabaseManipulationException(String module, String unit, Throwable cause, Object... args) {
        super("数据库事务操作发生异常：", cause);
        this.module = module;
        this.unit = unit;
        this.args = args;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
