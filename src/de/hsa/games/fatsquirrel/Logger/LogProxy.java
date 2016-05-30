package de.hsa.games.fatsquirrel.Logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by max on 30.05.16 14:13.
 */
public class LogProxy implements InvocationHandler {
    private Object target;

    public static Logger logger = Logger.getLogger(LogProxy.class.getName());


    public LogProxy (Object target)
    {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        long t1 = System.nanoTime();
        result = method.invoke(target, args);
        long t2 = System.nanoTime();

        if((t2-t1 >= 0))
        {
            logger.log(Level.INFO, "It takes " + (t2 - t1) + " nanoseconds to invoke " + method.getName() + " in " +  target.getClass().getName() +"\n");
        }
        return result;
    }

    public static Object newInstance (Object target)
    {
        ClassLoader loader = target.getClass().getClassLoader();
        Class[] classes = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, classes, new LogProxy(target));
    }
}
