package nl.waisda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InfoController {

	@RequestMapping("/voorwaarden")
	public String voorwaarden(ModelMap modelMap) {
		modelMap.addAttribute("cssClass", "content");
		return "voorwaarden";
	}

	@RequestMapping("/over-het-spel")
	public String overHetSpel(ModelMap modelMap) {
		modelMap.addAttribute("cssClass", "content");
		return "overHetSpel";
	}

	@RequestMapping("/spelinstructies")
	public String hoeWerktHet(ModelMap modelMap) {
		modelMap.addAttribute("cssClass", "content");
		return "spelinstructies";
	}
}
