package nuaa.zk.s08.type;

import cn.hutool.db.meta.JdbcType;

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
    private final Map<JdbcType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP = new EnumMap<>(JdbcType.class);
    private final Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP = new HashMap<>();
    private final Map<Class<?>, TypeHandler<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<>();

    public TypeHandlerRegistry() {

    }

    private void register(Type javaType, JdbcType jdbcType, TypeHandler<?> handler) {
        if (null != javaType){
            Map<JdbcType, TypeHandler<?>> map = TYPE_HANDLER_MAP.computeIfAbsent(javaType, k -> new HashMap<>());
            map.put(jdbcType, handler);
        }
        ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
    }
}
