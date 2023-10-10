package com.huazai.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理自定义实现
 *
 * @author huazai
 * @date 2023/10/10
 */
public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PerformanceMonitor.begin(target.getClass().getName() + "." + method.getName());
        // 方法开始前可以添加代理处理
        final Object object = method.invoke(target, args);
        // 方法结束可以添加代理处理
        PerformanceMonitor.end();
        return object;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        final IUserService userService = new UserServiceImpl();
        final MyInvocationHandler handler = new MyInvocationHandler(userService);
        final IUserService proxy = (IUserService) handler.getProxy();
        proxy.add();
    }
}

interface IUserService {
    void add();
}

class UserServiceImpl implements IUserService {

    public void add() {
        System.out.println("======== add ==========");
    }
}

class PerformanceMonitor {
    private static ThreadLocal<MethodPerformance> performanceThreadLocal =
            new ThreadLocal<MethodPerformance>();

    public static void begin(String method) {
        System.out.println("begin monitor");
        MethodPerformance mp = new MethodPerformance(method);
        performanceThreadLocal.set(mp);
    }

    public static void end() {
        System.out.println("end monitor");
        MethodPerformance mp = performanceThreadLocal.get();
        mp.printPerformance();
    }
}

class MethodPerformance {
    private long begin;
    private long end;
    private String serviceMethod;

    public MethodPerformance(String serviceMethod) {
        this.serviceMethod = serviceMethod;
        this.begin = System.currentTimeMillis();
    }

    public void printPerformance() {
        end = System.currentTimeMillis();
        long elapse = end - begin;
        System.out.println(serviceMethod + "花费" + elapse + "毫秒");
    }
}
