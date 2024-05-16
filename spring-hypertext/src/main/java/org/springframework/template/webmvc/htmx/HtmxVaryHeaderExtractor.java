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
package org.springframework.template.webmvc.htmx;

import java.util.Collections;
import java.util.Set;

import org.springframework.template.webmvc.hypertext.HeaderNameExtractor;

import jakarta.servlet.http.HttpServletRequest;

public class HtmxVaryHeaderExtractor implements HeaderNameExtractor {

	@Override
	public Set<String> getHeaders(HttpServletRequest request) {
		if (request.getHeader(HtmxRequestHeader.HX_REQUEST.getValue()) != null) {
			return Set.of(HtmxRequestHeader.HX_REQUEST.getValue());
		}
		return Collections.emptySet();
	}
	
}
