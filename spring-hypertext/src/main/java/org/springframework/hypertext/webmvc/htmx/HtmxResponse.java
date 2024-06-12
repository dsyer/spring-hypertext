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

import java.util.Map;

import org.springframework.hypertext.webmvc.HyperTextDetail;
import org.springframework.hypertext.webmvc.HyperTextResponse;

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

		/**
		 * Merges another {@link HtmxResponse} into this builder.
		 *
		 * @param otherResponse Another HtmxResponse that will be merged into this
		 *                      response.
		 * @return the builder
		 */
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

		/**
		 * Can be used to do a client-side redirect to a new location
		 *
		 * @param url the URL. Can be a relative or an absolute url
		 * @return the builder
		 */
		public Builder redirect(String url) {
			return set(HtmxResponseHeader.HX_REDIRECT.getValue(), url);
		}

		/**
		 * Pushes a new URL into the history stack of the browser.
		 * <p>
		 * If you want to prevent the history stack from being updated, use
		 * {@link #preventHistoryUpdate()}.
		 *
		 * @param url the URL to push into the history stack. The URL can be any URL in
		 *            the same origin as the current URL.
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-push/">HX-Push Response Header</a>
		 *      documentation
		 * @see <a href=
		 *      "https://developer.mozilla.org/en-US/docs/Web/API/History/replaceState">history.pushState()</a>
		 */
		public Builder pushUrl(String url) {
			clear(HtmxResponseHeader.HX_REPLACE_URL.getValue());
			return set(HtmxResponseHeader.HX_PUSH_URL.getValue(), url);
		}

		/**
		 * Allows you to replace the most recent entry, i.e. the current URL, in the
		 * browser history stack.
		 * <p>
		 * If you want to prevent the history stack from being updated, use
		 * {@link #preventHistoryUpdate()}.
		 *
		 * @param url the URL to replace in the history stack. The URL can be any URL in
		 *            the same origin as the current URL.
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-replace-url/">HX-Replace-Url
		 *      Response Header</a>
		 * @see <a href=
		 *      "https://developer.mozilla.org/en-US/docs/Web/API/History/replaceState">history.replaceState()</a>
		 */
		public Builder replaceUrl(String url) {
			clear(HtmxResponseHeader.HX_PUSH_URL.getValue());
			return set(HtmxResponseHeader.HX_REPLACE_URL.getValue(), url);
		}

		/**
		 * Prevents the browser history stack from being updated.
		 *
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-push/">HX-Push Response Header</a>
		 *      documentation
		 * @see <a href="https://htmx.org/headers/hx-replace-url/">HX-Replace-Url
		 *      Response Header</a>
		 */
		public Builder preventHistoryUpdate() {
			return pushUrl("false");
		}

		/**
		 * Set a CSS selector that allows you to choose which part of the response is
		 * used to be swapped in.
		 * Overrides an existing
		 * <a href="https://htmx.org/attributes/hx-select/">hx-select</a> on the
		 * triggering element.
		 *
		 * @param cssSelector the CSS selector
		 * @return the builder
		 */
		public Builder reselect(String cssSelector) {
			return set(HtmxResponseHeader.HX_RESELECT.getValue(), cssSelector);
		}

		/**
		 * Set a CSS selector that updates the target of the content update to a
		 * different element on the page
		 *
		 * @param cssSelector the CSS selector
		 * @return the builder
		 */
		public Builder retarget(String cssSelector) {
			return set(HtmxResponseHeader.HX_RETARGET.getValue(), cssSelector);
		}

		/**
		 * Allows you to do a client-side redirect that does not do a full page reload.
		 *
		 * @param path the path
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-location/">HX-Location Response
		 *      Header</a>
		 */
		public Builder location(String path) {
			this.location = new HtmxLocation(path);
			return location(location);
		}

		/**
		 * Allows you to do a client-side redirect that does not do a full page reload.
		 *
		 * @param location the location
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-location/">HX-Location Response
		 *      Header</a>
		 */
		public Builder location(HtmxLocation location) {
			clear(HtmxResponseHeader.HX_LOCATION.getValue());
			if (location.hasContextData()) {
				set(HtmxResponseHeader.HX_LOCATION.getValue(), location);
			} else {
				set(HtmxResponseHeader.HX_LOCATION.getValue(), location.getPath());
			}
			this.location = location;
			return this;
		}

		/**
		 * Set a new swap to specify how the response will be swapped.
		 *
		 * @param reswap the reswap options.
		 * @return the builder
		 */
		public Builder reswap(HtmxReswap reswap) {
			clear(HtmxResponseHeader.HX_RESWAP.getValue());
			this.reswap = reswap;
			return set(HtmxResponseHeader.HX_RESWAP.getValue(), reswap.toHeaderValue());
		}

		/**
		 * If set to "true" the client side will do a full refresh of the page
		 *
		 * @return the builder
		 */
		public Builder refresh() {
			this.refresh = true;
			return set(HtmxResponseHeader.HX_REFRESH.getValue(), "true");
		}

		/**
		 * Adds an event that will be triggered once the response is received.
		 * <p>
		 * Multiple trigger were automatically be merged into the same header.
		 *
		 * @param eventName the event name
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger Response
		 *      Headers</a>
		 */
		public Builder trigger(String event) {
			return add(HtmxResponseHeader.HX_TRIGGER.getValue(), event);
		}

		/**
		 * Adds an event that will be triggered once the response is received.
		 * <p>
		 * Multiple trigger were automatically be merged into the same header.
		 *
		 * @param eventName   the event name
		 * @param eventDetail details along with the event
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger Response
		 *      Headers</a>
		 */
		public Builder trigger(String event, Object detail) {
			return add(HtmxResponseHeader.HX_TRIGGER.getValue(), event, detail);
		}

		/**
		 * Adds an event that will be triggered after the
		 * <a href="https://htmx.org/docs/#request-operations">settling step</a>.
		 * <p>
		 * Multiple triggers were automatically be merged into the same header.
		 *
		 * @param eventName the event name
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger Response
		 *      Headers</a>
		 */
		public Builder triggerAfterSettle(String event) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SETTLE.getValue(), event);
		}

		/**
		 * Adds an event that will be triggered after the
		 * <a href="https://htmx.org/docs/#request-operations">settling step</a>.
		 * <p>
		 * Multiple triggers were automatically be merged into the same header.
		 *
		 * @param eventName   the event name
		 * @param eventDetail details along with the event
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger Response
		 *      Headers</a>
		 */
		public Builder triggerAfterSettle(String event, Object detail) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SETTLE.getValue(), event, detail);
		}

		/**
		 * Adds an event that will be triggered after the
		 * <a href="https://htmx.org/docs/#request-operations">swap step</a>.
		 * <p>
		 * Multiple triggers were automatically be merged into the same header.
		 *
		 * @param eventName the event name
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger Response
		 *      Headers</a>
		 */
		public Builder triggerAfterSwap(String event) {
			return add(HtmxResponseHeader.HX_TRIGGER_AFTER_SWAP.getValue(), event);
		}

		/**
		 * Adds an event that will be triggered after the
		 * <a href="https://htmx.org/docs/#request-operations">swap step</a>.
		 * <p>
		 * Multiple triggers were automatically be merged into the same header.
		 *
		 * @param eventName   the event name
		 * @param eventDetail details along with the event
		 * @return the builder
		 * @see <a href="https://htmx.org/headers/hx-trigger/">HX-Trigger Response
		 *      Headers</a>
		 */
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
