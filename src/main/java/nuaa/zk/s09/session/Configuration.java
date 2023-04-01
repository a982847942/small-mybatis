package nuaa.zk.s09.session;

import nuaa.zk.s09.binding.MapperRegistry;
import nuaa.zk.s09.datasource.druid.DruidDataSourceFactory;
import nuaa.zk.s09.datasource.pooled.PooledDataSourceFactory;
import nuaa.zk.s09.datasource.unpooled.UnpooledDataSourceFactory;
import nuaa.zk.s09.executor.Executor;
import nuaa.zk.s09.executor.SimpleExecutor;
import nuaa.zk.s09.executor.parameter.ParameterHandler;
import nuaa.zk.s09.executor.result.DefaultResultSetHandler;
import nuaa.zk.s09.executor.result.ResultSetHandler;
import nuaa.zk.s09.executor.statement.PrepareStatementHandler;
import nuaa.zk.s09.executor.statement.StatementHandler;
import nuaa.zk.s09.mapping.BoundSql;
import nuaa.zk.s09.mapping.Environment;
import nuaa.zk.s09.mapping.MapperStatement;
import nuaa.zk.s09.reflection.MetaObject;
import nuaa.zk.s09.reflection.factory.DefaultObjectFactory;
import nuaa.zk.s09.reflection.factory.ObjectFactory;
import nuaa.zk.s09.reflection.wrapper.DefaultObjectWrapperFactory;
import nuaa.zk.s09.reflection.wrapper.ObjectWrapperFactory;
import nuaa.zk.s09.scripting.LanguageDriver;
import nuaa.zk.s09.scripting.LanguageDriverRegistry;
import nuaa.zk.s09.scripting.xmltags.XMLLanguageDriver;
import nuaa.zk.s09.transaction.Transaction;
import nuaa.zk.s09.transaction.jdbc.JDBCTransactionFactory;
import nuaa.zk.s09.type.TypeAliasRegistry;
import nuaa.zk.s09.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:35
 */
public class Configuration {

    //环境
    protected Environment environment;
    protected String databaseId;
    private MapperRegistry mapperRegistry = new MapperRegistry(this);
    private Map<String, MapperStatement> mappedStatements = new HashMap<>();
    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();
    private Set<String> loadedResources = new HashSet<>();
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JDBCTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
    }
    public String getDatabaseId() {
        return databaseId;
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
    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
    }

    public boolean isResourceLoaded(String resource){
        return loadedResources.contains(resource);
    }
    public void addLoadedResource(String resource){
        loadedResources.add(resource);
    }

    public LanguageDriverRegistry getLanguageRegistry() {
        return languageRegistry;
    }
    public ParameterHandler newParameterHandler(MapperStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        // 创建参数处理器
        ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement, parameterObject, boundSql);
        // 插件的一些参数，也是在这里处理，暂时不添加这部分内容 interceptorChain.pluginAll(parameterHandler);
        return parameterHandler;
    }
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }
    public LanguageDriver getDefaultScriptingLanguageInstance() {
        return languageRegistry.getDefaultDriver();
    }
}
