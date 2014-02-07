package cz.kavan.radek.agent.bitcoin.errorhandling.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.hibernate.HibernateException;

import cz.kavan.radek.agent.bitcoin.errorhandling.exception.AgentDataAccessException;

public class ExceptionTranslationAspect {

    @Around("execution(* cz.kavan.radek.agent.bitcoin.domain..*(..))")
    public Object translateToDataAccessException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (HibernateException e) {
            throw new AgentDataAccessException(e);
        }
    }

}
