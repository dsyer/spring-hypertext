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

import org.springframework.core.annotation.AliasFor;

/**
 * Annotation to do a client side redirect that does not do a full page reload.
 * <p>
 * Note that this annotation does not provide support for specifying {@code values} or {@code headers}.
 * If you want to do this, use {@link HtmxResponse} instead.
 *
 * @see <a href="https://htmx.org/headers/hx-location/">HX-Location Response Header</a>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmxLocationResponse {

    /**
     * The url path to make the redirect.
     * <p>This is an alias for {@link #path}. For example,
     * {@code @HtmxLocationResponse("/foo")} is equivalent to
     * {@code @HtmxLocationResponse(path="/foo")}.
     */
    @AliasFor("path")
    String value() default "";
    /**
     * The url path to make the redirect.
     */
    String path() default "";
    /**
     * The source element of the request
     */
    String source() default "";
    /**
     * An event that "triggered" the request
     */
    String event() default "";
    /**
     * A callback that will handle the response HTML.
     */
    String handler() default "";
    /**
     * The target to swap the response into.
     */
    String target() default "";
    /**
     * How the response will be swapped in relative to the target
     */
    String swap() default "";

}
