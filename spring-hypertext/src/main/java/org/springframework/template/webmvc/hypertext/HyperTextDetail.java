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
package org.springframework.template.webmvc.hypertext;

import java.util.LinkedHashMap;
import java.util.Map;

public class HyperTextDetail {

	private String value;
	private Object object;
	private Map<String, Object> details;

	private HyperTextDetail(Map<String, Object> details, Object object, String value) {
		this.details = details;
		this.object = object;
		this.value = value;
	}

	public static HyperTextDetail of(String value) {
		return new HyperTextDetail(null, null, value);
	}

	public static HyperTextDetail of(Object object) {
		if (object instanceof String) {
			return new HyperTextDetail(null, null, (String) object);
		}
		if (object instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) object;
			return new HyperTextDetail(map, null, null);
		}
		return new HyperTextDetail(null, object, null);
	}

	public static HyperTextDetail from(Map<String, Object> of) {
		return new HyperTextDetail(new LinkedHashMap<>(of), null, null);
	}

	public String asString() {
		return value;
	}

	public Object asObject() {
		return object != null ? object : value;
	}

	public Map<String, Object> asMap() {
		if (this.details == null) {
			this.details = new LinkedHashMap<>();
		}
		return details;
	}

	public boolean isString() {
		return value != null;
	}

	public boolean isObject() {
		return object != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HyperTextDetail other = (HyperTextDetail) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		return true;
	}

}
