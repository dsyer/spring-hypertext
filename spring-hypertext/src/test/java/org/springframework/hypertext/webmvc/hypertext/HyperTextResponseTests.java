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
package org.springframework.hypertext.webmvc.hypertext;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.hypertext.webmvc.HyperTextResponse;
import org.springframework.web.servlet.ModelAndView;

public class HyperTextResponseTests {

	@Test
	public void testBuilderAndView() {
		ModelAndView modelAndView = new ModelAndView("viewName");
		HyperTextResponse response = HyperTextResponse.builder()
				.view(modelAndView)
				.build();

		assertThat(response.getViews()).containsOnly(modelAndView);
	}

	@Test
	public void testBuilderAndViewName() {
		HyperTextResponse response = HyperTextResponse.builder()
				.view("viewName")
				.build();

		assertThat(response.getViews()).hasSize(1);
		assertThat(response.getViews().iterator().next().getViewName()).isEqualTo("viewName");
	}

	@Test
	public void testBuilderAndHeader() {
		HyperTextResponse response = HyperTextResponse.builder()
				.set("Content-Type", "application/json")
				.build();

		Map<String, HyperTextDetail> expectedHeaders = new LinkedHashMap<>();
		expectedHeaders.put("Content-Type", HyperTextDetail.of("application/json"));

		assertThat(response.getDetails()).isEqualTo(expectedHeaders);
	}

	@Test
	public void testBuilderAndDetailHeader() {
		HyperTextResponse response = HyperTextResponse.builder()
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
		HyperTextResponse other = HyperTextResponse.builder()
				.set("Content-Type", "application/json")
				.view(modelAndView)
				.build();
		HyperTextResponse response = HyperTextResponse.builder().and(other)
				.build();

		Map<String, HyperTextDetail> expectedHeaders = new LinkedHashMap<>();
		expectedHeaders.put("Content-Type", HyperTextDetail.of("application/json"));
		assertThat(response.getDetails()).isEqualTo(expectedHeaders);

		assertThat(response.getViews()).containsOnly(modelAndView);
	}

}