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

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.hypertext.webmvc.AnnotationHyperTextDetailExtractorHelper;
import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.hypertext.webmvc.HyperTextMapping;
import org.springframework.hypertext.webmvc.hypertext.TestMapping.TestMappings;
import org.springframework.util.ReflectionUtils;

public class AnnotationHyperTextDetailExtractorTests {

	private AnnotationHyperTextDetailExtractorHelper extractor = new AnnotationHyperTextDetailExtractorHelper();

	@Test
	void testFoos() {
		Method method = ReflectionUtils.findMethod(getClass(), "foos");
		HyperTextDetail[] details = extractor.getDetails(method, TestMapping.class, "headers");
		assertThat(details[0].asMap()).containsEntry("foo", "bar");
	}
	
	@Test
	void testMoreFoos() {
		Method method = ReflectionUtils.findMethod(getClass(), "moreFoos");
		HyperTextDetail[] details = extractor.getDetails(method, TestMapping.class, "headers");
		assertThat(details[0].asMap()).containsEntry("foo", "bar");
		assertThat(details[1].asMap()).containsEntry("spam", "bucket");
	}
	
	@Test
	void testFoo() {
		Method method = ReflectionUtils.findMethod(getClass(), "foo");
		HyperTextDetail[] details = extractor.getDetails(method, TestMapping.class, "headers");
		assertThat(details[0].asMap()).containsEntry("foo", "bar");
	}
	
	@Test
	void testNotRepeatable() {
		Method method = ReflectionUtils.findMethod(getClass(), "bar");
		HyperTextDetail[] details = extractor.getDetails(method, HyperTextMapping.class, "headers");
		assertThat(details[0].asMap()).containsEntry("foo", "bar");
	}

	@Test
	void testNotFoo() {
		Method method = ReflectionUtils.findMethod(getClass(), "nofoo");
		HyperTextDetail[] details = extractor.getDetails(method, TestMapping.class, "headers");
		assertThat(details).isEmpty();
	}

	@TestMappings(@TestMapping(headers = "foo=bar"))
	public void foos(){}
	
	@TestMapping(headers = "foo=bar")
	@TestMapping(headers = "spam=bucket")
	public void moreFoos(){}
	
	@TestMapping(headers = "foo=bar")
	public void foo(){}
	
	@HyperTextMapping(headers = "foo=bar")
	public void bar(){}
	
	public void nofoo(){}
	
}

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TestMappings.class)
@interface TestMapping {

	String[] headers() default {};

	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
		@interface TestMappings {
			TestMapping[] value();
	}
}
