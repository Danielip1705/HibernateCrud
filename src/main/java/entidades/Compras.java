package entidades;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Compras")
public class Compras implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idCompras")
	private int idCompras;
	
	//Relacion con la tabla player
	@ManyToOne
	@JoinColumn(name = "idPlayer",
	foreignKey = @ForeignKey(name = "PLAYER_ID_FK"))
	private Player player;
	
	//Relacion con la tabla games
	@ManyToOne
	@JoinColumn(name = "idGames",
	foreignKey = @ForeignKey(name = "GAMES_ID_FK"))
	private Games game;
	
	@Column(name = "cosa")
	private String cosa;
	
	@Column(name = "precio")
	private double precio;
	
	@Column(name = "fechaCompra")
	private Date fechaCompra;
	
	public Compras() {
		
	}
	
	

	public Compras(Player player, Games game, String cosa, double precio, Date fechaCompra) {
		this.player = player;
		this.game = game;
		this.cosa = cosa;
		this.precio = precio;
		this.fechaCompra = fechaCompra;
	}



	public int getIdCompras() {
		return idCompras;
	}

	public void setIdCompras(int idCompras) {
		this.idCompras = idCompras;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Games getGame() {
		return game;
	}

	public void setGame(Games game) {
		this.game = game;
	}

	public String getCosa() {
		return cosa;
	}

	public void setCosa(String cosa) {
		this.cosa = cosa;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
    
}
