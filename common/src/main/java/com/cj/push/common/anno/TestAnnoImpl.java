package com.cj.push.common.anno;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author yuchuanWeng
 * @date 2018/8/6
 * @since 1.0
 */
@Aspect
@Slf4j
@Component
public class TestAnnoImpl {

    @Pointcut("@annotation(com.cj.push.common.anno.TestAnno)")
    public void cut() {
        //
        log.info("方法执行完回调");
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        System.out.print("接受方法：" + method.getName() + " 前置日志");
    }

    @After("cut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        Arrays.stream(joinPoint.getArgs()).forEach(x -> {
            log.info("param={}", JSON.toJSONString(x));
        });
        System.out.print(method.getName() + "执行完毕");
    }

    @AfterReturning(value="cut()",
            returning="result")
    public void afterReturning(JoinPoint joinPoint,Object result) {

        String methodNames = joinPoint.getSignature().getName();

        System.out.println("The method "+methodNames+" end with "+ JSON.toJSONString(result));

    }

}
