package it.fabiofenoglio.lelohub.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, it.fabiofenoglio.lelohub.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, it.fabiofenoglio.lelohub.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, it.fabiofenoglio.lelohub.domain.User.class.getName());
            createCache(cm, it.fabiofenoglio.lelohub.domain.Authority.class.getName());
            createCache(cm, it.fabiofenoglio.lelohub.domain.User.class.getName() + ".authorities");
            createCache(cm, it.fabiofenoglio.lelohub.domain.Sequence.class.getName());
            // createCache(cm, it.fabiofenoglio.lelohub.domain.Sequence.class.getName() + ".steps");
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStep.class.getName());
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStep.class.getName() + ".conditions");
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStep.class.getName() + ".actions");
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition.class.getName());
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition.class.getName() + ".parameters");
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter.class.getName());
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepCondition.class.getName());
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepCondition.class.getName() + ".parameters");
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepCondition.class.getName() + ".andConditions");
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepCondition.class.getName() + ".orConditions");
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter.class.getName());
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition.class.getName());
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition.class.getName() + ".parameters");
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter.class.getName());
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepAction.class.getName());
            // createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepAction.class.getName() + ".parameters");
            createCache(cm, it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
