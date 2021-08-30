package com.dxc.eproc.estimate.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

import com.dxc.eproc.config.EProcProperties;
import com.dxc.eproc.utils.PrefixedKeyGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class CacheConfiguration.
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

	/** The git properties. */
	private GitProperties gitProperties;

	/** The build properties. */
	private BuildProperties buildProperties;

	/** The jcache configuration. */
	private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

	/**
	 * Instantiates a new cache configuration.
	 *
	 * @param eProcProperties the e proc properties
	 */
	public CacheConfiguration(EProcProperties eProcProperties) {
		EProcProperties.Cache.Ehcache ehcache = eProcProperties.getCache().getEhcache();

		jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Object.class, Object.class,
						ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
				.withExpiry(
						ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
				.build());
	}

	/**
	 * Hibernate properties customizer.
	 *
	 * @param cacheManager the cache manager
	 * @return the hibernate properties customizer
	 */
	@Bean
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
		return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
	}

	/**
	 * Cache manager customizer.
	 *
	 * @return the j cache manager customizer
	 */
	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer() {
		return cm -> {
			createCache(cm, com.dxc.eproc.estimate.model.EstimateType.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.DeptEstimateType.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkType.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkCategory.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkCategoryAttribute.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.DeptHeadOfAccount.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.SchemeCode.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimate.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimate.class.getName() + ".subEstimates");
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimate.class.getName() + ".workSubEstimateGroups");
			createCache(cm, com.dxc.eproc.estimate.model.SubEstimate.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.SubEstimate.class.getName() + ".subEstimates");
			createCache(cm, com.dxc.eproc.estimate.model.SubEstimate.class.getName() + ".workEstimateItems");
			createCache(cm, com.dxc.eproc.estimate.model.WorkLocation.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateItem.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateItem.class.getName() + ".workEstimateItemLBDs");
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateItemLBD.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkSubEstimateGroup.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkSubEstimateGroup.class.getName()
					+ ".workSubEstimateGroupLumpsums");
			createCache(cm, com.dxc.eproc.estimate.model.WorkSubEstimateGroup.class.getName()
					+ ".workSubEstimateGroupOverheads");
			createCache(cm, com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum.class.getName());
			createCache(cm, com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead.class.getName());
			
			createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateAdditionalCharges.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateLiftCharges.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateLeadCharges.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateMarketRate.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.RaParameters.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.RaConfiguration.class.getName());
            createCache(cm, com.dxc.eproc.estimate.model.RaFormula.class.getName());
		};
	}

	/**
	 * Creates the cache.
	 *
	 * @param cm        the cm
	 * @param cacheName the cache name
	 */
	private void createCache(javax.cache.CacheManager cm, String cacheName) {
		javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
		if (cache != null) {
			cache.clear();
		} else {
			cm.createCache(cacheName, jcacheConfiguration);
		}
	}

	/**
	 * Sets the git properties.
	 *
	 * @param gitProperties the new git properties
	 */
	@Autowired(required = false)
	public void setGitProperties(GitProperties gitProperties) {
		this.gitProperties = gitProperties;
	}

	/**
	 * Sets the builds the properties.
	 *
	 * @param buildProperties the new builds the properties
	 */
	@Autowired(required = false)
	public void setBuildProperties(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	/**
	 * Key generator.
	 *
	 * @return the key generator
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
	}
}
