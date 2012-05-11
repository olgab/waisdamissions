package nl.waisda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {
	@RequestMapping("/favicon.ico")
	public String favicon() {
		return "redirect:/static/img/favicon-waisda.ico";
	}

	@RequestMapping("/robots.txt")
	public String robots() {
		return "redirect:/static/robots.txt";
	}
}
