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
package org.springframework.hypertext.webmvc.unpoly;

import java.util.HashSet;
import java.util.Set;

import org.springframework.hypertext.webmvc.HeaderNameExtractor;

import jakarta.servlet.http.HttpServletRequest;

public class UnpolyVaryHeaderExtractor implements HeaderNameExtractor {

	@Override
	public Set<String> getHeaders(HttpServletRequest request) {
		Set<String> result = new HashSet<>();
		for (UnpolyRequestHeader header : UnpolyRequestHeader.values()) {
			if (request.getHeader(header.getValue()) != null) {
				result.add(header.getValue());
			}
		}
		return result;
	}
	
}
