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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class HtmxVaryHeaderExtractorTests {

	private HtmxVaryHeaderExtractor extractor = new HtmxVaryHeaderExtractor();

	@Test
	void testHtmx() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(HtmxRequestHeader.HX_REQUEST.getValue(), "true");
		assertThat(extractor.getHeaders(request)).containsOnly(HtmxRequestHeader.HX_REQUEST.getValue());
	}
	
	@Test
	void testNotHtmx() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("X-Foo", "true");
		assertThat(extractor.getHeaders(request)).isEmpty();
	}
	
}
