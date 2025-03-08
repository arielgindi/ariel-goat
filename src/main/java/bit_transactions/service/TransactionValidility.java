package bit_transactions.service;

import bit_transactions.dal.TransactionDao;
import bit_transactions.exceptions.InvalidPhoneNumber;
import bit_transactions.exceptions.InvalidTransactionAmount;
import bit_transactions.exceptions.InvalidTransactionAttempt;
import bit_transactions.transaciton_entity.Transaction;
import bit_transactions.transaciton_entity.TransactionType;

/**
 * Updated:
 * - Removed phone number checks (startsWith("05"), length == 10).
 * - Renamed local variable from "phoneNumber" to "artistName".
 */
public class TransactionValidility {

	public static void checkValidility(Transaction trans, TransactionDao daoHand, String myID)
			throws Exception, InvalidPhoneNumber, InvalidTransactionAttempt {

		String artistName = trans.getArtistName();

		// Example: If you want to ensure "Artist" is not empty,
		// you could keep a check like:
		if (artistName == null || artistName.trim().isEmpty()) {
			throw new InvalidPhoneNumber("Artist name cannot be empty.");
		}

		// Remove phone-specific checks here:
		// (No more startsWith("05") or length == 10)

		// Still keep the "can't send to yourself" check
		// but note it compares to "myID" if that is still your phone number
		if (artistName.equals(myID)) {
			throw new InvalidTransactionAttempt("You can't send yourself money.");
		}

		// If the transaction type is "send," ensure we have enough balance
		if (trans.getTransactionValue() > daoHand.getUserBalance() && trans.getType() == TransactionType.send) {
			throw new InvalidTransactionAttempt("You can't send more money than what you have in your account.");
		}

		// If reason is empty, still throw an error
		if (trans.getTransactionReason().length() == 0) {
			throw new InvalidTransactionAttempt("Transactions must contain a reason.");
		}
	}

	public static void checkValueValidility(Transaction trans, float maxPrice)
			throws Exception, InvalidTransactionAmount {

		if (trans.getTransactionValue() == 0) {
			throw new InvalidTransactionAmount("Transaction value can't be 0");
		}
		if (trans.getTransactionValue() < 0) {
			throw new InvalidTransactionAmount("Transaction values can only be positive.");
		}
		if (trans.getTransactionValue() > maxPrice) {
			throw new InvalidTransactionAmount("You cannot transfer more than "
					+ maxPrice + ", that's a lot of money!!");
		}
	}
}
