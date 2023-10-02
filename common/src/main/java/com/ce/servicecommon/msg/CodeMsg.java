package com.ce.servicecommon.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by ce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeMsg implements Serializable {
    private Integer code;
    private String msg;
}
