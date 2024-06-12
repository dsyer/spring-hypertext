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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify a CSS selector that allows you to choose which part
 * of the response is used to be swapped in.
 *
 * @see <a href="https://htmx.org/reference/#response_headers">HX-Retarget</a>
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmxReselectResponse {
	/**
	 * A CSS selector that allows you to choose which part of the response is used
	 * to be swapped in.
	 * <p>
	 * Overrides an existing
	 * <a href="https://htmx.org/attributes/hx-select/">hx-select</a> on the
	 * triggering element.
	 */
	String value();
}