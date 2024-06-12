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
 * Annotation to specify how the response will be swapped.
 * See <a href="https://htmx.org/attributes/hx-swap/">hx-swap</a> for possible
 * values.
 *
 * @see <a href="https://htmx.org/reference/#response_headers">HX-Reswap</a>
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmxReswapResponse {

	/**
	 * A value to specify how the response will be swapped.
	 *
	 * @see <a href="https://htmx.org/attributes/hx-swap/">hx-swap</a>
	 */
	HxSwapType value() default HxSwapType.INNER_HTML;

	/**
	 * Set the time in milliseconds that should elapse after receiving a response to
	 * swap the content.
	 */
	long swap() default -1;

	/**
	 * Set the time in milliseconds that should elapse between the swap and the
	 * settle logic.
	 */
	long settle() default -1;

	/**
	 * Changes the scrolling behavior of the target element.
	 */
	Position scroll() default Position.UNDEFINED;

	/**
	 * Used to target a different element for scrolling.
	 */
	String scrollTarget() default "";

	/**
	 * Changes the scrolling behavior of the target element.
	 */
	Position show() default Position.UNDEFINED;

	/**
	 * Used to target a different element for showing.
	 */
	String showTarget() default "";

	/**
	 * Enables the use of the new
	 * <a href=
	 * "https://developer.mozilla.org/en-US/docs/Web/API/View_Transitions_API">View
	 * Transitions API</a>
	 * when a swap occurs.
	 */
	boolean transition() default false;

	/**
	 * Enable or disable auto-scrolling to focused inputs between requests.
	 */
	FocusScroll focusScroll() default FocusScroll.UNDEFINED;

	/**
	 * Represents the values for {@link #focusScroll()}
	 */
	public enum FocusScroll {
		TRUE,
		FALSE,
		UNDEFINED
	}

	/**
	 * Represents the position values for {@link #show()} and {@link #scroll()}
	 */
	public enum Position {
		TOP,
		BOTTOM,
		UNDEFINED
	}

}
