package nuaa.zk.s09.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import nuaa.zk.s09.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:16
 */
public class DruidDataSourceFactory implements DataSourceFactory {
    Properties prop;
    @Override
    public void setProperties(Properties prop) {
        this.prop = prop;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(prop.getProperty("driver"));
        dataSource.setUrl(prop.getProperty("url"));
        dataSource.setUsername(prop.getProperty("username"));
        dataSource.setPassword(prop.getProperty("password"));
        return dataSource;
    }
}
