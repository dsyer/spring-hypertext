package org.springframework.test;

import java.util.Date;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.template.webmvc.hypertext.HyperTextMapping;
import org.springframework.template.webmvc.hypertext.HyperTextRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import jakarta.servlet.http.HttpServletRequest;

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

	@HyperTextMapping(headers = "x-turbo-request-id")
	@GetMapping(path = "/", produces = "text/vnd.turbo-stream.html")
	String indexHyper(Map<String, Object> model) {
		index(model);
		return "index::stream,layout::stream";
	}

	@GetMapping(path = "/greet")
	String greet(Map<String, Object> model) {
		menu(model, "greet");
		model.put("greeting", "Hello World");
		model.put("time", new Date());
		return "greet";
	}

	@HyperTextMapping(headers = "x-turbo-request-id")
	@GetMapping(path = "/greet", produces = "text/vnd.turbo-stream.html")
	String greetHyper(Map<String, Object> model) {
		greet(model);
		return "greet::stream,layout::stream";
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
