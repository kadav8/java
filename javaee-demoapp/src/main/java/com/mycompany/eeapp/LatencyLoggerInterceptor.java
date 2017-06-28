package com.mycompany.eeapp;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@LatencyLogger
public class LatencyLoggerInterceptor {

    @AroundInvoke
    public Object computeLatency(InvocationContext invocationCtx) throws Exception {
        long startTime = System.currentTimeMillis();
        Object returnValue = invocationCtx.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Latency of " + invocationCtx.getMethod().getName() + ": " + (endTime - startTime) + "ms");
        return returnValue;
    }
}
