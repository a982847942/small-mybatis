package nuaa.zk.s07.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:14
 */
public interface DataSourceFactory {
    void setProperties(Properties prop);
    DataSource getDataSource();
}
