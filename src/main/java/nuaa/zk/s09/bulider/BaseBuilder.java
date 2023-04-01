package nuaa.zk.s09.bulider;

import nuaa.zk.s09.session.Configuration;
import nuaa.zk.s09.type.TypeAliasRegistry;
import nuaa.zk.s09.type.TypeHandlerRegistry;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:22
 */
public class BaseBuilder {
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;
    protected final TypeHandlerRegistry typeHandlerRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected Class<?> resolveAlias(String alias){
        return typeAliasRegistry.resolveAlias(alias);
    }
}
