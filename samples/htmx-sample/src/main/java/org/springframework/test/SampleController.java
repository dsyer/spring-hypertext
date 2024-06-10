package org.springframework.test;

import java.util.Date;
import java.util.Map;

import org.springframework.hypertext.webmvc.HyperTextRequest;
import org.springframework.hypertext.webmvc.htmx.HtmxMapping;
import org.springframework.hypertext.webmvc.htmx.HtmxRefreshResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxTriggerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController {

	private final Application application;

	public SampleController(Application application) {
		this.application = application;
	}

	@GetMapping(path = "/")
	String index(Map<String, Object> model) {
		menu(model, "home");
		model.put("message", "Welcome");
		model.put("time", new Date());
		return "index";
	}

	@HtmxMapping
	@GetMapping(path = "/")
	HtmxResponse indexHyper(Map<String, Object> model) {
		index(model);
		return HtmxResponse.builder().view("index::main").view("layout::menu").build();
	}

	@GetMapping(path = "/greet")
	HtmxResponse greet(Map<String, Object> model, HyperTextRequest hx) {
		menu(model, "greet");
		model.put("greeting", "Hello World");
		model.put("time", new Date());
		return hx.isActive() ? HtmxResponse.builder().view("greet::main").view("layout::menu").build()
				: HtmxResponse.builder().view("greet").build();
	}

	@GetMapping(path = "/menu")
	@HtmxRefreshResponse
	String menu(Map<String, Object> model, @RequestParam(defaultValue = "home") String active) {
		application.activate(active);
		model.put("app", application);
		return "layout::menu";
	}

	@GetMapping(path = "/logo")
	@HtmxTriggerResponse("logo")
	String logo() {
		return "layout :: logo";
	}

	@PostMapping(path = "/greet")
	HtmxResponse name(Map<String, Object> model, @RequestParam String name, HyperTextRequest hx) {
		greet(model, hx);
		model.put("greeting", "Hello " + name);
		model.put("name", name);
		return hx.isActive() ? HtmxResponse.builder().view("greet::main").view("layout::menu").build()
				: HtmxResponse.builder().view("greet").build();
	}

}
