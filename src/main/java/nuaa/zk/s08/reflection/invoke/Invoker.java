package nuaa.zk.s08.reflection.invoke;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:19
 */
public interface Invoker {
    Object invoke(Object target,Object[] args) throws Exception;
    Class<?> getType();
}
