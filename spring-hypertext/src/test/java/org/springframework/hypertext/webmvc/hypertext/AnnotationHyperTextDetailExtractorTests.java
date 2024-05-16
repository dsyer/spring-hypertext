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
package org.springframework.hypertext.webmvc.hypertext;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.hypertext.webmvc.AnnotationHyperTextDetailExtractorHelper;
import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.hypertext.webmvc.HyperTextMapping;
import org.springframework.util.ReflectionUtils;

public class AnnotationHyperTextDetailExtractorTests {

	private AnnotationHyperTextDetailExtractorHelper extractor = new AnnotationHyperTextDetailExtractorHelper();

	@Test
	void testFoo() {
		Method method = ReflectionUtils.findMethod(getClass(), "foo");
		HyperTextDetail details = extractor.getDetails(method, HyperTextMapping.class, "headers");
		assertThat(details.asMap()).containsEntry("foo", "bar");
	}
	
	@Test
	void testNotFoo() {
		Method method = ReflectionUtils.findMethod(getClass(), "nofoo");
		HyperTextDetail details = extractor.getDetails(method, HyperTextMapping.class, "headers");
		assertThat(details).isNull();
	}

	@HyperTextMapping(headers = "foo=bar")
	public void foo(){}
	
	public void nofoo(){}
	
}
