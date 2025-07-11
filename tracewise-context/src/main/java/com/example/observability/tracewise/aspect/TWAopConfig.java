package com.example.observability.tracewise.aspect;

import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TWAopConfig {

	@Bean
	public Advisor dynamicAdvisor(@Value("${tracewise.trace.aspect.log.package}") String basePackage) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* " + basePackage + "..*(..))");

		return new DefaultPointcutAdvisor(pointcut, (MethodBeforeAdvice) (method, args, target) -> {
			log.info("{},{}", target.getClass().getName(), method.getName());
		});

	}

}
