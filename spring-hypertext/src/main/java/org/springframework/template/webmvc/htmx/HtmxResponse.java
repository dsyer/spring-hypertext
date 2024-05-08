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

import java.util.Map;

import org.springframework.template.webmvc.hypertext.HyperTextDetail;
import org.springframework.template.webmvc.hypertext.HyperTextResponse;

public class HtmxResponse extends HyperTextResponse {

	private final HtmxLocation location;
	private final HtmxReswap reswap;
	private final boolean refresh;

	private HtmxResponse(Builder builder, HtmxLocation location, HtmxReswap reswap, boolean refresh) {
		super(builder);
		this.location = location;
		this.reswap = reswap;
		this.refresh = refresh;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getRedirect() {
		HyperTextDetail detail = getDetails().get(HtmxResponseHeader.HX_REDIRECT.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getPushUrl() {
		HyperTextDetail detail = getDetails().get(HtmxResponseHeader.HX_PUSH_URL.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getReplaceUrl() {
		HyperTextDetail detail = getDetails().get(HtmxResponseHeader.HX_REPLACE_URL.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getReselect() {
		HyperTextDetail detail = getDetails().get(HtmxResponseHeader.HX_RESELECT.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getRetarget() {
		HyperTextDetail detail = getDetails().get(HtmxResponseHeader.HX_RETARGET.getValue());
		return detail == null ? null : detail.asString();
	}

	public HtmxLocation getLocation() {
		return location;
	}

	public HtmxReswap getReswap() {
		return reswap;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public static class Builder extends HyperTextResponse.Builder<Builder> {

		private HtmxLocation location;
		private HtmxReswap reswap;
		private boolean refresh;

		public HtmxResponse build() {
			return new HtmxResponse(this, location, reswap, refresh);
		}

		protected Builder self() {
			return this;
		}

		public Builder and(HtmxResponse other) {
			super.and(other);
			if (other.location != null) {
				this.location = other.location;
			}
			if (other.reswap != null) {
				this.reswap = other.reswap;
			}
			if (other.refresh) {
				this.refresh = true;
			}
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

		public Builder location(String path) {
			this.location = new HtmxLocation(path);
			return location(location);
		}

		public Builder location(HtmxLocation location) {
			clear(HtmxResponseHeader.HX_LOCATION.getValue());
			if (location.hasContextData()) {
				set(HtmxResponseHeader.HX_LOCATION.getValue(), location);
			}
			set(HtmxResponseHeader.HX_LOCATION.getValue(), location.getPath());
			this.location = location;
			return this;
		}

		public Builder reswap(HtmxReswap reswap) {
			clear(HtmxResponseHeader.HX_RESWAP.getValue());
			this.reswap = reswap;
			return set(HtmxResponseHeader.HX_RESWAP.getValue(), reswap.toHeaderValue());
		}

		public Builder refresh() {
			this.refresh = true;
			return set(HtmxResponseHeader.HX_REFRESH.getValue(), "true");
		}

		public Builder trigger(String event) {
			return add(HtmxResponseHeader.HX_TRIGGER.getValue(), event);
		}

		public Builder trigger(String event, Object detail) {
			return add(HtmxResponseHeader.HX_TRIGGER.getValue(), event, detail);
		}

		public Builder triggerAfterSettle(String event) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SETTLE.getValue(), event);
		}

		public Builder triggerAfterSettle(String event, Object detail) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SETTLE.getValue(), event, detail);
		}

		public Builder triggerAfterSwap(String event) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SWAP.getValue(), event);
		}

		public Builder triggerAfterSwap(String event, Object detail) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SWAP.getValue(), event, detail);
		}
	}

	public Map<String, Object> getTriggers() {
		return getDetails().get(HtmxResponseHeader.HX_TRIGGER.getValue()).asMap();
	}

	public Map<String, Object> getTriggersAfterSettle() {
		return getDetails().get(HtmxResponseHeader.HX_TRIGGER_AFTER_SETTLE.getValue()).asMap();
	}

	public Map<String, Object> getTriggersAfterSwap() {
		return getDetails().get(HtmxResponseHeader.HX_TRIGGER_AFTER_SWAP.getValue()).asMap();
	}

}
