package com.metafour.multitenancy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Simple index controller.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping
	public String index() {
		return "index";
	}
}
