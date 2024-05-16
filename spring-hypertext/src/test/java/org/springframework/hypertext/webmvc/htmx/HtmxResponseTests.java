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
package org.springframework.hypertext.webmvc.htmx;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.web.servlet.ModelAndView;

public class HtmxResponseTests {

	@Test
	public void testBuilderAndView() {
		ModelAndView modelAndView = new ModelAndView("viewName");
		HtmxResponse response = HtmxResponse.builder()
				.view(modelAndView)
				.build();

		assertThat(response.getViews()).containsOnly(modelAndView);
	}

	@Test
	public void testBuilderAndViewName() {
		HtmxResponse response = HtmxResponse.builder()
				.view("viewName")
				.build();

		assertThat(response.getViews()).hasSize(1);
		assertThat(response.getViews().iterator().next().getViewName()).isEqualTo("viewName");
	}

	@Test
	public void testBuilderAndHeader() {
		HtmxResponse response = HtmxResponse.builder()
				.set("Content-Type", "application/json")
				.build();

		Map<String, HyperTextDetail> expectedHeaders = new LinkedHashMap<>();
		expectedHeaders.put("Content-Type", HyperTextDetail.of("application/json"));

		assertThat(response.getDetails()).isEqualTo(expectedHeaders);
	}

	@Test
	public void testBuilderAndDetailHeader() {
		HtmxResponse response = HtmxResponse.builder()
				.add("X-Custom-Header", "key", "value")
				.build();

		Map<String, HyperTextDetail> expectedDetailHeaders = Collections.singletonMap(
				"X-Custom-Header",
				HyperTextDetail.from(Collections.singletonMap("key", "value")));

		assertThat(response.getDetails()).isEqualTo(expectedDetailHeaders);
	}

	@Test
	public void testAnd() {
		ModelAndView modelAndView = new ModelAndView("viewName");
		HtmxResponse other = HtmxResponse.builder()
				.set("Content-Type", "application/json")
				.view(modelAndView)
				.build();
		HtmxResponse response = HtmxResponse.builder().and(other)
				.build();

		Map<String, HyperTextDetail> expectedHeaders = new LinkedHashMap<>();
		expectedHeaders.put("Content-Type", HyperTextDetail.of("application/json"));
		assertThat(response.getDetails()).isEqualTo(expectedHeaders);

		assertThat(response.getViews()).containsOnly(modelAndView);
	}

	@Test
	public void testAddingTrigger() {
		HtmxResponse response = HtmxResponse.builder()
				.trigger("event")
				.build();

		assertThat(response.getTriggers().keySet()).containsExactly("event");
	}

	@Test
	public void testAddingTriggerWithDetails() {
		var eventDetail = Map.of("detail1", "message1", "detail2", "message2");
		HtmxResponse response = HtmxResponse.builder()
				.trigger("event", eventDetail)
				.build();

		assertThat(response.getTriggers())
				.containsEntry("event", eventDetail);
	}

	@Test
	public void testAddingTriggerAfterSettle() {
		HtmxResponse response = HtmxResponse.builder()
				.triggerAfterSettle("event")
				.build();

		assertThat(response.getTriggersAfterSettle().keySet()).containsExactly("event");
	}

	@Test
	public void testAddingTriggerAfterSettleWithDetails() {
		var eventDetail = Map.of("detail1", "message1", "detail2", "message2");
		HtmxResponse response = HtmxResponse.builder()
				.triggerAfterSettle("event", eventDetail)
				.build();

		assertThat(response.getTriggersAfterSettle())
				.containsEntry("event", eventDetail);
	}

	@Test
	public void testAddingTriggerAfterSwap() {
		HtmxResponse response = HtmxResponse.builder()
				.triggerAfterSwap("event")
				.build();

		assertThat(response.getTriggersAfterSwap().keySet()).containsExactly("event");
	}

	@Test
	public void testAddingTriggerAfterSwapWithDetails() {
		var eventDetail = Map.of("detail1", "message1", "detail2", "message2");
		HtmxResponse response = HtmxResponse.builder()
				.triggerAfterSwap("event", eventDetail)
				.build();

		assertThat(response.getTriggersAfterSwap())
				.containsEntry("event", eventDetail);
	}

	@Test
	public void testAddingResponseToExistingOneShouldMergeTemplatesAndTriggers() {
		var response1 = HtmxResponse.builder()
				.view("view1")
				.trigger("trigger1")
				.view("view2")
				.trigger("trigger2")
				.build();

		var response2 = HtmxResponse.builder()
				.view("view1")
				.trigger("trigger1")
				.and(response1)
				.build();

		assertThat(response2).satisfies(response -> {
			assertThat(response.getViews())
					.extracting(m -> m.getViewName())
					.containsExactly("view1", "view2");

			assertThat(response.getTriggers()
					.keySet()).containsExactly("trigger1", "trigger2");
		});
	}

	@Test
	public void testAddingResponseToExistingOneShouldOverrideProperties() {
		var response1 = HtmxResponse.builder()
				.location("location1")
				.pushUrl("url1")
				.redirect("url1")
				.replaceUrl("url1")
				.reswap(HtmxReswap.innerHtml())
				.retarget("selector1");

		var response2 = HtmxResponse.builder()
				.location("location2")
				.pushUrl("url2")
				.redirect("url2")
				.replaceUrl("url2")
				.reswap(HtmxReswap.outerHtml())
				.retarget("selector2")
				.refresh();

		response1.and(response2.build());

		assertThat(response1.build()).satisfies(response -> {
			assertThat(response.getLocation()).isEqualTo(new HtmxLocation("location2"));
			assertThat(response.getPushUrl()).isEqualTo(null);
			assertThat(response.getRedirect()).isEqualTo("url2");
			assertThat(response.getReplaceUrl()).isEqualTo("url2");
			assertThat(response.getReswap()).isEqualTo(HtmxReswap.outerHtml());
			assertThat(response.getRetarget()).isEqualTo("selector2");
			assertThat(response.isRefresh()).isEqualTo(true);
		});
	}

	@Test
	public void testResponseHeaderProperties() {
		var response = HtmxResponse.builder()
				.trigger("my-trigger")
				.pushUrl("/a/history")
				.redirect("/a/new/page")
				.refresh()
				.retarget("#theThing")
				.reswap(HtmxReswap.afterBegin())
				.build();

		assertThat(response.getTriggers()).containsOnlyKeys("my-trigger");
		assertThat(response.getPushUrl()).isEqualTo("/a/history");
		assertThat(response.getRedirect()).isEqualTo("/a/new/page");
		assertThat(response.isRefresh()).isTrue();
		assertThat(response.getRetarget()).isEqualTo("#theThing");
		assertThat(response.getReswap()).isEqualTo(HtmxReswap.afterBegin());
	}

}