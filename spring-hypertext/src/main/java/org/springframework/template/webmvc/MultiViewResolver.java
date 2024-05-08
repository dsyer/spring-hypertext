/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.springframework.template.webmvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MultiViewResolver implements ViewResolver, Ordered {

	private final ViewResolver resolver;

	public MultiViewResolver(ViewResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 10;
	}

	@Override
	public View resolveViewName(String name, Locale locale) throws Exception {
		if (name.contains(",")) {
			List<View> templates = new ArrayList<>();
			String[] names = name.split(",");
			for (int i = 0; i < names.length; i++) {
				names[i] = names[i].trim();
			}
			for (String template : names) {
				View value = resolver.resolveViewName(template, locale);
				if (value == null) {
					return null;
				}
				templates.add(value);
			}
			return new MultiView(templates);
		} else {
			return resolver.resolveViewName(name, locale);
		}
	}

}

class MultiView implements View {

	private final List<View> templates;

	public MultiView(List<View> templates) {
		this.templates = templates;
	}

	@Override
	public String getContentType() {
		return MediaType.TEXT_HTML_VALUE;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
		for (View template : templates) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) model;
			template.render(map, request, wrapper);
			wrapper.getWriter().write("\n\n");
		}
		wrapper.copyBodyToResponse();
	}

}