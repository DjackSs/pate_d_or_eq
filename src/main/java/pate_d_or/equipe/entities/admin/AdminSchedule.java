package pate_d_or.equipe.entities.admin;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Schedules")
@Data
public class AdminSchedule 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "open_hour")
	private LocalTime openHour;
	
	@Column(name = "close_hour")
	private LocalTime closeHour;
	

}
