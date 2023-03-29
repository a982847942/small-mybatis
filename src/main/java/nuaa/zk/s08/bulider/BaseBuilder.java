package nuaa.zk.s08.bulider;

import nuaa.zk.s08.session.Configuration;
import nuaa.zk.s08.type.TypeAliasRegistry;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:22
 */
public class BaseBuilder {
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected Class<?> resolveAlias(String alias){
        return typeAliasRegistry.resolveAlias(alias);
    }
}
