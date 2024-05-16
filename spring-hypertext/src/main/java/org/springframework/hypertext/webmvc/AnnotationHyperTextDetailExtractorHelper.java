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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

public class AnnotationHyperTextDetailExtractorHelper {

	public HyperTextDetail getDetails(Method method, Class<? extends Annotation> annotationType, String attrName) {
		AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method, annotationType);
		if (attrs == null || !attrs.containsKey(attrName)) {
			return null;
		}
		String[] values = attrs.getStringArray(attrName);
		HyperTextDetail detail = HyperTextDetail.of(new LinkedHashMap<>());
		for (String key : values) {
			String value = null;
			if (key.contains("=")) {
				value = key.substring(key.indexOf("=") + 1).trim();
				key = key.substring(0, key.indexOf("=")).trim();
			}
			detail.asMap().put(key,
					value == null ? Map.of() : value);
		}
		return detail;
	}

}
