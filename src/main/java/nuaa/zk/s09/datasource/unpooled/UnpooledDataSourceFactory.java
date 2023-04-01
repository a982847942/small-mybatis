package nuaa.zk.s09.datasource.unpooled;

import nuaa.zk.s09.datasource.DataSourceFactory;
import nuaa.zk.s09.reflection.MetaObject;
import nuaa.zk.s09.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/23 20:25
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {
    //    protected Properties props;
    protected DataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
//    public void setProperties(Properties prop) {
//        this.props = prop;
//    }
    public void setProperties(Properties props) {
        MetaObject metaObject = SystemMetaObject.forObject(dataSource);
        for (Object key : props.keySet()) {
            String propertyName = (String) key;
            if (metaObject.hasSetter(propertyName)) {
                String value = (String) props.get(propertyName);
                Object convertedValue = convertValue(metaObject, propertyName, value);
                metaObject.setValue(propertyName, convertedValue);
            }
        }
    }

    //    public DataSource getDataSource(){
//        UnpooledDataSource unpooledDataSource = new UnpooledDataSource();
//        unpooledDataSource.setDriver(props.getProperty("driver"));
//        unpooledDataSource.setUrl(props.getProperty("url"));
//        unpooledDataSource.setUsername(props.getProperty("username"));
//        unpooledDataSource.setPassword(props.getProperty("password"));
//        return unpooledDataSource;
//    }
    public DataSource getDataSource() {
        return dataSource;
    }

    private Object convertValue(MetaObject metaObject, String propertyName, String value) {
        Object convertedValue = value;
        Class<?> targetType = metaObject.getSetterType(propertyName);
        if (targetType == Integer.class || targetType == int.class) {
            convertedValue = Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            convertedValue = Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            convertedValue = Boolean.valueOf(value);
        }
        return convertedValue;
    }
}
