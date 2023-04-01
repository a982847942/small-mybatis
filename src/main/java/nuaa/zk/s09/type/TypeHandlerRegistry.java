package nuaa.zk.s09.type;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/29 20:59
 */
public class TypeHandlerRegistry {
    private final Map<JDBCType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP = new EnumMap<>(JDBCType.class);
    private final Map<Type, Map<JDBCType, TypeHandler<?>>> TYPE_HANDLER_MAP = new HashMap<>();
    private final Map<Class<?>, TypeHandler<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<>();

    public TypeHandlerRegistry() {
        register(Long.class, new LongTypeHandler());
        register(long.class,new LongTypeHandler());
        register(String.class,new StringTypeHandler());
        register(String.class,JDBCType.CHAR,new StringTypeHandler());
        register(String.class,JDBCType.VARCHAR,new StringTypeHandler());
    }

    private <T> void register(Type javaType, TypeHandler<? extends T> handler) {
        register(javaType, null, handler);
    }

    private void register(Type javaType, JDBCType jdbcType, TypeHandler<?> handler) {
        if (null != javaType) {
            Map<JDBCType, TypeHandler<?>> map = TYPE_HANDLER_MAP.computeIfAbsent(javaType, k -> new HashMap<>());
            map.put(jdbcType, handler);
        }
        ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
    }
    public boolean hasTypeHandler(Class<?> javaType) {
        return hasTypeHandler(javaType, null);
    }

    public boolean hasTypeHandler(Class<?> javaType, JDBCType jdbcType) {
        return javaType != null && getTypeHandler((Type) javaType, jdbcType) != null;
    }

    public   <T> TypeHandler<T> getTypeHandler(Type type,JDBCType jdbcType){
        Map<JDBCType, TypeHandler<?>> jdbcHandlerMap = TYPE_HANDLER_MAP.get(type);
        TypeHandler<?> handler = null;
        if (jdbcHandlerMap != null){
            handler = jdbcHandlerMap.get(jdbcType);
            if (handler == null){
                handler = jdbcHandlerMap.get(null);
            }
        }
        return (TypeHandler<T>) handler;
    }

}
