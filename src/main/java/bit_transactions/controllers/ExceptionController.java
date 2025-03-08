package bit_transactions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {
	
	@RequestMapping("/backHomeException")
	public String backToHome() {
		// Redirect to the home page 
		return "redirect:/homePage";
	}
}
