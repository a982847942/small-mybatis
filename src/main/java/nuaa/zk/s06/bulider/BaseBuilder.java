package nuaa.zk.s06.bulider;

import nuaa.zk.s06.session.Configuration;
import nuaa.zk.s06.type.TypeAliasRegistry;

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
}
