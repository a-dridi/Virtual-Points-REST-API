package at.adridi.virtualpoints.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ownerId;

	private String forename;
	private String surname;
	@NotBlank(message="Email must be entered!")
	@Column(unique=true)
	private String email;
	@NotBlank(message="Password must be entered!")
	private String password;
	@OneToMany(mappedBy = "owner")
	private List<Account> accounts = new ArrayList<>();

}
