An abstraction for hypermedia libraries (like [HTMX](https://htmx.org/), [Unpoly](https://unpoly.com) and [Hotwired Turbo](https://turbo.hotwired.dev)) with Spring MVC. Features:

* A `@RequestMapping` can be annotated with `@HyperTextMapping` to select it for rendering special content. Typically this will be part of a page, not a whole HTML page.
* `HyperTextResponse` as a return value from a `@RequestMapping` method. This is a collection of view renderings, typically HTML fragments. It can also be used to set additional headers on the response.
* `HyperTextRequest` as a method argument for a `@RequestMapping` method. It detects a hypermedia request from one of the libraries and allows the controller to react to it.
* `MultiViewResolver` is a `ViewResolver` that splits its view name parameter by commas and resolves each part separately. This allows a controller to return multiple views in a single response without using `HyperTextResponse` (but all the views get the same model when they render).

HTMX, Unpoly and Turbo each get their own specific version of the `@HyperTextMapping` annotation and the `HyperTextResponse`. This allows the controller to react differently to requests from different libraries.

Example with HTMX:

```java
@HtmxMapping
@PostMapping(path = "/greet")
HtmxResponse name(Map<String, Object> model, @RequestParam String name) {
	...
	return HtmxResponse.builder().view("greet::main").view("layout::menu").build();
}
```

or taking advantage of the `MultiViewResolver` and `HyperTextRequest`:

```java
@PostMapping(path = "/greet")
String name(Map<String, Object> model, @RequestParam String name, HyperTextRequest hx) {
	...
	return hx.isActive() ? "greet::main,layout::menu" : "greet";
}
```