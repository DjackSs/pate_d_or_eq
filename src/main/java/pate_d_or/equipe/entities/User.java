package pate_d_or.equipe.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity @Table(name = "Users")
@Data
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String lastname;
	private String email;
	private String password;
	private String token;
	private String role;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	private List<Message> messages;
}
