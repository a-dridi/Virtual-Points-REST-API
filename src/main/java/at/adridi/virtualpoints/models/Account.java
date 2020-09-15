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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer accountId;
	private Integer balance;
	private Date opened;
	@ManyToOne(cascade=CascadeType.ALL)
	private Owner owner;
	@ManyToOne(cascade=CascadeType.ALL)
	private AccountType accountType;
	
	@OneToMany(mappedBy = "account")
	private List<Transaction> transactions = new ArrayList<>();

}
