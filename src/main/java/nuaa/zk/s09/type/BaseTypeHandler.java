package nuaa.zk.s09.type;

import cn.hutool.db.meta.JdbcType;
import nuaa.zk.s09.session.Configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/4/1 11:21
 */
public abstract class BaseTypeHandler<T> implements TypeHandler<T>{
    protected Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JDBCType jdbcType) throws SQLException {
        setNonNullParameter(ps,i,parameter,jdbcType);
    }
    protected abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JDBCType jdbcType) throws SQLException;

}
