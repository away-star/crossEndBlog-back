package com.ce.serviceusercenter.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProverbRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "箴言")
    private String context;

    @Schema(description = "创作者")
    private String createPeople;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "状态")
    private Integer isActive;

}
