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
import java.lang.annotation.Repeatable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

public class AnnotationHyperTextDetailExtractorHelper {

	public HyperTextDetail[] getDetails(Method method, Class<? extends Annotation> annotationType, String attrName) {
		if (annotationType.isAnnotationPresent(Repeatable.class)) {
			Set<? extends Annotation> annotations = AnnotatedElementUtils.getMergedRepeatableAnnotations(method, annotationType);
			if (annotations==null || annotations.isEmpty()) {
				return new HyperTextDetail[0];
			}
			HyperTextDetail[] details = new HyperTextDetail[annotations.size()];
			Iterator<? extends Annotation> iterator = annotations.iterator();
			for (int i=0; i<annotations.size(); i++) {
				AnnotationAttributes attrs = AnnotationUtils.getAnnotationAttributes(iterator.next(), true, true);
				if (attrs == null) {
					continue;
				}
				details[i] = detail(attrs, attrName);
			}
			return details;
		}
		AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method, annotationType);
		if (attrs == null || !attrs.containsKey(attrName)) {
			return new HyperTextDetail[0];
		}
		return new HyperTextDetail[] {detail(attrs, attrName)};
	}

	private HyperTextDetail detail(AnnotationAttributes attrs, String attrName) {
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
