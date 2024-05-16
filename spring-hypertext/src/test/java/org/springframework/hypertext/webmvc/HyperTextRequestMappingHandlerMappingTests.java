package org.springframework.hypertext.webmvc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;

public class HyperTextRequestMappingHandlerMappingTests {

	private StaticApplicationContext context = new StaticApplicationContext();
	private MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");

	{
		context.registerSingleton("myController", MyController.class);
		context.registerSingleton("anotherController", AnotherController.class);
	}

	@Test
	public void testGetHyperTextMapping() throws Exception {
		HyperTextRequestMappingHandlerMapping handlerMapping = new HyperTextRequestMappingHandlerMapping();
		handlerMapping.setApplicationContext(context);
		handlerMapping.afterPropertiesSet();
		handlerMapping.getHandlerMethodsForMappingName("/");
		List<HandlerMethod> methods = handlerMapping.getHandlerMethodsForMappingName("MC#myMethod");
		assertThat(methods).hasSize(1);
		request.addHeader("Content-Type", "application/json");
		Object handler = handlerMapping.getHandler(request).getHandler();
		assertThat(handler).isInstanceOf(HandlerMethod.class);
		if (handler instanceof HandlerMethod method) {
			assertThat(method.getMethod()).isEqualTo(methods.get(0).getMethod());
		}
	}

	@Controller
	static class MyController {
		@HyperTextMapping(headers = "Content-Type=application/json")
		@GetMapping("/")
		public void myMethod() {
		}

		@GetMapping("/")
		public void anotherMethod() {
		}
	}

	@Controller
	static class AnotherController {
		@GetMapping("/another")
		public void someMethod() {
		}
	}
}