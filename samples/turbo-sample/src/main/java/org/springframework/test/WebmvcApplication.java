package org.springframework.test;

import java.util.LinkedHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.annotation.RequestScope;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@SpringBootApplication
public class WebmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebmvcApplication.class, args);
	}

	@Bean
	@RequestScope
	@ConfigurationProperties(prefix = "app")
	public Application app() {
		return new Application();
	}

	@Bean
	@ConditionalOnMissingBean(name = "extraThymeleafViewResolver")
	ThymeleafViewResolver extraThymeleafViewResolver(ThymeleafProperties properties,
			SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine);
		resolver.setCharacterEncoding(properties.getEncoding().name());
		resolver.setContentType(
				appendCharset(MimeTypeUtils.parseMimeType("text/vnd.turbo-stream.html"), resolver.getCharacterEncoding()));
		resolver.setProducePartialOutputWhileProcessing(
				properties.getServlet().isProducePartialOutputWhileProcessing());
		resolver.setExcludedViewNames(properties.getExcludedViewNames());
		resolver.setViewNames(properties.getViewNames());
		// This resolver acts as a fallback resolver (e.g. like a
		// InternalResourceViewResolver) so it needs to have low precedence
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 3);
		resolver.setCache(properties.isCache());
		return resolver;
	}

	private String appendCharset(MimeType type, String charset) {
		if (type.getCharset() != null) {
			return type.toString();
		}
		LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
		parameters.put("charset", charset);
		parameters.putAll(type.getParameters());
		return new MimeType(type, parameters).toString();
	}
}
