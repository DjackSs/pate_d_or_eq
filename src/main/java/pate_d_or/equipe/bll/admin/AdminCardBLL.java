package pate_d_or.equipe.bll.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.admin.AdminCardDAO;
import pate_d_or.equipe.entities.admin.AdminCard;

@Service
public class AdminCardBLL 
{
	@Autowired
	private AdminCardDAO adminCardDAO;
	
	public List<AdminCard> findAll()
	{
		return (List<AdminCard>) this.adminCardDAO.findAll();
	}
	
	public  void save(AdminCard newCard)
	{
		this.adminCardDAO.save(newCard);
	}

}
