package nuaa.zk.s08.reflection.wrapper;

import nuaa.zk.s08.reflection.MetaObject;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 12:59
 */
public interface ObjectWrapperFactory {
    //判断object是否存在包装对象
    boolean hasWrapperFor(Object object);

    //得到对应的包装器
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
