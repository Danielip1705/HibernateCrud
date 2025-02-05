package entidades;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "Games")
public class Games implements Serializable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "idGames")
	private long idGames;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name="tiempoJugado")
	private LocalTime tiempoJugado;
	
	public Games() {
		
	}
	
	
	
	public Games(long idGames) {
		this.idGames = idGames;
	}



	public Games(String nombre,LocalTime tiempo) {
		this.nombre = nombre;
		this.tiempoJugado = tiempo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdGames(long idGames) {
		this.idGames = idGames;
	}

	public LocalTime getTiempoJugado() {
		return tiempoJugado;
	}

	public void setTiempoJugado(LocalTime tiempoJugado) {
		this.tiempoJugado = tiempoJugado;
	}

	public long getIdGames() {
		return idGames;
	}
	
}
