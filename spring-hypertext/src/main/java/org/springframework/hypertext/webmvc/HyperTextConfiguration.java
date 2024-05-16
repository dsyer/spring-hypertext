/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hypertext.webmvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Configuration(proxyBeanMethods = false)
public class HyperTextConfiguration implements BeanPostProcessor {

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

	@Bean
	public HyperTextHandlerInterceptor hyperTextHandlerInterceptor(ObjectProvider<HyperTextDetailExtractor> details,
			ObjectProvider<HeaderNameExtractor> extractors, ObjectMapper objectMapper) {
		return new HyperTextHandlerInterceptor(new CompositeHeaderNameExtractor(extractors),
				new CompositeHyperTextDetailExtractor(details), objectMapper);
	}

	static class CompositeHyperTextDetailExtractor implements HyperTextDetailExtractor {

		private final ObjectProvider<HyperTextDetailExtractor> details;

		CompositeHyperTextDetailExtractor(ObjectProvider<HyperTextDetailExtractor> details) {
			this.details = details;
		}

		@Override
		public Map<String, HyperTextDetail> getDetails(Method method) {
			Map<String, HyperTextDetail> result = new HashMap<>();
			for (HyperTextDetailExtractor detail : this.details) {
				result.putAll(detail.getDetails(method));
			}
			return result;
		}

	}

	static class CompositeHeaderNameExtractor implements HeaderNameExtractor {

		private final ObjectProvider<HeaderNameExtractor> extractors;

		CompositeHeaderNameExtractor(ObjectProvider<HeaderNameExtractor> extractors) {
			this.extractors = extractors;
		}

		@Override
		public Set<String> getHeaders(HttpServletRequest request) {
			Set<String> headerNames = new HashSet<>();
			for (HeaderNameExtractor extractor : this.extractors) {
				headerNames.addAll(extractor.getHeaders(request));
			}
			return headerNames;
		}

	}
}
