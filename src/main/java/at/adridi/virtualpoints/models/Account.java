package at.adridi.virtualpoints.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.NoArgsConstructor;

@Entity	
@NoArgsConstructor
public class Account{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer accountId;
	private Integer balance;
	private Date openedDate;
	@ManyToOne(cascade=CascadeType.ALL)
	private Owner owner;
	@ManyToOne(cascade=CascadeType.ALL)
	private AccountType accountType;
	
	@OneToMany(mappedBy = "account")
	private List<Transaction> transactions = new ArrayList<>();

	public Account(Integer accountId, Integer balance, Owner owner, AccountType accountType,
			List<Transaction> transactions) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.owner = owner;
		this.accountType = accountType;
		this.transactions = transactions;
		this.openedDate=new Date();
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public Date getOpenedDate() {
		return this.openedDate;
	}
	

	
}
