package com.shinelon.deathknight.spel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author shinelon
 */
@Aspect
@Component
public class SpelAspec {

    private static final Logger logger = LoggerFactory.getLogger(SpelAspec.class);


    @Pointcut("@annotation(com.shinelon.deathknight.spel.Spel)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object after(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        ExpressionParser expressionParser = new SpelExpressionParser();
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames == null) {
            return joinPoint.proceed(args);
        }
        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            // 参数名称和参数对象设置到表达式上下文对象里,这样才能通过 #reqVo 这样的写法来引用方法参数
            evaluationContext.setVariable(paramName, args[i]);
        }
        Spel annotation = method.getAnnotation(Spel.class);
        String keySpel = annotation.keySpel();
        String valueSpel = annotation.valueSpel();
        String key = String.valueOf(expressionParser.parseExpression(keySpel).getValue(evaluationContext));
        String value = String.valueOf(expressionParser.parseExpression(valueSpel).getValue(evaluationContext));
        logger.info("keySpel:{},value:{}", keySpel, key);
        logger.info("valueSpel:{},value:{}", valueSpel, value);
        return joinPoint.proceed(args);
    }
}
