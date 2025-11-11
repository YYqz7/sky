package com.sky.anno;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义公共字段自动填充注解
 */
@Target(ElementType.METHOD) // 作用对象
@Retention(RetentionPolicy.RUNTIME) // 作用范围
public @interface AutoFill {

    // 表示 修改 还是 增加
    OperationType value();
}
