package nuaa.zk.s03.bulider;

import nuaa.zk.s03.session.Configuration;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:22
 */
public class BaseBuilder {
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
