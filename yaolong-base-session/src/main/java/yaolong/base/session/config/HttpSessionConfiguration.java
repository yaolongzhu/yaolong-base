package yaolong.base.session.config;

/**
 * 
 * @author
 *
 */

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.ConfigureNotifyKeyspaceEventsAction;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import yaolong.base.session.util.RepositoryUtils;

@Configuration
@EnableScheduling
public class HttpSessionConfiguration extends SessionConfig implements ImportAware {

    private Integer maxInactiveIntervalInSeconds = 1800;

    private ConfigureRedisAction configureRedisAction = new ConfigureNotifyKeyspaceEventsAction();

    private String redisNamespace = "";

    private RedisFlushMode redisFlushMode = RedisFlushMode.ON_SAVE;

    private RedisSerializer<Object> defaultRedisSerializer;

    private Executor redisTaskExecutor;

    private Executor redisSubscriptionExecutor;

    private BaseHeaderHttpSessionStrategy baseStrategy;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(@Qualifier("base_session_sentinel_jedisConnectionFactory") RedisConnectionFactory connectionFactory,
            RedisOperationsSessionRepository messageListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        if (redisTaskExecutor != null) {
            container.setTaskExecutor(redisTaskExecutor);
        }
        if (redisSubscriptionExecutor != null) {
            container.setSubscriptionExecutor(redisSubscriptionExecutor);
        }
        container.addMessageListener(messageListener,
                Arrays.asList(new PatternTopic("__keyevent@*:del"), new PatternTopic("__keyevent@*:expired")));
        container.addMessageListener(messageListener,
                Arrays.asList(new PatternTopic(messageListener.getSessionCreatedChannelPrefix() + "*")));
        return container;
    }

    @Bean("base_session_redisTemplate")
    public RedisTemplate<Object, Object> sessionRedisTemplate(@Qualifier("base_session_sentinel_jedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        if (defaultRedisSerializer != null) {
            template.setDefaultSerializer(defaultRedisSerializer);
        }
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisOperationsSessionRepository sessionRepository(
            @Qualifier("base_session_redisTemplate") RedisOperations<Object, Object> sessionRedisTemplate,
            ApplicationEventPublisher applicationEventPublisher) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setApplicationEventPublisher(applicationEventPublisher);
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
        if (defaultRedisSerializer != null) {
            sessionRepository.setDefaultSerializer(defaultRedisSerializer);
        }

        String redisNamespace = getRedisNamespace();
        if (StringUtils.hasText(redisNamespace)) {
            sessionRepository.setRedisKeyNamespace(redisNamespace);
        }
        sessionRepository.setRedisFlushMode(redisFlushMode);
        //加入工具
        RepositoryUtils.setSessionRepository(sessionRepository);
        return sessionRepository;
    }

    // @Bean
    // public CookieSerializer cookieSerializer() {
    // DefaultCookieSerializer serializer = new DefaultCookieSerializer();
    // serializer.setCookieName("JSESSIONID"); // <1>
    // serializer.setCookiePath("/"); // <2>
    // serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");// <3>
    // return serializer;
    // }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return baseStrategy;
    }

    public BaseHeaderHttpSessionStrategy getCdpStrategy() {
        return baseStrategy;
    }

    @Autowired(required = false)
    @Qualifier("base_session_strategy")
    public void setCdpStrategy(BaseHeaderHttpSessionStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Bean(name = "base_session_httpSessionCookies")
    public SessionConfiguration httpSessionCookies() {
        return new HttpSessionCookies();
    }

    @Bean(name = "base_session_httpSessionHeader")
    public SessionConfiguration httpSessionHeader() {
        return new HttpSessionHeader();
    }

    public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
        this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
    }

    public void setRedisNamespace(String namespace) {
        this.redisNamespace = namespace;
    }

    public void setRedisFlushMode(RedisFlushMode redisFlushMode) {
        Assert.notNull(redisFlushMode, "redisFlushMode cannot be null");
        this.redisFlushMode = redisFlushMode;
    }

    private String getRedisNamespace() {
        if (StringUtils.hasText(this.redisNamespace)) {
            return this.redisNamespace;
        }
        return System.getProperty("spring.session.redis.namespace", "");
    }

    public void setImportMetadata(AnnotationMetadata importMetadata) {

        Map<String, Object> enableAttrMap = importMetadata
                .getAnnotationAttributes(EnableRedisHttpSession.class.getName());
        AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
        maxInactiveIntervalInSeconds = enableAttrs.getNumber("maxInactiveIntervalInSeconds");
        this.redisNamespace = enableAttrs.getString("redisNamespace");
        this.redisFlushMode = enableAttrs.getEnum("redisFlushMode");
    }

    @Bean
    public InitializingBean enableRedisKeyspaceNotificationsInitializer(@Qualifier("base_session_sentinel_jedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return new EnableRedisKeyspaceNotificationsInitializer(connectionFactory, configureRedisAction);
    }

    /**
     * Ensures that Redis is configured to send keyspace notifications. This is
     * important to ensure that expiration and deletion of sessions trigger
     * SessionDestroyedEvents. Without the SessionDestroyedEvent resources may
     * not get cleaned up properly. For example, the mapping of the Session to
     * WebSocket connections may not get cleaned up.
     */
    static class EnableRedisKeyspaceNotificationsInitializer implements InitializingBean {
        private final RedisConnectionFactory connectionFactory;

        private ConfigureRedisAction configure;

        EnableRedisKeyspaceNotificationsInitializer(RedisConnectionFactory connectionFactory,
                ConfigureRedisAction configure) {
            this.connectionFactory = connectionFactory;
            this.configure = configure;
        }

        public void afterPropertiesSet() throws Exception {
            RedisConnection connection = connectionFactory.getConnection();
            configure.configure(connection);
        }
    }

    /**
     * Sets the action to perform for configuring Redis.
     *
     * @param configureRedisAction
     *            the configureRedis to set. The default is
     *            {@link ConfigureNotifyKeyspaceEventsAction}.
     */
    @Autowired(required = false)
    public void setConfigureRedisAction(ConfigureRedisAction configureRedisAction) {
        this.configureRedisAction = configureRedisAction;
    }

    @Autowired(required = false)
    @Qualifier("springSessionDefaultRedisSerializer")
    public void setDefaultRedisSerializer(RedisSerializer<Object> defaultRedisSerializer) {
        this.defaultRedisSerializer = defaultRedisSerializer;
    }

    @Autowired(required = false)
    @Qualifier("springSessionRedisTaskExecutor")
    public void setRedisTaskExecutor(Executor redisTaskExecutor) {
        this.redisTaskExecutor = redisTaskExecutor;
    }

    @Autowired(required = false)
    @Qualifier("springSessionRedisSubscriptionExecutor")
    public void setRedisSubscriptionExecutor(Executor redisSubscriptionExecutor) {
        this.redisSubscriptionExecutor = redisSubscriptionExecutor;
    }

}
