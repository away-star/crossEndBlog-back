package com.ce.servicecommon.exception;


import com.ce.servicecommon.msg.CodeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ce
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private CodeMsg codeMsg;
    public BusinessException(CodeMsg codeMsg){
        this.codeMsg = codeMsg;
    }
}
