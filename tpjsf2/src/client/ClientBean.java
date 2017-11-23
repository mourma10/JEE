package client;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean
@SessionScoped
public class ClientBean {
	private DataSource dataSource;
	private Client client = new Client();
	private List<Client> clients;
	private ClientDao clientDao;
	private boolean editMode = false;
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	public ClientBean() throws NamingException{
		dataSource = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/dic3");
		clientDao = new ClientDao(dataSource);
		
	}
	
	public String list(){
		clients = clientDao.listClient();
		return "/client/index";
	}
	
	public String save(){
		if(!this.editMode)
			clientDao.save(client);
		else
			clientDao.update(client);
		this.editMode = false;
		client = new Client();
		return list();
	}
	
	public String update(){
		this.editMode = true;
		return list();
	}
	
	public String delete(){
		clientDao.delete(client);
		client = new Client();
		return list();
	}

}
