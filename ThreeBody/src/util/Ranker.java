package util;

import java.util.Comparator;

import model.Account;

public class Ranker implements Comparator<Account>{

	@Override
	public int compare(Account ac0, Account ac1) {
		return ac0.getPoint() - ac1.getPoint();
	}

}
