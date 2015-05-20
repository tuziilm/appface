package com.zhanghui.appface.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注多个ID的参数，如："12-23-123-1234"
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ids {
	String value() default "";
}
