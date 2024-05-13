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
package org.springframework.template.webmvc.unpoly;

import java.util.Map;

import org.springframework.template.webmvc.hypertext.HyperTextDetail;
import org.springframework.template.webmvc.hypertext.HyperTextResponse;

public class UnpolyResponse extends HyperTextResponse {

	private UnpolyResponse(Builder builder) {
		super(builder);
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getAcceptLayer() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_ACCEPT_LAYER.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getDismissLayer() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_DISMISS_LAYER.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getEvictCache() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_EVICT_CACHE.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getExpireCache() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_EXPIRE_CACHE.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getLocation() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_LOCATION.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getMethod() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_METHOD.getValue());
		return detail == null ? null : detail.asString();
	}

	public String getTitle() {
		HyperTextDetail detail = getDetails().get(UnpolyResponseHeader.UP_TITLE.getValue());
		return detail == null ? null : detail.asString();
	}

	public static class Builder extends HyperTextResponse.Builder<Builder> {

		public UnpolyResponse build() {
			return new UnpolyResponse(this);
		}

		protected Builder self() {
			return this;
		}

		public Builder and(UnpolyResponse other) {
			super.and(other);
			return this;
		}

		public Builder evict(String pattern) {
			return set(UnpolyResponseHeader.UP_EVICT_CACHE.getValue(), pattern);
		}

		public Builder expire(String pattern) {
			return set(UnpolyResponseHeader.UP_EXPIRE_CACHE.getValue(), pattern);
		}

		public Builder location(String path) {
			return set(UnpolyResponseHeader.UP_LOCATION.getValue(), path);
		}

		public Builder method(String method) {
			return set(UnpolyResponseHeader.UP_METHOD.getValue(), method);
		}

		public Builder title(String title) {
			return set(UnpolyResponseHeader.UP_TITLE.getValue(), title);
		}

		public Builder acceptLayer(String layer) {
			return set(UnpolyResponseHeader.UP_ACCEPT_LAYER.getValue(), layer);
		}

		public Builder dismissLayer(String layer) {
			return set(UnpolyResponseHeader.UP_DISMISS_LAYER.getValue(), layer);
		}

		public Builder event(String event) {
			return add(UnpolyResponseHeader.UP_EVENTS.getValue(), event);
		}

		public Builder event(String event, Object detail) {
			return add(UnpolyResponseHeader.UP_EVENTS.getValue(), event, detail);
		}

	}

	public Map<String, Object> getEvents() {
		return getDetails().get(UnpolyResponseHeader.UP_EVENTS.getValue()).asMap();
	}

}
