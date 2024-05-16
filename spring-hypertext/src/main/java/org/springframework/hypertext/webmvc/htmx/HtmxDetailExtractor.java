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
package org.springframework.hypertext.webmvc.htmx;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.hypertext.webmvc.AnnotationHyperTextDetailExtractorHelper;
import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.hypertext.webmvc.HyperTextDetailExtractor;

public class HtmxDetailExtractor implements HyperTextDetailExtractor {

	private AnnotationHyperTextDetailExtractorHelper helper = new AnnotationHyperTextDetailExtractorHelper();

	@Override
	public Map<String, HyperTextDetail> getDetails(Method method) {
		Map<String, HyperTextDetail> details = new LinkedHashMap<>();
		HyperTextDetail detail = helper.getDetails(method, HtmxTriggerResponse.class, "value");
		if (detail != null) {
			AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method,
					HtmxTriggerResponse.class);
			if (attrs != null) {
				HtmxTriggerLifecycle value = attrs.getEnum("lifecycle");
				details.put(value.getHeaderName(), detail);
			}
		}
		if (method.isAnnotationPresent(HtmxRefreshResponse.class)) {
			details.put(HtmxResponseHeader.HX_REFRESH.getValue(), HyperTextDetail.of("true"));
		}
		return details;
	}
}
