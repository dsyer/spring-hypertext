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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class HyperTextDetailHeaderManager {

	private final ObjectMapper objectMapper;

	public HyperTextDetailHeaderManager(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void addHeaders(HttpServletResponse response, Map<String, HyperTextDetail> details) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		addDetailHeaders(details, headers);
		addHeaders(headers, response);
	}
	
	private void addDetailHeaders(Map<String, HyperTextDetail> details, MultiValueMap<String, String> headers) {
		for (String name : details.keySet()) {
			headers.add(name, jsonHeaders(details.get(name)));
		}
	}

	private String jsonHeaders(HyperTextDetail value) {
		if (value == null) {
			return "";
		}
		if (value.isString()) {
			return value.asString();
		}
		try {
			if (value.isObject()) {
				return objectMapper.writeValueAsString(value.asObject());
			}
			Map<String, Object> map = value.asMap();
			if (map.values().stream().allMatch(v -> v instanceof Map sub && sub.isEmpty())) {
				return map.keySet().stream().collect(Collectors.joining(","));
			}
			if (map.isEmpty()) {
				return "";
			}
			return objectMapper.writeValueAsString(value.asMap());
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Unable to serialize " + value.getClass(), e);
		}
	}

	private void addHeaders(MultiValueMap<String, String> headers, HttpServletResponse response) {
		for (String name : headers.keySet()) {
			addHeader(response, name, headers.get(name));
		}
	}

	private void addHeader(HttpServletResponse response, String headerName, List<String> values) {
		if (values.isEmpty()) {
			return;
		}

		String value = values.stream()
				.collect(Collectors.joining(","));

		response.setHeader(headerName, value);
		return;
	}

}
