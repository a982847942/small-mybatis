package nuaa.zk.s09.reflection.wrapper;

import nuaa.zk.s09.reflection.MetaObject;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 13:01
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new RuntimeException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }
}
