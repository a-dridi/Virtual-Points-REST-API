package at.adridi.virtualpoints.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Default constants and map of transaction types. 0 is widthdraw, 1 is deposit,
 * etc.
 * 
 * @author A.Dridi
 *
 */

public class TransactionTypes {
	Map<String, Integer> transactionTypesMap = new HashMap<>();

	public TransactionTypes() {
		transactionTypesMap.put("Widthdraw", 0);
		transactionTypesMap.put("Deposit", 1);
		transactionTypesMap.put("Transfer", 2);
	}

	public Map<String, Integer> getTransactionTypesMap() {
		return transactionTypesMap;
	}

	public Integer getTransactionTypeNumber(String transactionTypeString) {
		return transactionTypesMap.get(transactionTypeString);
	}
}

enum TransactionTypesConsts {
	WIDTHDRAW, DEPOSIT, TRANSFER
}
