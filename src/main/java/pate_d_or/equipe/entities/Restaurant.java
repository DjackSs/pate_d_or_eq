package pate_d_or.equipe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name= "Restaurants")
@Data
public class Restaurant 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String address;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	private String town;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_card")
	private Card card;
}
