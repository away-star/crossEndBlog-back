package com.star.servicegateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * @author star
 * @date 2023/5/7 17:10
 */
@Component
public class AddClientIpHeaderFilter implements GlobalFilter {

    private static final String CLIENT_IP_HEADER = "X-Forwarded-For";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        InetSocketAddress address = exchange.getRequest().getRemoteAddress();
        String remoteIp = address != null ? address.getAddress().getHostAddress() : "Unknown";
        System.out.println(remoteIp);
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(CLIENT_IP_HEADER, remoteIp)
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }
}