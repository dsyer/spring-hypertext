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
package org.springframework.hypertext.webmvc.unpoly;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class UnpolyVaryHeaderExtractorTests {

	private UnpolyVaryHeaderExtractor extractor = new UnpolyVaryHeaderExtractor();

	@Test
	void testUnpoly() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(UnpolyRequestHeader.UP_VERSION.getValue(), "1.0.0");
		assertThat(extractor.getHeaders(request)).containsOnly(UnpolyRequestHeader.UP_VERSION.getValue());
	}
	
	@Test
	void testMulti() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(UnpolyRequestHeader.UP_VERSION.getValue(), "1.0.0");
		request.addHeader(UnpolyRequestHeader.UP_TARGET.getValue(), "foo");
		assertThat(extractor.getHeaders(request)).contains(UnpolyRequestHeader.UP_VERSION.getValue(), UnpolyRequestHeader.UP_TARGET.getValue());
	}
	
	@Test
	void testNotUnpoly() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("X-Foo", "true");
		assertThat(extractor.getHeaders(request)).isEmpty();
	}
	
}
