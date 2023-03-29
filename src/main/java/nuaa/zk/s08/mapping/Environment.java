package nuaa.zk.s08.mapping;

import nuaa.zk.s08.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:21
 */
public class Environment {
    private String id;
    private DataSource dataSource;
    private TransactionFactory transactionFactory;

    public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
        this.transactionFactory = transactionFactory;
    }

    public String getId() {
        return id;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }


    public static class Builder {

        private String id;
        private TransactionFactory transactionFactory;
        private DataSource dataSource;

        public Builder(String id) {
            this.id = id;
        }

        public Builder transactionFactory(TransactionFactory transactionFactory) {
            this.transactionFactory = transactionFactory;
            return this;
        }

        public Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public String id() {
            return this.id;
        }

        public Environment build() {
            return new Environment(this.id, this.transactionFactory, this.dataSource);
        }

    }
}
