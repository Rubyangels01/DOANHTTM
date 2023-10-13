package poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Meal_Controller {
	@RequestMapping(value = "users/Main", method = RequestMethod.GET)
	public String Home() {
		return "users/Main";
	}
}
