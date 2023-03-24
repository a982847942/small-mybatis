package nuaa.zk.s06.datasource.unpooled;

import nuaa.zk.s06.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/23 20:25
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {
    protected Properties props;

    @Override
    public void setProperties(Properties prop) {
        this.props = prop;
    }

    public DataSource getDataSource(){
        UnpooledDataSource unpooledDataSource = new UnpooledDataSource();
        unpooledDataSource.setDriver(props.getProperty("driver"));
        unpooledDataSource.setUrl(props.getProperty("url"));
        unpooledDataSource.setUsername(props.getProperty("username"));
        unpooledDataSource.setPassword(props.getProperty("password"));
        return unpooledDataSource;
    }
}
