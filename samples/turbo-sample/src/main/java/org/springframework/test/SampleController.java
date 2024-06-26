package org.springframework.test;

import java.util.Date;
import java.util.Map;

import org.springframework.hypertext.webmvc.HyperTextRequest;
import org.springframework.hypertext.webmvc.turbo.TurboMapping;
import org.springframework.hypertext.webmvc.turbo.TurboResponse;
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
	String index(Map<String, Object> model, HyperTextRequest hx) {
		menu(model, "home");
		model.put("message", "Welcome");
		model.put("time", new Date());
		return hx.isActive() ? "index::stream,layout::stream" : "index";
	}

	@GetMapping(path = "/greet")
	String greet(Map<String, Object> model) {
		menu(model, "greet");
		model.put("greeting", "Hello World");
		model.put("time", new Date());
		return "greet";
	}

	@TurboMapping
	@GetMapping(path = "/greet")
	TurboResponse greetHyper(Map<String, Object> model) {
		greet(model);
		return TurboResponse.builder().view("greet::stream").view("layout::stream").build();
	}
			
	@GetMapping(path = "/menu")
	String menu(Map<String, Object> model, @RequestParam(defaultValue = "home") String active) {
		application.activate(active);
		model.put("app", application);
		return "layout::menu";
	}

	@GetMapping(path = "/logo")
	String logo() {
		return "layout :: logo";
	}

	@PostMapping(path = "/greet")
	String name(Map<String, Object> model, @RequestParam String name, HyperTextRequest hx) {
		greet(model);
		model.put("greeting", "Hello " + name);
		model.put("name", name);
		// A POST is always a turbo-stream request
		return hx.isActive() ? "greet::stream" : "greet";
	}

}
