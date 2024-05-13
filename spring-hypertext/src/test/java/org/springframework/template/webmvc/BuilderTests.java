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
package org.springframework.template.webmvc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FooTests {

	@Test
	void test() {
		assertThat(B.builder().build()).isInstanceOf(B.class);
		assertThat(C.builder().build()).isInstanceOf(C.class);
	}
	
}

class A {

	public static BasicBuilder<?> builder() {
		return new Builder<>();
	}

	protected A(Builder<?> builder) {
	}

	protected A(BasicBuilder<?> builder) {
	}

	static class BasicBuilder<T extends BasicBuilder<T>> {
		public A build() {
			return new A(this);
		}
		protected BasicBuilder<T> self() {
			return this;
		}
	}
	static class Builder<T extends Builder<T>> extends BasicBuilder<T> {
		public A build() {
			return new A(this);
		}
		protected Builder<T> self() {
			return this;
		}
	}}

class B extends A {
	
	public static Builder builder() {
		return new Builder();
	}

	private B(Builder builder) {
		super(builder);
	}

	static class Builder extends A.Builder<Builder> {
		public B build() {
			return new B(this);
		}
		@Override
		protected Builder self() {
			return this;
		}
	}
}

class C extends A {
	
	public static Builder builder() {
		return new Builder();
	}

	private C(Builder builder) {
		super(builder);
	}

	static class Builder extends A.BasicBuilder<Builder> {
		public C build() {
			return new C(this);
		}
		@Override
		protected Builder self() {
			return this;
		}
	}
}