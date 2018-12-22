package org.finchley.study.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志切面注解，实现对方法级的日志切面注解，如果需要该方法进行日志则在该方法前添加本注解即可。如：<code></code>
 * @author John Fang
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	String value() default "";
}

