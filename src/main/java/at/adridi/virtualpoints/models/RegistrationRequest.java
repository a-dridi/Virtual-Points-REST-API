package at.adridi.virtualpoints.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegistrationRequest {
	private String email;
	private String password;
	private String forename;
	private String surname;

}
