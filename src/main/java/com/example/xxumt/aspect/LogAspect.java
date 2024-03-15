package com.example.xxumt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/7 11:29
 * @since 1.0
 */
@EnableAspectJAutoProxy
@Aspect
@Component
@Order(1)
public class LogAspect {

  /**
   * 切入点
   *
   * execution(* com.example.xxumt.service.*.*(..)) -- 在service包中定义的任意方法的执行
   * execution(* com.example.xxumt.service..*.*(..)) -- 在service包或其子包中定义的任意方法的执行
   * @Pointcut("execution(* com.example.xxumt.service..*.*(..))")
   *
   * Spring 支持如下三个逻辑运算符来组合切入点表达式
   * &&：要求连接点同时匹配两个切入点表达式
   * ||：要求连接点匹配任意个切入点表达式
   * !:：要求连接点不匹配指定的切入点表达式
   */
  @Pointcut(
      "execution(* com.example.xxumt.service..*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
  public void pointCutMethod() {}

  /**
   * 环绕通知 在方法调用前后完成自定义的行为 相当于MethodInterceptor
   * @param pjp 连接点
   * @return obj
   * @throws Throwable 异常
   */
  @Around("pointCutMethod()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("---------------");
    System.out.println("环绕通知：进入方法");
    final Object proceed = pjp.proceed();
    System.out.println("环绕通知：退出方法");
    return proceed;
  }

  /**
   * 前置通知 相当于BeforeAdvice
   */
  @Before("pointCutMethod()")
  public void doBefore() {
    System.out.println("前置通知");
  }

  /**
   * 后置通知 相当于AfterReturningAdvice
   * @param result 返回值
   */
  @AfterReturning(pointcut = "pointCutMethod()", returning = "result")
  public void doAfterReturning(String result) {
    System.out.println("后置通知, 返回值：" + result);
  }

  /**
   * 异常通知 相当于ThrowAdvice
   * @param e 异常
   */
  @AfterThrowing(pointcut = "pointCutMethod()", throwing = "e")
  public void doAfterThrowing(Exception e) {
    System.out.println("异常通知，异常：" + e.getMessage());
  }

  /**
   * 最终通知 不管是否异常，该通知都会执行
   */
  @After("pointCutMethod()")
  public void doAfter() {
    System.out.println("最终通知");
  }

}
