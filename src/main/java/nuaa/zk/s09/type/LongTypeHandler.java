package nuaa.zk.s09.type;

import cn.hutool.db.meta.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/4/1 11:21
 */
public class LongTypeHandler extends BaseTypeHandler<Long>{

    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JDBCType jdbcType) throws SQLException {
        ps.setLong(i,parameter);
    }
}
