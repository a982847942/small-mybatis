package nuaa.zk.s09.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/4/1 14:38
 */
public interface ParameterHandler {
    /**
     * 获取参数
     */
    Object getParameterObject();

    /**
     * 设置参数
     */
    void setParameters(PreparedStatement ps) throws SQLException;
}
