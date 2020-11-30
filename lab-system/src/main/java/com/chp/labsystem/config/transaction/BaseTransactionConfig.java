package com.chp.labsystem.config.transaction;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/** 类功能描述：事务配置 */
public class BaseTransactionConfig {

    private String pointcut;

    public TransactionInterceptor buildTransactionInterceptor(TransactionManager transactionManager) {
        DefaultTransactionAttribute requiredAttrubite = buildRequiredTransactionAttribute();
        DefaultTransactionAttribute readOnlyAttribute = buildReadOnlyTransactionAttribute();
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("get*", readOnlyAttribute);
        source.addTransactionalMethod("is*", readOnlyAttribute);
        source.addTransactionalMethod("check*", readOnlyAttribute);
        source.addTransactionalMethod("build*", readOnlyAttribute);
        source.addTransactionalMethod("*", requiredAttrubite);
        return new TransactionInterceptor(transactionManager, source);
    }

    public Advisor buldAopConfig(TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(getPointcut());
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }

    private DefaultTransactionAttribute buildRequiredTransactionAttribute() {
        DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
        attribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        attribute.setReadOnly(false);
        return attribute;
    }

    private DefaultTransactionAttribute buildReadOnlyTransactionAttribute() {
        DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
        attribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        attribute.setReadOnly(true);
        return attribute;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }
}
