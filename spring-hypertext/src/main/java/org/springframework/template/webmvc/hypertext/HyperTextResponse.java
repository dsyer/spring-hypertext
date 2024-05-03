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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

public class HyperTextResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(HyperTextResponse.class);

	private final Set<ModelAndView> views = new HashSet<>();
	private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	private final Map<String, Map<String, Object>> details = new LinkedHashMap<>();

	protected HyperTextResponse() {
	}

	private HyperTextResponse(Set<ModelAndView> views, MultiValueMap<String, String> headers,
			Map<String, Map<String, Object>> details) {
		this.views.addAll(views);
		this.headers.putAll(headers);
		this.details.putAll(details);
	}

	public HyperTextResponse(Builder<?> builder) {
		this(builder.views, builder.headers, builder.details);
	}

	public static Builder<?> builder() {
		return new Builder<>();
	}

	public Collection<ModelAndView> getViews() {
		return Collections.unmodifiableCollection(views);
	}

	public MultiValueMap<String, String> getHeaders() {
		return new LinkedMultiValueMap<>(HttpHeaders.readOnlyHttpHeaders(headers));
	}

	public Map<String, Map<String, Object>> getDetails() {
		return Collections.unmodifiableMap(details);
	}

	public static class Builder<T extends Builder<T>> {
		private final Set<ModelAndView> views = new HashSet<>();
		private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		private final Map<String, Map<String, Object>> details = new LinkedHashMap<>();

		public HyperTextResponse build() {
			return new HyperTextResponse(this);
		}

		@SuppressWarnings("unchecked")
		protected T self() {
			return (T) this;
		}

		public T and(HyperTextResponse other) {
			other.views.forEach(template -> {
				if (views.stream().anyMatch(mav -> template.equals(mav))) {
					LOGGER.warn("Duplicate template '{}' found while merging HyperTextResponse", template);
				} else {
					views.add(template);
				}
			});
			headers.addAll(other.headers);
			details.putAll(other.details);
			return self();
		}

		public T view(ModelAndView view) {
			views.add(view);
			return self();
		}

		public T view(View view) {
			if (!views.stream().anyMatch(mav -> view.equals(mav.getView()))) {
				views.add(new ModelAndView(view));
			}
			return self();
		}

		public T view(String viewName) {
			if (!views.stream().anyMatch(mav -> viewName.equals(mav.getViewName()))) {
				views.add(new ModelAndView(viewName));
			}
			return self();
		}

		public T add(String name, String value) {
			headers.add(name, value);
			return self();
		}

		public T set(String name, String value) {
			headers.set(name, value);
			return self();
		}

		public T clear(String name) {
			headers.remove(name);
			return self();
		}

		public T detail(String name, String key, Object value) {
			details.computeIfAbsent(name, k -> new LinkedHashMap<>()).put(key, value);
			return self();
		}

	}

}
