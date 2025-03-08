package bit_transactions.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PropertySource("classpath:param.properties")
public class LoginController {
	
	@Value("${Password}")
    private String userPassword;
    
    @Value("${PhoneNumber}")
    private String adminPhoneNumber;
	
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    @GetMapping("/")
    public String showLoginPage1() {
        return "redirect:/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String phoneNumber, @RequestParam String password, HttpServletRequest request, Model model) {
    	if (adminPhoneNumber.equals(phoneNumber) && userPassword.equals(password)) {
            request.getSession().setAttribute("phoneNumber", phoneNumber);
            return "redirect:/homePage";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    
    

}