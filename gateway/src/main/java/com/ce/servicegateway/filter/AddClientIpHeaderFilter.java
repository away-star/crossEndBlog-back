package com.ce.servicegateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

import static com.ce.servicecommon.constant.Security.realIpKey;

/**
 * @author xingxing
 * @date 2023/5/7 17:10
 */
@Component
@Slf4j
public class AddClientIpHeaderFilter implements GlobalFilter {

    /**
     * 确保ip的准确性
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.error("AddClientIpHeaderFilter真实ip获取");
        InetSocketAddress address = exchange.getRequest().getRemoteAddress();
        String remoteIp = address != null ? address.getAddress().getHostAddress() : "Unknown";
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(realIpKey, remoteIp)
                .build();
        System.out.println(exchange.getRequest().mutate().header(realIpKey));
        return chain.filter(exchange.mutate().request(request).build());
    }
}