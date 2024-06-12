package org.springframework.template.webmvc;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.hypertext.webmvc.HyperTextHeaderConfiguration;
import org.springframework.hypertext.webmvc.HyperTextRequestMappingHandlerMapping;
import org.springframework.hypertext.webmvc.htmx.EnableHtmx;
import org.springframework.hypertext.webmvc.turbo.EnableTurbo;
import org.springframework.hypertext.webmvc.unpoly.EnableUnpoly;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@AutoConfiguration
@ConditionalOnWebApplication
@AutoConfigureBefore({ WebMvcAutoConfiguration.class })

public class HypertextMvcAutoConfiguration implements WebMvcRegistrations {

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new HyperTextRequestMappingHandlerMapping();
	}

	/**
	 * Enables all of Htmx, Unpoly, and Turbo support if none is already available.
	 * Each one can be switched off using the corresponding enabled property.
	 */
	@ConditionalOnMissingBean(HyperTextHeaderConfiguration.class)
	@Configuration(proxyBeanMethods = false)
	static class EnableHyperTextConfiguration {

		@ConditionalOnProperty(prefix = "hypertext.htmx", name = "enabled", havingValue = "true", matchIfMissing = true)
		@Configuration(proxyBeanMethods = false)
		@EnableHtmx
		static class HtmxAutoConfiguration {
		}

		@ConditionalOnProperty(prefix = "hypertext.unpoly", name = "enabled", havingValue = "true", matchIfMissing = true)
		@Configuration(proxyBeanMethods = false)
		@EnableUnpoly
		static class UnpolyAutoConfiguration {
		}

		@ConditionalOnProperty(prefix = "hypertext.turbo", name = "enabled", havingValue = "true", matchIfMissing = true)
		@Configuration(proxyBeanMethods = false)
		@EnableTurbo
		static class TurboAutoConfiguration {
		}
	}
}
