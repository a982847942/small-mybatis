package nuaa.zk.s09.reflection;

import nuaa.zk.s09.reflection.factory.DefaultObjectFactory;
import nuaa.zk.s09.reflection.factory.ObjectFactory;
import nuaa.zk.s09.reflection.wrapper.DefaultObjectWrapperFactory;
import nuaa.zk.s09.reflection.wrapper.ObjectWrapperFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 14:01
 */
public class SystemMetaObject {
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
    private SystemMetaObject() {
        // Prevent Instantiation of Static Class
    }
    private static class NullObject {
    }

    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
    }
}
