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

import org.springframework.template.webmvc.hypertext.HyperTextResponse;

public class HtmxResponse extends HyperTextResponse {

	protected HtmxResponse() {
		super();
	}

	private HtmxResponse(Builder builder) {
		super(builder);
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getRedirect() {
		return getHeaders().getFirst(HtmxResponseHeader.HX_REDIRECT.getValue());
	}

	public String getPushUrl() {
		return getHeaders().getFirst(HtmxResponseHeader.HX_PUSH_URL.getValue());
	}

	public String getReplaceUrl() {
		return getHeaders().getFirst(HtmxResponseHeader.HX_REPLACE_URL.getValue());
	}

	public String getReselect() {
		return getHeaders().getFirst(HtmxResponseHeader.HX_RESELECT.getValue());
	}

	public String getRetarget() {
		return getHeaders().getFirst(HtmxResponseHeader.HX_RETARGET.getValue());
	}

	public static class Builder extends HyperTextResponse.Builder<Builder> {

		public HtmxResponse build() {
			return new HtmxResponse(this);
		}

		protected Builder self() {
			return this;
		}

		public Builder redirect(String url) {
			return set(HtmxResponseHeader.HX_REDIRECT.getValue(), url);
		}

		public Builder pushUrl(String url) {
			clear(HtmxResponseHeader.HX_REPLACE_URL.getValue());
			return set(HtmxResponseHeader.HX_PUSH_URL.getValue(), url);
		}

		public Builder replaceUrl(String url) {
			clear(HtmxResponseHeader.HX_PUSH_URL.getValue());
			return set(HtmxResponseHeader.HX_REPLACE_URL.getValue(), url);
		}

		public Builder reselect(String cssSelector) {
			return set(HtmxResponseHeader.HX_RESELECT.getValue(), cssSelector);
		}

		public Builder retarget(String cssSelector) {
			return set(HtmxResponseHeader.HX_RETARGET.getValue(), cssSelector);
		}

	}

}
