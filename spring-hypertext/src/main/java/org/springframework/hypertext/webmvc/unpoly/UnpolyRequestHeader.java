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

import java.util.Set;

public enum UnpolyRequestHeader {

	UP_FAIL_MODE("X-Up-Fail-Mode"),
	UP_FAIL_TARGET("X-Up-Fail-Target"),
	UP_MODE("X-Up-Mode"),
	UP_TARGET("X-Up-Target"),
	UP_VERSION("X-Up-Version"),
	UP_VALIDATE("X-Up-Validate");

	private final String value;

	UnpolyRequestHeader(String value) {
		this.value = value;
	}

	public static boolean isUnpoly(Set<String> names) {
		for (UnpolyRequestHeader header : values()) {
			if (names.contains(header.getValue())) {
				return true;
			}
		}
		return false;
	}

	public String getValue() {
		return value;
	}
}
