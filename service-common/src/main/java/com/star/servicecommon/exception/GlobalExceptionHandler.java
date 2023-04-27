package com.star.servicecommon.exception;

import com.star.servicecommon.domain.Result;
import com.star.servicecommon.msg.CodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.star.servicecommon.msg.CommonCodeMsg.METHOD_ARGUMENT_IN_VALID;


@Slf4j
@RestControllerAdvice//控制器增强
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public Result<CodeMsg> handleBusinessException(BusinessException e) {

        log.error(e.toString());
        return Result.error(e.getCodeMsg());
    }



    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result<CodeMsg> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        //校验的错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //收集错误
        StringBuffer errors = new StringBuffer();
        fieldErrors.forEach(error -> {
            errors.append(error.getDefaultMessage()).append(",");
        });
        log.error(errors.toString());
        return Result.error(METHOD_ARGUMENT_IN_VALID);
    }

    //捕获不可预知异常 Exception
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("捕获异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.defaultError();
    }
}


