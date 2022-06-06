package utils;

public class TokenBucket {
    /**
     * 令牌桶容量
     */
    private final long capacity;

    /**
     * 当前桶内令牌数
     */
    private long currentTokens;

    /**
     * 每秒处理数（放入令牌数量）
     */
    private long generateTokenRatePerSecond;

    /**
     * 最后刷新时间
     */
    private long lastTokenTimestamp;

    public TokenBucket(final long capacity) {
        this.capacity = capacity;
    }

    public synchronized boolean addToken() {
        if (currentTokens < capacity) {
            currentTokens++;
            return true;
        }
        return false;
    }

    public synchronized boolean takeToken() {
        if (currentTokens > 0) {
            currentTokens--; // 令牌数量-1
            return true;
        }
        return false;
    }

    synchronized void tryAcquire() {
        long currentTime = System.currentTimeMillis();  // 获取系统当前时间
        if (currentTime - lastTokenTimestamp >= 1000) { // 因为生成令牌的最小时间单位时s
            long generateToken = (currentTime - lastTokenTimestamp) / 1000 * generateTokenRatePerSecond; // 生成的令牌 = (当前时间-上次刷新时间) * 放入令牌的速率
            currentTokens = Math.min(capacity, generateToken + currentTokens); // 当前令牌数量 = 之前的桶内令牌数量+放入的令牌数量
            lastTokenTimestamp = currentTime; // 刷新时间
        }
    }
}
