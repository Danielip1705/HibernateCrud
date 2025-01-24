package entidades;

import java.time.LocalDate;

import javax.persistence.*;


@Entity
@Table(name = "Compras")
public class Compras {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idCompras")
	private int idCompras;
	
	//Relacion con la tabla player
	@ManyToOne
	@JoinColumn(name = "player_id",
	foreignKey = @ForeignKey(name = "PLAYER_ID_FK"))
	private Player player;
	
	//Relacion con la tabla games
	@ManyToOne
	@JoinColumn(name = "games_id",
	foreignKey = @ForeignKey(name = "GAMES_ID_FK"))
	private Games game;
	
	@Column(name = "cosa")
	private String cosa;
	
	@Column(name = "precio")
	private double precio;
	
	@Column(name = "fechaCompra")
	private LocalDate fechaCompra;
	
	public Compras() {
		
	}
	
	// Constructor que acepta solo los IDs de Player y Game
    public Compras(int idPlayer, int idGame, String cosa, double precio, LocalDate fechaCompra) {
        // Aquí puedes asignar los valores de cosa, precio y fechaCompra directamente
        this.cosa = cosa;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        // Crear objetos Player y Game usando sus IDs (esto dependerá de cómo gestionas el acceso a las entidades)
        this.player = new Player();  // Instanciar el objeto Player
        this.player.setIdPlayer(idPlayer);  // Asignar el ID al objeto Player
        this.game = new Games();  // Instanciar el objeto Game
        this.game.setIdGames(idGame);  // Asignar el ID al objeto Game
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

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
    
}
