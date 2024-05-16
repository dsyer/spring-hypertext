package org.springframework.template.webmvc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.hypertext.webmvc.HyperTextRequestMappingHandlerMapping;
import org.springframework.hypertext.webmvc.HyperTextWebMvcConfiguration;
import org.springframework.hypertext.webmvc.MultiViewResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfiguration
@ConditionalOnWebApplication
@AutoConfigureBefore({ WebMvcAutoConfiguration.class })
public class HypertextMvcAutoConfiguration implements WebMvcRegistrations, BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if ("viewResolver".equals(beanName)) {
			return new MultiViewResolver((ViewResolver) bean);
		}
		return bean;
	}

	@Bean
	public HyperTextWebMvcConfiguration hyperTextConfigurer(
			@Qualifier("viewResolver") ObjectFactory<ViewResolver> resolver,
			ObjectFactory<LocaleResolver> locales, ObjectMapper objectMapper) {
		return new HyperTextWebMvcConfiguration(resolver, locales, objectMapper);
	}

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new HyperTextRequestMappingHandlerMapping();
	}

}
