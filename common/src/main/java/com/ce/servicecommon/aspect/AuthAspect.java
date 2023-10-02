package com.ce.servicecommon.aspect;

import com.ce.servicecommon.annotations.PreAuth;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: xingxing
 * @create: 2023-10-02 09:52
 * @Description: 切面类
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {


    @Pointcut("@annotation(com.ce.servicecommon.annotations.PreAuth)")
    public void authAspect() {
    }


    @Before("authAspect()")
    public void doBefore(JoinPoint joinPoint) {

        log.info("====>日志记录 doBefore");

        Object[] args = joinPoint.getArgs();
        log.info("====>JoinPoint 获取参数：{}", JSONArray.toJSONString(Arrays.asList(args)));
        Object target = joinPoint.getTarget();
        log.info("====>JoinPoint 被代理目标对象：{}", target);
        Object proxyObject = joinPoint.getThis();
        log.info("====>JoinPoint 代理对象：{}", proxyObject);

        //// 封装了署名信息的对象，在该对象中可以获取到目标方法名，所属类的Class等信息
        //Signature signature = joinPoint.getSignature();
        //log.info("====>JoinPoint 封装的署名信息的对象：{}", JSONObject.toJSONString((Map<String, ? extends Object>) signature));
        //String name = signature.getName();
        //log.info("====>JoinPoint signature 目标方法名：{}", name);
        //String declaringTypeName = signature.getDeclaringTypeName();
        //log.info("====>JoinPoint signature 目标方法所属类的类名：{}", declaringTypeName);
        //Class declaringType = signature.getDeclaringType();
        //log.info("====>JoinPoint signature 目标方法所属类的类型名：{}", declaringType);
        //String simpleName = declaringType.getSimpleName();
        //log.info("====>JoinPoint signature 目标方法所属类的简单类名：{}", simpleName);
        //int modifiers = declaringType.getModifiers();
        //log.info("====>JoinPoint signature 目标方法声明类型：{}，{}", modifiers, Modifier.toString(modifiers));

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        log.info("====>请求地址：{}", url);
        String ip = request.getRemoteAddr();
        log.info("====>请求ip：{}", ip);

        // 权限认证
        String token = request.getHeader("Authorization");
        TokenClaim tokenClaim = JwtUtil.parseJWT(token);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PreAuth preAuth = method.getAnnotation(PreAuth.class);
        boolean b = tokenClaim.getPowerCodeList().stream().anyMatch(e -> e.toString().equals(String.valueOf(preAuth.auth())));
        if (!b) {
            throw new RuntimeException("权限不足");
        }

    }

    @Around("authAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====>日志记录 Around，环绕");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PreAuth preAuth = method.getAnnotation(PreAuth.class);
        String content = String.valueOf(preAuth.auth());
        log.info("====>日志记录 Around，注解：{}，方法：{}, 注解内容：{}", preAuth, method, content);
        return joinPoint.proceed();
    }

    @After("authAspect()")
    public void doAfter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getName();
        log.info("====>日志记录 After，方法 {} 执行后", classMethod);
    }

    @AfterReturning(pointcut = "authAspect()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getName();
        log.info("====>日志记录 AfterReturning，方法 {} 执行后，返回值：{}", classMethod, result);
    }
}