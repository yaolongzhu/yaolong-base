package yaolong.base.session.config;

import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

/**
 * @author
 */
public class SessionRedisSentinelConfiguration extends RedisSentinelConfiguration {
    
//    private String name;

    public SessionRedisSentinelConfiguration() {
        super();
    }

    public SessionRedisSentinelConfiguration(String master) {
        super.master(master);
    }

    public SessionRedisSentinelConfiguration(String master, String sentinels) {
        this(master);
        addSentinel(sentinels);
    }

    public void addSentinel(String sentinels) {
        String[] ss = sentinels.split(",");
        for (String sentinel: ss) {
            String[] s = sentinel.split(":");
            String ip = s[0].trim();
            int port = Integer.parseInt(s[1].trim());
            RedisNode rn = new RedisNode(ip, port);
            super.addSentinel(rn);
        }
    }

    public void setSentinel(String sentinels) {
        this.addSentinel(sentinels);
    }
    
    public void setMaster(final String name) {
       super.setMaster(name);
    }

//    public String getName() {
//        return name;
//    }

    public void setName(final String name) {
//        this.name = name;
        super.setMaster(name);
    }
    
    
}
