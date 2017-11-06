package com.scsk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/page")
public class OtherViewsController {

    @RequestMapping(value = "/timeout", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView timeout() {
        return new ModelAndView("timeout");
    }
    @RequestMapping(value = "/expire", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView expire() {
        return new ModelAndView("expire");
    }
}
