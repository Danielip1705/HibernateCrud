package entidades;
import java.sql.Time;

import javax.persistence.*;

@Entity
@Table(name = "Games")
public class Games {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "idGames")
	private int idGames;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name="tiempoJugado")
	private Time tiempoJugado;
	
	public Games() {
		
	}
	
	public Games(String nombre,Time tiempo) {
		this.nombre = nombre;
		this.tiempoJugado = tiempo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdGames(int idGames) {
		this.idGames = idGames;
	}

	public Time getTiempoJugado() {
		return tiempoJugado;
	}

	public void setTiempoJugado(Time tiempoJugado) {
		this.tiempoJugado = tiempoJugado;
	}

	public int getIdGames() {
		return idGames;
	}
	
}
