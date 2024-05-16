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
package org.springframework.template.webmvc.hypertext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HyperTextHandlerInterceptor implements HandlerInterceptor, WebMvcConfigurer {

	private final HeaderNameExtractor varyExtractor;
	private final HyperTextDetailExtractor detailExtractor;
	private final HyperTextDetailHeaderManager headerManager;

	public HyperTextHandlerInterceptor(HeaderNameExtractor headerExtractor, HyperTextDetailExtractor detailExtractor, ObjectMapper objectMapper) {
		this.varyExtractor = headerExtractor;
		this.detailExtractor = detailExtractor;
		this.headerManager = new HyperTextDetailHeaderManager(objectMapper);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler) {
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			setHeaders(method, response);
			setVary(request, response);
		}
		return true;
	}

	private void setVary(HttpServletRequest request, HttpServletResponse response) {
		Set<String> header = this.varyExtractor.getHeaders(request);
		if (header.size() > 0) {	
			response.addHeader("Vary", String.join(",", header));
		}
	}

	private void setHeaders(Method method, HttpServletResponse response) {
		Map<String, HyperTextDetail> header = this.detailExtractor.getDetails(method);
		headerManager.addHeaders(response, header);
	}
}
