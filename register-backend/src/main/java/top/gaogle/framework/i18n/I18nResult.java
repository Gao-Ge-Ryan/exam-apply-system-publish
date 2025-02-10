package top.gaogle.framework.i18n;


import org.springframework.http.ResponseEntity;
import top.gaogle.pojo.enums.HttpStatusEnum;

import java.io.Serializable;

/**
 * 国际化返回结果封装
 *
 * @author goge
 * @since 1.0.0
 */
public class I18nResult<T> implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private Boolean success;
    private Integer code = 200;
    private HttpStatusEnum status = HttpStatusEnum.OK;
    private String message = "success";
    private transient T data;

    public static <T> I18nResult<T> newInstance() {
        return new I18nResult<>();
    }

    public Integer getCode() {
        return code;
    }

    public I18nResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public I18nResult<T> setMessage(String msg) {
        this.message = I18nMessageConfig.getMessage(I18ResultCode.MESSAGE, msg);
        return this;
    }


    public I18nResult<T> setMessage(String code, Object... args) {
        this.message = I18nMessageConfig.getMessage(code, args);
        return this;
    }

    public T getData() {
        return data;
    }

    public I18nResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public I18nResult<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public I18nResult<T> succeed() {
        this.success = true;
        setStatus(HttpStatusEnum.OK);
        return this;
    }

    public I18nResult<T> failed() {
        this.success = false;
        setStatus(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        return this;
    }

    public I18nResult<T> failedBadRequest() {
        this.success = false;
        setStatus(HttpStatusEnum.BAD_REQUEST);
        return this;
    }

    public HttpStatusEnum getStatus() {
        return status;
    }

    public I18nResult<T> setStatus(HttpStatusEnum status) {
        this.status = status;
        this.code = status.getValue();
        return this;
    }

    public ResponseEntity<I18nResult<T>> toResponseEntity() {
        return ResponseEntity.status(code).body(this);
    }

}
