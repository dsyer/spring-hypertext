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

public enum UnpolyResponseHeader {

    UP_ACCEPT_LAYER("X-Up-Accept-Layer"),
    UP_DISMISS_LAYER("X-Up-Dismiss-Layer"),
    UP_EVENTS("X-Up-Events"),
    UP_EVICT_CACHE("X-Up-Evict-Cache"),
    UP_EXPIRE_CACHE("X-Up-Expire-Cache"),
    UP_LOCATION("X-Up-Location"),
    UP_METHOD("X-Up-Method"),
    UP_TITLE("X-Up-Title");

    private final String value;

    UnpolyResponseHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
