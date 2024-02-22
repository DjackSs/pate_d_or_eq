package pate_d_or.equipe.entities.admin;

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
import pate_d_or.equipe.entities.Dish;

@Entity
@Table(name="Cards")
@Data
public class AdminCard 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_card")
	private List<AdminRestaurant> restaurants;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_card")
	private List<Dish> dishes;

}
