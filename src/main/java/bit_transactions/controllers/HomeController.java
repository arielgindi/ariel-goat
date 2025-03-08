package bit_transactions.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bit_transactions.dal.UserData;
import bit_transactions.service.Service;
import bit_transactions.transaciton_entity.Transaction;
import bit_transactions.transaciton_entity.TransactionType;



@Controller
public class HomeController {
	@Autowired
	private Service service;

    @GetMapping("/homePage")
    public String showHomePage(HttpServletRequest request, Model model) throws Exception {
        String userName = (String) request.getSession().getAttribute("phoneNumber");
        if (userName == null) {
            return "redirect:/login";	
        }
        List<Transaction> transactions= service.getTransactions();
        model.addAttribute("transactions", transactions);
        double balance = service.getUserBalance();
        model.addAttribute("balance", balance);
        return "homePage";
    }
    
    @RequestMapping("/create")
    public String createTransactionPage(Model model) {
        model.addAttribute("mode", "create");
        return "transaction";
    }
    
    @RequestMapping("/show")
    public String showTransactionPage(@RequestParam(value = "transaction", required = false) Integer transId, Model model) {
    	try {
            Transaction transaction = service.getTransaction(transId);
            model.addAttribute("transaction", transaction);
            model.addAttribute("mode", "view");
            return "transaction";
    	}
	    catch (Exception e) {
	    }
    	return "redirect:/homePage";
    }
    
    @RequestMapping("/edit")
    public String editTransactionPage(@RequestParam(value = "transaction", required = false) Integer transId, Model model) {
        try {
            Transaction transaction = service.getTransaction(transId);
            model.addAttribute("transaction", transaction);
            model.addAttribute("mode", "edit");
            return "transaction";
        } catch (Exception e) {
           
        }
        return "redirect:/homePage";
    }
    
    @RequestMapping("/remove")
    public String removeTransaction(@RequestParam(value = "transaction", required = false) Integer transId) {
        try {
            service.removeTransaction(transId);
        } catch (Exception e) {
        }
        return "redirect:/homePage";  
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }
    
}
