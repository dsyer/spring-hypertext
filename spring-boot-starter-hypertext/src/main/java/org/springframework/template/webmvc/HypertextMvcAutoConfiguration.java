package org.springframework.template.webmvc;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.hypertext.webmvc.HyperTextConfiguration;
import org.springframework.hypertext.webmvc.HyperTextRequestMappingHandlerMapping;
import org.springframework.hypertext.webmvc.htmx.EnableHtmx;
import org.springframework.hypertext.webmvc.htmx.HtmxConfiguration;
import org.springframework.hypertext.webmvc.turbo.EnableTurbo;
import org.springframework.hypertext.webmvc.turbo.TurboConfiguration;
import org.springframework.hypertext.webmvc.unpoly.EnableUnpoly;
import org.springframework.hypertext.webmvc.unpoly.UnpolyConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnMissingBean(HyperTextConfiguration.class)
@AutoConfigureBefore({ WebMvcAutoConfiguration.class })
public class HypertextMvcAutoConfiguration implements WebMvcRegistrations {

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new HyperTextRequestMappingHandlerMapping();
	}

	@ConditionalOnMissingBean(HtmxConfiguration.class)
	@ConditionalOnProperty(prefix = "hypertext.htmx", name = "enabled", havingValue = "true", matchIfMissing = true)
	@Configuration(proxyBeanMethods = false)
	@EnableHtmx
	static class HtmxAutoConfiguration {}

	@ConditionalOnMissingBean(UnpolyConfiguration.class)
	@ConditionalOnProperty(prefix = "hypertext.unpoly", name = "enabled", havingValue = "true", matchIfMissing = true)
	@Configuration(proxyBeanMethods = false)
	@EnableUnpoly
	static class UnpolyAutoConfiguration {}

	@ConditionalOnMissingBean(TurboConfiguration.class)
	@ConditionalOnProperty(prefix = "hypertext.turbo", name = "enabled", havingValue = "true", matchIfMissing = true)
	@Configuration(proxyBeanMethods = false)
	@EnableTurbo
	static class TurboAutoConfiguration {}
}
