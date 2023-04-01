package nuaa.zk.s09.reflection.invoke;

import java.lang.reflect.Method;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:21
 */
public class MethodInvoker implements Invoker {
    Method method;
    private Class<?> type;

    public MethodInvoker(Method method) {
        this.method = method;
        if (method.getParameterCount() == 1){
            type = method.getParameterTypes()[0];
        }else {
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return method.invoke(target,args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
