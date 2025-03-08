package bit_transactions.service;

import java.io.IOException;

import bit_transactions.dal.TransactionDao;
import bit_transactions.exceptions.*;
import bit_transactions.transaciton_entity.Transaction;
import bit_transactions.transaciton_entity.TransactionType;

public class TransactionValidility {

	public static void checkValidility(Transaction trans, TransactionDao daoHand, String myID) 
			throws Exception, InvalidPhoneNumber, InvalidTransactionAttempt {
		
		String phoneNumber = trans.getOtherPhoneNum();
		
		if(!phoneNumber.startsWith("05"))
			throw new InvalidPhoneNumber("Phone numbers must start with '05'.");
		
		if(phoneNumber.length() != 10)
			throw new InvalidPhoneNumber("Phone numbers must contain 10 digits.");
		
		if(trans.getOtherPhoneNum().equals(myID))
			throw new InvalidTransactionAttempt("You can't send yourself money.");
		
		if(trans.getTransactionValue() > daoHand.getUserBalance() && trans.getType() == TransactionType.send)
			throw new InvalidTransactionAttempt("You can't send more money than what you have in your account.");
				
		if(trans.getTransactionReason().length() == 0)
			throw new InvalidTransactionAttempt("Transactions must contain a reason.");
	}
	
	
	
	public static void checkValueValidility(Transaction trans, float maxPrice)
			throws Exception, InvalidTransactionAmount{
		if(trans.getTransactionValue() == 0)
			throw new InvalidTransactionAmount("Transaction value can't be 0");
		
		if(trans.getTransactionValue() < 0)
			throw new InvalidTransactionAmount("Transaction values can only be positive.");
		if(trans.getTransactionValue() > maxPrice)
			throw new InvalidTransactionAmount("You cannot transfer more than " + maxPrice + ", that's a lot of money!!");

	}
}
