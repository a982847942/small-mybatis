package nuaa.zk.s07.reflection.wrapper;

import nuaa.zk.s07.reflection.MetaObject;
import nuaa.zk.s07.reflection.factory.ObjectFactory;
import nuaa.zk.s07.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 12:48
 */
public interface ObjectWrapper {
    //get值
    Object get(PropertyTokenizer prop);
    //set值
    void set(PropertyTokenizer prop,Object value);
    //查找name对应的属性是否存在
    String findProperty(String name,boolean useCamelCaseMapping);
    String[] getSetterNames();
    String[] getGetterNames();
    //set的类型
    Class<?> getSetterType(String name);
    //getter的类型
    Class<?> getGetterType(String name);
    //是否有乡音国的setter方法
    boolean hasSetter(String name);
    boolean hasGetter(String name);

    //实例化属性
    MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

    //是否是集合
    boolean isCollection();
    //添加属性
    void add(Object element);
    <E> void addAll(List<E> elements);
}
