package nuaa.zk.s03.session;

import nuaa.zk.s03.session.SqlSession;
import nuaa.zk.s03.binding.MapperRegistry;
import nuaa.zk.s03.mapping.MapperStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:35
 */
public class Configuration {

    private MapperRegistry mapperRegistry = new MapperRegistry(this);
    private Map<String, MapperStatement> mappedStatements = new HashMap<>();

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

}
