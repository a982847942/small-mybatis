package nuaa.zk.s08.reflection.invoke;

import java.lang.reflect.Field;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:20
 */
public class SetFieldInvoker implements Invoker {
    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        field.set(target,args);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
