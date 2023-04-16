package org.sapient.newsaggregator.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(public * org.sapient.newsaggregator.controller.*Controller.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replaceAll("[\n|\r|\t]", "_"); // Replace pattern-breaking characters
        LOG.info("*** Request URL: {}", requestURI);
        if (Objects.nonNull(pathVariables) && pathVariables.size() > 0) {
            LOG.info("*** Request Params: {}", pathVariables);
        }
        Object result = joinPoint.proceed();
        stopWatch.stop();
        LOG.info("*** Response Body: {}", objectMapper.writeValueAsString(result));
        LOG.info("*** Request Execution Time: {} ms", stopWatch.getTotalTimeMillis());
        return result;
    }
}
