package nuaa.zk.s07.reflection.wrapper;

import nuaa.zk.s07.reflection.MetaObject;
import nuaa.zk.s07.reflection.factory.ObjectFactory;
import nuaa.zk.s07.reflection.property.PropertyTokenizer;

import java.util.List;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 13:55
 */
public class MapWrapper extends BaseWrapper{
    private Map<String, Object> map;

    public MapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject);
        this.map = map;
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
