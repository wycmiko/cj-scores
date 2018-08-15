package com.cj.push.common.anno;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author yuchuanWeng
 * @date 2018/8/6
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestAnno {
    String value() default "test";
}
