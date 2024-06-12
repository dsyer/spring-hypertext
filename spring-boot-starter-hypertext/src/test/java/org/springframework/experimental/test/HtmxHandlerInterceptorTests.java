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
package org.springframework.experimental.test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsIterableContainingInRelativeOrder.containsInRelativeOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestController.class)
class HtmxHandlerInterceptorTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHeaderIsSetOnResponseIfHxTriggerIsPresent() throws Exception {
		mockMvc.perform(get("/with-trigger"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger", "eventTriggered"));
	}

	@Test
	public void testHeaderIsSetOnResponseWithMultipleEventsIfHxTriggerIsPresent() throws Exception {
		mockMvc.perform(get("/with-trigger-multiple-events"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger", "event1,event2"));
	}

	@Test
	public void testAfterSwapHeaderIsSetOnResponseIfHxTriggerIsPresent() throws Exception {
		mockMvc.perform(get("/with-trigger-swap"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger-After-Swap", "eventTriggered"));
	}

	@Test
	public void testAfterSettleHeaderIsSetOnResponseIfHxTriggerAfterSettleIsPresent() throws Exception {
		mockMvc.perform(get("/with-trigger-after-settle"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger-After-Settle", "eventTriggered"));
	}

	@Test
	public void testAfterSettleHeaderIsSetOnResponseWithMultipleEventsIfHxTriggerAfterSettleIsPresent()
			throws Exception {
		mockMvc.perform(get("/with-trigger-after-settle-multiple-events"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger-After-Settle", "event1,event2"));
	}

	@Test
	public void testAfterSwapHeaderIsSetOnResponseIfHxTriggerAfterSwapIsPresent() throws Exception {
		mockMvc.perform(get("/with-trigger-after-swap"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger-After-Swap", "eventTriggered"));
	}

	@Test
	public void testAfterSwapHeaderIsSetOnResponseWithMultipleEventsIfHxTriggerAfterSwapIsPresent() throws Exception {
		mockMvc.perform(get("/with-trigger-after-swap-multiple-events"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger-After-Swap", "event1,event2"));
	}

	@Test
	public void testHeadersAreSetOnResponseIfHxTriggersArePresent() throws Exception {
		mockMvc.perform(get("/with-triggers"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Trigger", "event1,event2"))
				.andExpect(header().string("HX-Trigger-After-Settle", "event1,event2"))
				.andExpect(header().string("HX-Trigger-After-Swap", "event1,event2"));
	}

	@Test
	public void testHeaderIsNotSetOnResponseIfHxTriggerNotPresent() throws Exception {
		mockMvc.perform(get("/without-trigger"))
				.andExpect(status().isOk())
				.andExpect(header().doesNotExist("HX-Trigger"));
	}

	@Test
	public void testVary() throws Exception {
		mockMvc.perform(get("/hx-vary").header("HX-Request", "true"))
				.andExpect(status().isOk())
				.andExpect(header().stringValues("Vary", containsInRelativeOrder("HX-Request")));
	}

	@Test
	public void testVaryNoHxRequest() throws Exception {
		mockMvc.perform(get("/hx-vary"))
				.andExpect(status().isOk())
				.andExpect(header().stringValues("Vary", not(containsInRelativeOrder("HX-Request"))));
	}

	@Test
	public void testHxRefresh() throws Exception {
		mockMvc.perform(get("/hx-refresh"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Refresh", "true"));
	}

	@Test
	public void testHxLocationWithContextData() throws Exception {
		mockMvc.perform(get("/hx-location-with-context-data"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Location",
						"{\"path\":\"/path\",\"source\":\"source\",\"event\":\"event\",\"handler\":\"handler\",\"target\":\"target\",\"swap\":\"swap\"}"));
	}

	@Test
	public void testHxLocationWithoutContextData() throws Exception {
		mockMvc.perform(get("/hx-location-without-context-data"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Location", "/path"));
	}

	@Test
	public void testHxPushUrl() throws Exception {
		mockMvc.perform(get("/hx-push-url"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Push-Url", "/path"));
	}

	@Test
	public void testHxRedirect() throws Exception {
		mockMvc.perform(get("/hx-redirect"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Redirect", "/path"));
	}

	@Test
	public void testHxReplaceUrl() throws Exception {
		mockMvc.perform(get("/hx-replace-url"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Replace-Url", "/path"));
	}

	@Test
	public void testHxReswap() throws Exception {
		mockMvc.perform(get("/hx-reswap"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Reswap", "innerHTML swap:300ms"));
	}

	@Test
	public void testHxRetarget() throws Exception {
		mockMvc.perform(get("/hx-retarget"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Retarget", "#target"));
	}

	@Test
	public void testHxReselect() throws Exception {
		mockMvc.perform(get("/hx-reselect"))
				.andExpect(status().isOk())
				.andExpect(header().string("HX-Reselect", "#target"));
	}

}
