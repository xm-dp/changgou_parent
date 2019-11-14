package com.changgou.system.filter;

import com.changgou.system.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizeFilter implements GlobalFilter, Ordered
{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        //获取请求对象
        ServerHttpRequest request = exchange.getRequest();

        //获取响应对象
        ServerHttpResponse response = exchange.getResponse();

        //判断是否为登录请求，是则放行
        if (request.getURI().getPath().contains("/admin/login")){
            return chain.filter(exchange);
        }
        //获取所有请求头
        HttpHeaders headers = request.getHeaders();

        //获取请求令牌信息
        String jwtToken = headers.getFirst("token");

        //判断令牌是否存在
        if (StringUtils.isEmpty(jwtToken)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
           return response.setComplete();
        }

        try
        {
            //解析token
            JwtUtil.parseJWT(jwtToken);
        } catch (Exception e)
        {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);

    }

    @Override
    public int getOrder()
    {
        return 0;
    }
}
