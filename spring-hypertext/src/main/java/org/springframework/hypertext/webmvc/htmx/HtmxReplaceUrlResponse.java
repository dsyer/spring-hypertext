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
 * Annotation to replace the current URL in the browser
 * <a href="https://developer.mozilla.org/en-US/docs/Web/API/History_API">location history</a>.
 * <p>
 * The possible values are:
 * <ul>
 * <li>{@link HtmxValue#TRUE}, which replaces the current URL with the fetched URL in the history.</li>
 * <li>{@link HtmxValue#FALSE}, which prevents the browser’s current URL from being updated.</li>
 * <li>A URL to replace the current URL in the location bar. This may be relative or absolute, as per 
 * <a href="https://developer.mozilla.org/en-US/docs/Web/API/History/replaceState">history.replaceState()</a>,
 * but must have the same origin as the current URL.</li>
 * </ul>
 *
 * @see <a href="https://htmx.org/headers/hx-replace-url/">HX-Replace-Url</a>
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmxReplaceUrlResponse {
	/**
	 * The value for the {@code HX-Replace-Url} response header.
	 */
	String value();
}
