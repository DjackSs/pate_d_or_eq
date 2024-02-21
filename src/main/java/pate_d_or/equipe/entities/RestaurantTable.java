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

@Entity @Table(name = "tables")
@Data
public class RestaurantTable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "number_place")
	private int numberPlace;
	
	@Column(length = 4)
	private String state;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_restaurant")
	private Restaurant restaurant;
	
}
