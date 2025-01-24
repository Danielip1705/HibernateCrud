package entidades;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Player")
public class Player implements Serializable{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "idPlayer")
	private int idPlayer;
	
	@Column(name = "nick")
	private String nick;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	public Player() {
		
	}
	
	public Player(String nick,String pasw,String email) {
		this.nick=nick;
		this.password=pasw;
		this.email=email;
	}

	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}
	
	
	
	
}
