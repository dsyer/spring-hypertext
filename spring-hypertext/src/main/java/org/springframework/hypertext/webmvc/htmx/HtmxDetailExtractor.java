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
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.hypertext.webmvc.HyperTextDetailExtractor;

public class HtmxDetailExtractor implements HyperTextDetailExtractor {

	@Override
	public Map<String, HyperTextDetail> getDetails(Method method) {
		Map<String, HyperTextDetail> details = new LinkedHashMap<>();
		Set<HtmxTriggerResponse> multi = AnnotatedElementUtils.getMergedRepeatableAnnotations(method, HtmxTriggerResponse.class);
		for (HtmxTriggerResponse detail : multi) {
				HtmxTriggerLifecycle value = detail.lifecycle();
				AnnotationAttributes attrs = AnnotationUtils.getAnnotationAttributes(detail, true, true);
				if (attrs == null) {
					continue;
				}
				details.put(value.getHeaderName(), detail(attrs, "value"));
		}
		if (method.isAnnotationPresent(HtmxRefreshResponse.class)) {
			details.put(HtmxResponseHeader.HX_REFRESH.getValue(), HyperTextDetail.of("true"));
		}
		if (method.isAnnotationPresent(HtmxReselectResponse.class)) {
			AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method,
					HtmxReselectResponse.class);
			details.put(HtmxResponseHeader.HX_RESELECT.getValue(), HyperTextDetail.of(attrs.getString("value")));
		}
		if (method.isAnnotationPresent(HtmxPushUrlResponse.class)) {
			AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method,
					HtmxPushUrlResponse.class);
			details.put(HtmxResponseHeader.HX_PUSH_URL.getValue(), HyperTextDetail.of(attrs.getString("value")));
		}
		if (method.isAnnotationPresent(HtmxRedirectResponse.class)) {
			AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method,
					HtmxRedirectResponse.class);
			details.put(HtmxResponseHeader.HX_REDIRECT.getValue(), HyperTextDetail.of(attrs.getString("value")));
		}
		if (method.isAnnotationPresent(HtmxReplaceUrlResponse.class)) {
			AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method,
					HtmxReplaceUrlResponse.class);
			details.put(HtmxResponseHeader.HX_REPLACE_URL.getValue(), HyperTextDetail.of(attrs.getString("value")));
		}
		if (method.isAnnotationPresent(HtmxRetargetResponse.class)) {
			AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(method,
					HtmxRetargetResponse.class);
			details.put(HtmxResponseHeader.HX_RETARGET.getValue(), HyperTextDetail.of(attrs.getString("value")));
		}
		if (method.isAnnotationPresent(HtmxLocationResponse.class)) {
			HtmxLocation location = convertToLocation(
					AnnotatedElementUtils.findMergedAnnotation(method, HtmxLocationResponse.class));
			details.putAll(HtmxResponse.builder().location(location).build().getDetails());
		}
		if (method.isAnnotationPresent(HtmxReswapResponse.class)) {
			String reswap = convertToReswap(
					AnnotatedElementUtils.findMergedAnnotation(method, HtmxReswapResponse.class));
			details.put(HtmxResponseHeader.HX_RESWAP.getValue(), HyperTextDetail.of(reswap));
		}
		return details;
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

	private HtmxLocation convertToLocation(HtmxLocationResponse annotation) {
		var location = new HtmxLocation();
		location.setPath(annotation.path());
		if (!annotation.source().isEmpty()) {
			location.setSource(annotation.source());
		}
		if (!annotation.event().isEmpty()) {
			location.setEvent(annotation.event());
		}
		if (!annotation.handler().isEmpty()) {
			location.setHandler(annotation.handler());
		}
		if (!annotation.target().isEmpty()) {
			location.setTarget(annotation.target());
		}
		if (!annotation.target().isEmpty()) {
			location.setSwap(annotation.swap());
		}
		return location;
	}

	private String convertToReswap(HtmxReswapResponse annotation) {

		var reswap = new HtmxReswap(annotation.value());
		if (annotation.swap() != -1) {
			reswap.swap(Duration.ofMillis(annotation.swap()));
		}
		if (annotation.settle() != -1) {
			reswap.swap(Duration.ofMillis(annotation.settle()));
		}
		if (annotation.transition()) {
			reswap.transition();
		}
		if (annotation.focusScroll() != HtmxReswapResponse.FocusScroll.UNDEFINED) {
			reswap.focusScroll(annotation.focusScroll() == HtmxReswapResponse.FocusScroll.TRUE);
		}
		if (annotation.show() != HtmxReswapResponse.Position.UNDEFINED) {
			reswap.show(convertToPosition(annotation.show()));
			if (!annotation.showTarget().isEmpty()) {
				reswap.scrollTarget(annotation.showTarget());
			}
		}
		if (annotation.scroll() != HtmxReswapResponse.Position.UNDEFINED) {
			reswap.scroll(convertToPosition(annotation.scroll()));
			if (!annotation.scrollTarget().isEmpty()) {
				reswap.scrollTarget(annotation.scrollTarget());
			}
		}

		return reswap.toString();
	}

	private HtmxReswap.Position convertToPosition(HtmxReswapResponse.Position position) {
		return switch (position) {
			case TOP -> HtmxReswap.Position.TOP;
			case BOTTOM -> HtmxReswap.Position.BOTTOM;
			default -> throw new IllegalStateException("Unexpected value: " + position);
		};
	}
}
