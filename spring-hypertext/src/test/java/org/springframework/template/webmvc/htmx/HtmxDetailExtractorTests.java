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

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.template.webmvc.hypertext.HyperTextDetail;
import org.springframework.util.ReflectionUtils;

public class HtmxDetailExtractorTests {

	private HtmxDetailExtractor extractor = new HtmxDetailExtractor();

	@Test
	void testTrigger() {
		Method method = ReflectionUtils.findMethod(getClass(), "trigger");
		Map<String, HyperTextDetail> details = extractor.getDetails(method);
		assertThat(details).containsKey(HtmxResponseHeader.HX_TRIGGER.getValue());
		assertThat(details.get(HtmxResponseHeader.HX_TRIGGER.getValue()).asMap()).containsKey("event");
	}

	@Test
	void testSwap() {
		Method method = ReflectionUtils.findMethod(getClass(), "swap");
		Map<String, HyperTextDetail> details = extractor.getDetails(method);
		assertThat(details).containsKey(HtmxResponseHeader.HX_TRIGGER_AFTER_SWAP.getValue());
		assertThat(details.get(HtmxResponseHeader.HX_TRIGGER_AFTER_SWAP.getValue()).asMap()).containsKey("event");
	}

	@Test
	void testRefresh() {
		Method method = ReflectionUtils.findMethod(getClass(), "refresh");
		Map<String, HyperTextDetail> details = extractor.getDetails(method);
		assertThat(details).containsKey(HtmxResponseHeader.HX_REFRESH.getValue());
		assertThat(details.get(HtmxResponseHeader.HX_REFRESH.getValue()).asString()).isEqualTo("true");
	}

	@Test
	void testNotHtmx() {
		Method method = ReflectionUtils.findMethod(getClass(), "nohtmx");
		assertThat(extractor.getDetails(method)).isEmpty();
	}

	@HtmxTriggerResponse(value = "event")
	public void trigger() {
	}

	@HtmxRefreshResponse
	public void refresh() {
	}

	@HtmxTriggerResponse(value = "event", lifecycle = HtmxTriggerLifecycle.SWAP)
	public void swap() {
	}

	public void nohtmx() {
	}

}
