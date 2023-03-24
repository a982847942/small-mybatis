package nuaa.zk.s06.datasource.pooled;

import nuaa.zk.s06.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/23 20:53
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {
    @Override
    public DataSource getDataSource() {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver(props.getProperty("driver"));
        pooledDataSource.setUrl(props.getProperty("url"));
        pooledDataSource.setUsername(props.getProperty("username"));
        pooledDataSource.setPassword(props.getProperty("password"));
        return pooledDataSource;
    }
}
