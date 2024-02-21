package pate_d_or.equipe.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Table(name = "orders")
@Data
public class RestaurantOrder {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 4)
	private String state;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_table")
	private RestaurantTable table;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name= "Orders_Dishes", 
			joinColumns = {@JoinColumn(name = "id_order")}, 
			inverseJoinColumns = {@JoinColumn(name = "id_dish")})
	private List<Dish> dishes;


}
