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
package org.springframework.hypertext.webmvc;

import java.util.Locale;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class HyperTextResponseHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
	private final ViewResolver views;
	private final ObjectFactory<LocaleResolver> locales;
	private final HyperTextDetailHeaderManager headerManager;

	public HyperTextResponseHandlerMethodReturnValueHandler(ViewResolver views,
			ObjectFactory<LocaleResolver> locales,
			ObjectMapper objectMapper) {
		this.views = views;
		this.locales = locales;
		this.headerManager = new HyperTextDetailHeaderManager(objectMapper);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return HyperTextResponse.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {

		HyperTextResponse htmxResponse = (HyperTextResponse) returnValue;
		mavContainer.setView(toView(htmxResponse));

		this.headerManager.addHeaders(webRequest.getNativeResponse(HttpServletResponse.class),
				htmxResponse.getDetails());
	}

	private View toView(HyperTextResponse htmxResponse) {

		Assert.notNull(htmxResponse, "HtmxResponse must not be null!");

		return (model, request, response) -> {
			Locale locale = locales.getObject().resolveLocale(request);
			ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
			for (ModelAndView modelAndView : htmxResponse.getViews()) {
				View view = modelAndView.getView();
				if (view == null) {
					view = views.resolveViewName(modelAndView.getViewName(), locale);
				}
				for (String key : model.keySet()) {
					if (!modelAndView.getModel().containsKey(key)) {
						modelAndView.getModel().put(key, model.get(key));
					}
				}
				Assert.notNull(view, "Template '" + modelAndView + "' could not be resolved");
				view.render(modelAndView.getModel(), request, wrapper);
			}
			wrapper.copyBodyToResponse();
		};
	}

}
