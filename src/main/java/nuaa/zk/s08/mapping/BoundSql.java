package nuaa.zk.s08.mapping;

import nuaa.zk.s08.reflection.MetaObject;
import nuaa.zk.s08.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:20
 */
public class BoundSql {
    private String sql;
    //    private Map<Integer, String> parameterMappings;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
//    private String parameterType;
    //    private String resultType;
    private Map<String, Object> additionalParameters;
    private MetaObject metaParameters;

//    public BoundSql(String sql, Map<Integer, String> parameterMappings, String parameterType, String resultType) {
//        this.sql = sql;
//        this.parameterMappings = parameterMappings;
//        this.parameterType = parameterType;
////        this.resultType = resultType;
//    }

    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public boolean hasAdditionalParameter(String name) {
        return metaParameters.hasGetter(name);
    }

    public void setAdditionalParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    public Object getAdditionalParameter(String name) {
        return metaParameters.getValue(name);
    }
}
