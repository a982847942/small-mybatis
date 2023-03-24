package nuaa.zk.s06.datasource.pooled;

import java.util.ArrayList;
import java.util.List;

public class PoolState {

    protected PooledDataSource dataSource;

    // 空闲链接
    protected final List<PooledConnection> idleConnections = new ArrayList<>();
    // 活跃链接
    protected final List<PooledConnection> activeConnections = new ArrayList<>();

    // 请求数据库连接的次数
    protected long requestCount = 0;
    // 获取连接的总请求时间
    protected long accumulatedRequestTime = 0;
    //checkoutTime表示从连接池取出连接，到归还连接的时间。accumulatedCheckoutTime表示所有连接的累计checkoutTime
    protected long accumulatedCheckoutTime = 0;
    //当连接长时间为归还给连接池时，会被认为连接超时，这个字段记录超时的连接数量
    protected long claimedOverdueConnectionCount = 0;
    //累计超时的时间
    protected long accumulatedCheckoutTimeOfOverdueConnections = 0;

    // 总等待时间
    protected long accumulatedWaitTime = 0;
    // 需要等待的次数
    protected long hadToWaitCount = 0;
    // 获取连接失败次数
    protected long badConnectionCount = 0;

    public PoolState(PooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public synchronized long getRequestCount() {
        return requestCount;
    }

    public synchronized long getAverageRequestTime() {
        return requestCount == 0 ? 0 : accumulatedRequestTime / requestCount;
    }

    public synchronized long getAverageWaitTime() {
        return hadToWaitCount == 0 ? 0 : accumulatedWaitTime / hadToWaitCount;
    }

    public synchronized long getHadToWaitCount() {
        return hadToWaitCount;
    }

    public synchronized long getBadConnectionCount() {
        return badConnectionCount;
    }

    public synchronized long getClaimedOverdueConnectionCount() {
        return claimedOverdueConnectionCount;
    }

    public synchronized long getAverageOverdueCheckoutTime() {
        return claimedOverdueConnectionCount == 0 ? 0 : accumulatedCheckoutTimeOfOverdueConnections / claimedOverdueConnectionCount;
    }

    public synchronized long getAverageCheckoutTime() {
        return requestCount == 0 ? 0 : accumulatedCheckoutTime / requestCount;
    }

    public synchronized int getIdleConnectionCount() {
        return idleConnections.size();
    }

    public synchronized int getActiveConnectionCount() {
        return activeConnections.size();
    }

}
