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
package org.springframework.experimental.test;

import org.springframework.hypertext.webmvc.htmx.HtmxLocationResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxPushUrlResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxRedirectResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxRefreshResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxReplaceUrlResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxReselectResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxReswapResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxRetargetResponse;
import org.springframework.hypertext.webmvc.htmx.HtmxTriggerLifecycle;
import org.springframework.hypertext.webmvc.htmx.HtmxTriggerResponse;
import org.springframework.hypertext.webmvc.htmx.HxSwapType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestController {

    @GetMapping("/without-trigger")
    @ResponseBody
    public String methodWithoutAnnotations() {
        return "";
    }

    @GetMapping("/with-trigger")
    @HtmxTriggerResponse("eventTriggered")
    @ResponseBody
    public String methodWithHxTrigger() {
        return "";
    }

    @GetMapping("/with-trigger-multiple-events")
    @HtmxTriggerResponse({ "event1", "event2" })
    @ResponseBody
    public String methodWithHxTriggerAndMultipleEvents() {
        return "";
    }

    @GetMapping("/with-trigger-settle")
    @HtmxTriggerResponse(value = "eventTriggered", lifecycle = HtmxTriggerLifecycle.SETTLE)
    @ResponseBody
    public String methodWithHxTriggerAndLifecycleSettle() {
        return "";
    }

    @GetMapping("/with-trigger-swap")
    @HtmxTriggerResponse(value = "eventTriggered", lifecycle = HtmxTriggerLifecycle.SWAP)
    @ResponseBody
    public String methodWithHxTriggerAndLifecycleSwap() {
        return "";
    }

    @GetMapping("/with-trigger-after-settle-multiple-events")
    @HtmxTriggerResponse(value = { "event1", "event2" }, lifecycle = HtmxTriggerLifecycle.SETTLE)
    @ResponseBody
    public String methodWithHxTriggerAfterSettleAndMultipleEvents() {
        return "";
    }

    @GetMapping("/with-trigger-after-swap")
    @HtmxTriggerResponse(value = "eventTriggered", lifecycle = HtmxTriggerLifecycle.SWAP)
    @ResponseBody
    public String methodWithHxTriggerAfterSwap() {
        return "";
    }

    @GetMapping("/with-trigger-after-swap-multiple-events")
    @HtmxTriggerResponse(value = { "event1", "event2" }, lifecycle = HtmxTriggerLifecycle.SWAP)
    @ResponseBody
    public String methodWithHxTriggerAfterSwapAndMultipleEvents() {
        return "";
    }

    @GetMapping("/with-triggers")
    @HtmxTriggerResponse({ "event1", "event2" })
    @HtmxTriggerResponse(value = { "event1", "event2" }, lifecycle = HtmxTriggerLifecycle.SETTLE)
    @HtmxTriggerResponse(value = { "event1", "event2" }, lifecycle = HtmxTriggerLifecycle.SWAP)
    @ResponseBody
    public String methodWithHxTriggers() {
        return "";
    }

    // TODO: Add this test
	// @GetMapping("/updates-sidebar")
    // @HxUpdatesSidebar
    // @ResponseBody
    // public String updatesSidebar() {
    //     return "";
    // }

    @GetMapping("/hx-refresh")
    @HtmxRefreshResponse
    @ResponseBody
    public String hxRefresh() {
        return "";
    }

    @GetMapping("/hx-vary")
    @ResponseBody
    public String hxVary() {
        return "";
    }

    @GetMapping("/hx-location-without-context-data")
    @HtmxLocationResponse("/path")
    @ResponseBody
    public String hxLocationWithoutContextData() {
        return "";
    }

    @GetMapping("/hx-location-with-context-data")
    @HtmxLocationResponse(path = "/path", source = "source", event = "event", handler = "handler", target = "target", swap = "swap")
    @ResponseBody
    public String hxLocationWithContextData() {
        return "";
    }

    @GetMapping("/hx-push-url")
    @HtmxPushUrlResponse("/path")
    @ResponseBody
    public String hxPushUrl() {
        return "";
    }

    @GetMapping("/hx-redirect")
    @HtmxRedirectResponse("/path")
    @ResponseBody
    public String hxRedirect() {
        return "";
    }

    @GetMapping("/hx-replace-url")
    @HtmxReplaceUrlResponse("/path")
    @ResponseBody
    public String hxReplaceUrl() {
        return "";
    }

    @GetMapping("/hx-reswap")
    @HtmxReswapResponse(value = HxSwapType.INNER_HTML, swap = 300)
    @ResponseBody
    public String hxReswap() {
        return "";
    }

    @GetMapping("/hx-retarget")
    @HtmxRetargetResponse("#target")
    @ResponseBody
    public String hxRetarget() {
        return "";
    }

    @GetMapping("/hx-reselect")
    @HtmxReselectResponse("#target")
    @ResponseBody
    public String hxReselect() {
        return "";
    }

}
