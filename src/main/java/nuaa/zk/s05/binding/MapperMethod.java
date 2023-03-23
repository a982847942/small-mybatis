package nuaa.zk.s05.binding;

import nuaa.zk.s05.mapping.MapperStatement;
import nuaa.zk.s05.mapping.SqlCommandType;
import nuaa.zk.s05.session.Configuration;
import nuaa.zk.s05.session.SqlSession;

import java.lang.reflect.Method;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:50
 */
public class MapperMethod {
    private final SqlCommand command;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.command = new SqlCommand(configuration, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                result = sqlSession.selectOne(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }


    public  class SqlCommand{
        public final String name;
        public final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            String statementName = mapperInterface.getName() + "." + method.getName();
            MapperStatement mappedStatement = configuration.getMappedStatement(statementName);
            name = mappedStatement.getId();
            type = mappedStatement.getSqlCommandType();
        }

        public SqlCommandType getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
