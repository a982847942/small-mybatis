package nuaa.zk.s07.reflection.wrapper;

import nuaa.zk.s07.reflection.MetaObject;
import nuaa.zk.s07.reflection.factory.ObjectFactory;
import nuaa.zk.s07.reflection.property.PropertyTokenizer;

import java.util.Collection;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 13:55
 */
public class CollectionWrapper implements ObjectWrapper{
    // 原来的对象
    private Collection<Object> object;

    public CollectionWrapper(MetaObject metaObject,Collection<Object> object) {
        this.object = object;
    }


    @Override
    public Object get(PropertyTokenizer prop) {
        return null;
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {

    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return null;
    }

    @Override
    public String[] getSetterNames() {
        return new String[0];
    }

    @Override
    public String[] getGetterNames() {
        return new String[0];
    }

    @Override
    public Class<?> getSetterType(String name) {
        return null;
    }

    @Override
    public Class<?> getGetterType(String name) {
        return null;
    }

    @Override
    public boolean hasSetter(String name) {
        return false;
    }

    @Override
    public boolean hasGetter(String name) {
        return false;
    }

    @Override
    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        return null;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public void add(Object element) {

    }

    @Override
    public <E> void addAll(List<E> elements) {

    }
}
