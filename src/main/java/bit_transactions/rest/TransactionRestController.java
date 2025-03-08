package bit_transactions.rest;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bit_transactions.exceptions.NoSuchTransaction;
import bit_transactions.service.Service;
import bit_transactions.transaciton_entity.Transaction;

@RestController
@RequestMapping("/api")
public class TransactionRestController {

	@Autowired
	private Service transService;


	// Define endpoint for "/transactions" - return list of transactions
	@GetMapping("/transactions")
	public List<Transaction> getTransactions() throws Exception {
		return transService.getTransactions();
	}

	// Define endpoint for "/transactions/{transactionId}" - return transaction at
	// index
	@GetMapping("transactions/{transactionId}")
	public Transaction getTransaction(@PathVariable int transactionId) throws NoSuchTransaction, Exception {
		// Just index into the list... keep it simple
		return transService.getTransaction(transactionId);
	}

	
	// Add an exception handler using @ExceptionHandler
	@ExceptionHandler
	public ResponseEntity<TransactionErrorResponse> handleException(Exception e) {
		// Create a studentErrorResponse
		TransactionErrorResponse error = new TransactionErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		// Return ResponseEntity
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}