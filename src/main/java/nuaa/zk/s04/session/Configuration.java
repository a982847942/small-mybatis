package nuaa.zk.s04.session;

import nuaa.zk.s04.binding.MapperRegistry;
import nuaa.zk.s04.datasource.druid.DruidDataSourceFactory;
import nuaa.zk.s04.mapping.Environment;
import nuaa.zk.s04.mapping.MapperStatement;
import nuaa.zk.s04.transaction.jdbc.JDBCTransactionFactory;
import nuaa.zk.s04.type.TypeAliasRegistry;

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
}
