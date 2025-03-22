package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.type.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 公共字段自动填充切面类
 */
@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    /**
     * 定义切点
     * com.sky.mapper 包下的任意方法 && 标记了 com.sky.annotation.AutoFill 注解
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
        // do nothing
    }

    /**
     * 前置通知
     */
    @Before("autoFillPointCut()")
    public void before(JoinPoint joinPoint) {
        log.info("AutoFillAspect: 公共字段自动填充..");

        // step1: 获取到当前执行方法的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();

        // step2: 获取当前被拦截方法的参数 ( 约定 方法的第一个参数为实体对象类型数据 )
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            log.info("AutoFillAspect: 无效的参数类型");
            return;
        }
        Object arg = args[0];

        // step3: 使用反射更新公共数据
        LocalDateTime now = LocalDateTime.now();
        Long currentUserId = BaseContext.getCurrentId();

        // 更新当前操作的时间和操作的userId
        updateAttr(arg, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class, now);
        updateAttr(arg, AutoFillConstant.SET_UPDATE_USER, Long.class, currentUserId);
        if (operationType == OperationType.INSERT) {
            // 如果是新增操作，需要更新创建时间和创建的userId
            updateAttr(arg, AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class, now);
            updateAttr(arg, AutoFillConstant.SET_CREATE_USER, Long.class, currentUserId);
        }
    }

    private void updateAttr(Object obj, String methodName, Class<?> parameterClz, Object value) {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, parameterClz);
            method.setAccessible(true);
            method.invoke(obj, value);
        } catch (Exception e) {
            log.error("AutoFillAspect: 更新公共属性失败, methodName:{}, {}", methodName, e.getMessage());
        }
    }

}
