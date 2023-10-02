package com.ce.servicecommon.exception;

import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.msg.CodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ce.servicecommon.msg.CommonCodeMsg.METHOD_ARGUMENT_IN_VALID;

/**
 * @author xingxing
 */
@Slf4j
@RestControllerAdvice//控制器增强
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public Result<CodeMsg> handleBusinessException(BusinessException e) {
        log.error(e.toString());
        return Result.error(e.getCodeMsg());
    }


//    @ExceptionHandler(value = {InternalAuthenticationServiceException.class})
//    public Result<CodeMsg> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
//        log.error(e.toString());
//        return Result.error(LOGIN_ERROR);
//    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result<CodeMsg> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e.getMessage());

        BindingResult bindingResult = e.getBindingResult();
        //校验的错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //收集错误
        StringBuffer errors = new StringBuffer();
        fieldErrors.forEach(error -> errors.append(error.getDefaultMessage()).append(","));
        log.error(errors.toString());
        return Result.error(METHOD_ARGUMENT_IN_VALID);
    }


    @ExceptionHandler(Exception.class)
    public Result<CodeMsg> handleException(Exception e) {
        log.error("捕获异常：{}", e.getMessage());
        return Result.defaultError();
    }
}


