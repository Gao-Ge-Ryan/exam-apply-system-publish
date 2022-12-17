package com.gaoge.intercepter;

import com.gaoge.common.ApiEnumCode;
import com.gaoge.common.Result;
import com.gaoge.entity.FileException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@Component
@ControllerAdvice
@ResponseBody
@CrossOrigin
public class GlobalExceptionInterceptor {
    @ExceptionHandler(Exception.class)
    public  Result<String> allException(Exception e) {
        FileException fileException = new FileException(e.getMessage());
        return  Result.failed(ApiEnumCode.FAILED,fileException.getPrintException());
    }
}
