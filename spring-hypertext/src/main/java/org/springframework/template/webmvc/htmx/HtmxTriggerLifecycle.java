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
package org.springframework.template.webmvc.htmx;

public enum HtmxTriggerLifecycle {
	/**
	 * Trigger events as soon as the response is received.
	 *
	 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger</a>
	 */
	RECEIVE("HX-Trigger"),
	/**
	 * Trigger events after the
	 * <a href="https://htmx.org/docs/#request-operations">settling step</a>.
	 *
	 * @see <a href=
	 *      "https://htmx.org/headers/hx-trigger/">HX-Trigger-After-Settle</a>
	 */
	SETTLE("HX-Trigger-After-Settle"),
	/**
	 * Trigger events after the
	 * <a href="https://htmx.org/docs/#request-operations">swap step</a>.
	 *
	 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger-After-Swap</a>
	 */
	SWAP("HX-Trigger-After-Swap");

	private final String headerName;

	HtmxTriggerLifecycle(String headerName) {
		this.headerName = headerName;
	}

	public String getHeaderName() {
		return this.headerName;
	}
}
