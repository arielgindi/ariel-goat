package bit_transactions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bit_transactions.service.Service;
import bit_transactions.transaciton_entity.Transaction;
import bit_transactions.transaciton_entity.TransactionType;

@Controller
public class EditTransController {
	
	@Autowired
	private Service serviceInstance;
	
	@RequestMapping("/updateTransaction")
	public String saveTransaction(@RequestParam("transactionValue") String transactionValue,
			@RequestParam("transactionID") int transId, Model model) {

		// update the transaction
		try {
	        float value = Float.parseFloat(transactionValue.replace("$", "").trim());
			serviceInstance.updateTransaction(transId, value);
		} catch (Exception e) {
	        model.addAttribute("errorMessage", e.getMessage());
	        return "exception";
		}
		
		// Redirect to the home page after updating
		return "redirect:/homePage";
	}
	
	@RequestMapping("/backHome")
	public String backToHome() {
		// Redirect to the home page
		return "redirect:/homePage";
	}
}
