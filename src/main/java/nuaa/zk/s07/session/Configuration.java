package nuaa.zk.s07.session;

import nuaa.zk.s07.binding.MapperRegistry;
import nuaa.zk.s07.datasource.druid.DruidDataSourceFactory;
import nuaa.zk.s07.datasource.pooled.PooledDataSourceFactory;
import nuaa.zk.s07.datasource.unpooled.UnpooledDataSourceFactory;
import nuaa.zk.s07.executor.Executor;
import nuaa.zk.s07.executor.SimpleExecutor;
import nuaa.zk.s07.executor.result.DefaultResultSetHandler;
import nuaa.zk.s07.executor.result.ResultSetHandler;
import nuaa.zk.s07.executor.statement.PrepareStatementHandler;
import nuaa.zk.s07.executor.statement.StatementHandler;
import nuaa.zk.s07.mapping.BoundSql;
import nuaa.zk.s07.mapping.Environment;
import nuaa.zk.s07.mapping.MapperStatement;
import nuaa.zk.s07.transaction.Transaction;
import nuaa.zk.s07.transaction.jdbc.JDBCTransactionFactory;
import nuaa.zk.s07.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:35
 */
public class Configuration {

    //环境
    protected Environment environment;

    private MapperRegistry mapperRegistry = new MapperRegistry(this);
    private Map<String, MapperStatement> mappedStatements = new HashMap<>();
    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();


    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JDBCTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> boolean hasMapper(Class<T> type) {
        return mapperRegistry.hasMapper(type);
    }

    public <T> T  getMapper(Class<T> type, SqlSession sqlSession){
        return mapperRegistry.getMapper(type,sqlSession);
    }

    public void addMappedStatement(MapperStatement mapperStatement){
        mappedStatements.put(mapperStatement.getId(),mapperStatement);
    }

    public MapperStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    //执行器
    public Executor newExecutor(Transaction transaction){
        return new SimpleExecutor(this,transaction);
    }
    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MapperStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PrepareStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }

    public ResultSetHandler newResultSetHandler(Executor executor, MapperStatement mapperStatement, BoundSql boundSql){
        return new DefaultResultSetHandler(executor,mapperStatement,boundSql);
    }
}
