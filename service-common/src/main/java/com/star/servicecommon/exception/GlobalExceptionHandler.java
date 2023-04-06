package com.star.servicecommon.exception;

import com.star.servicecommon.domain.Result;
import com.star.servicecommon.msg.CodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.star.servicecommon.msg.CommonCodeMsg.METHOD_ARGUMENT_IN_VALID;


@Slf4j
@ControllerAdvice//控制器增强
public class GlobalExceptionHandler {

    //处理XueChengPlusException异常  此类异常是程序员主动抛出的，可预知异常
    @ResponseBody//将信息返回为 json格式
    @ExceptionHandler(BusinessException.class)//此方法捕获XueChengPlusException异常
    public Result<CodeMsg> doBusinessException(BusinessException e) {

        log.error("捕获异常：{}", e.getCodeMsg());
        e.printStackTrace();

        return Result.error(e.getCodeMsg());
    }


    //捕获不可预知异常 Exception
    @ResponseBody//将信息返回为 json格式
    @ExceptionHandler(Exception.class)//此方法捕获Exception异常
    public Result doException(Exception e) {

        log.error("捕获异常：{}", e.getMessage());
        return Result.defaultError();
    }


    @ResponseBody//将信息返回为 json格式
    @ExceptionHandler(MethodArgumentNotValidException.class)//此方法捕获MethodArgumentNotValidException异常
    public Result<CodeMsg> doMethodArgumentNotValidException(MethodArgumentNotValidException e) {

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
}
