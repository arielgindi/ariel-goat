package bit_transactions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bit_transactions.dal.TransactionDao;
import bit_transactions.service.Service;
import bit_transactions.transaciton_entity.Transaction;
import bit_transactions.transaciton_entity.TransactionType;

@Controller
public class CreateTransController {
	
	@Autowired
	private Service serviceInstance;
	
	@PostMapping("/saveTransaction")
	public String saveTransaction(@RequestParam("transactionType") String transactionType,
            @RequestParam("otherPhoneNumber") String otherPhoneNumber,
            @RequestParam("transactionValue") String transactionValue,
           @RequestParam("transactionReason") String transactionReason, Model model) {
		// create the transaction from the input
		TransactionType type = TransactionType.valueOf(transactionType.toLowerCase());
		float value = Float.parseFloat(transactionValue.replace("$", "").trim());
		Transaction newTransaction = new Transaction(type, otherPhoneNumber, value, transactionReason);

		// Save the transaction
		try {
			serviceInstance.addTransaction(newTransaction);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "exception";
		}
		
		// Redirect to the home page after saving
		return "redirect:/homePage";
	}
	
	@GetMapping("/backToHome")
	public String backToHome() {
		// Redirect to the home page
		return "redirect:/homePage";
	}

}
