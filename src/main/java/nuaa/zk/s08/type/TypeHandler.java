package nuaa.zk.s08.type;

import cn.hutool.db.meta.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/29 20:58
 */
public interface TypeHandler<T> {
    /**
     * 设置参数
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;
}
