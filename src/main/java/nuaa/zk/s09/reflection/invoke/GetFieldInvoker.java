package nuaa.zk.s09.reflection.invoke;

import java.lang.reflect.Field;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:20
 */
public class GetFieldInvoker implements Invoker {
    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return field.get(target);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
