import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import accesobd.AccesoBD;
import entidades.Compras;
import entidades.Games;
import entidades.Player;
import funciones.FuncionesCompra;
import funciones.FuncionesGames;
import funciones.FuncionesPlayer;

public class CRUD {
	static AccesoBD ins = new AccesoBD();
	// Atributos Player
	static Player player = null;
	static int idPlayer;
	static String nick;
	static String password;
	static String email;
	// Atributos Games
	static Games game = null;
	static int idGames;
	static String nombre;
	static int hora;
	static int min;
	static LocalTime tiempoJugado;
	// Atributos Compras
	static Compras compra = null;
	static String cosa;
	static double precio;
	static int año;
	static int mes;
	static int dia;
	static Date fechaCompra;

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// Variables para el funcionamiento del programa
		int opc = -1;
		final String OPC_INVALIDA = "Pelelín, esta opcion no existe >:[";
		do {

			// imprimimos las opciones disponibles
			System.out.println("Indique que opcion quieres realizar");
			System.out.println("1. Conectarse");
			System.out.println("2. Insertar");
			System.out.println("3. Listar");
			System.out.println("4. Modificar");
			System.out.println("5. Eliminar");
			// el usuario indica la opcion
			opc = sc.nextInt();
			// Limpiamos buffer
			sc.nextLine();

			// Creamos un switch para realizar una accion dependiendo de la opcion elegida
			switch (opc) {
			// Si se elige la opcion 1
			case 1:
				// Mostrara el estado de la conexion
				System.out.println(ins.getSesion());
				break;
			// Si se elige la opcion 2
			case 2:
				// Le preguntamos al usuario que entidad quiere crear
				System.out.println("¿Que entidad quieres crear?");
				// Le mostramos las entidades que hay o si quiere volver
				opcionesEntidades();
				// Inidcamos la opcion
				opc = sc.nextInt();
				// Limpiamos buffer
				sc.nextLine();
				// Dependiendo de la opciones elegida
				switch (opc) {
				// Si es 1 crearemos el jugador
				case 1:
					System.out.println("Indique el nick del jugador");
					nick = sc.nextLine();
					System.out.println("Indique la contraseña del jugador");
					password = sc.nextLine();
					System.out.println("Indique el correo del jugador");
					email = sc.nextLine();
					player = new Player(nick, password, email);
					FuncionesPlayer.crearPlayer(player);
					break;
				// Si es 2 crearemos el juego
				case 2:
					System.out.println("Indique el nombre del juego");
					nombre = sc.nextLine();
					indicarHoras();
					indicarMinutos();
					tiempoJugado = LocalTime.of(hora, min);
					game = new Games(nombre, tiempoJugado);
					FuncionesGames.crearGames(game);
					break;

				// Si el 3 crearemos la compra
				case 3:
					System.out.println("Indique el id del jugador");
					idPlayer = sc.nextInt();
					sc.nextLine();
					System.out.println("Indique el id del juego");
					idGames = sc.nextInt();
					sc.nextLine();
					System.out.println("Indique el producto que ha comprado el jugador");
					cosa = sc.nextLine();
					System.out.println("indique el precio del producto");
					precio = sc.nextDouble();
					sc.nextLine();
					indiqueAño();
					indiqueMes();
					indiqueDia();
					fechaCompra = Date.valueOf(LocalDate.of(año, mes, dia));
					Player p = new Player();
					p.setIdPlayer(idPlayer);
					Games g = new Games();
					g.setIdGames(idGames);
					compra = new Compras(p, g, cosa, precio, fechaCompra);
					FuncionesCompra.crearCompra(compra);
					break;
				default:
					System.out.println(OPC_INVALIDA);
				}
				break;
				//Listar
			case 3:
				
				break;
			default:
				System.out.println(OPC_INVALIDA);
			}

		} while (opc != 0);

		// Imprimimos en consola que has salido del programa
		System.out.println("Cerrado.");
		// Cerramos scanner
		sc.close();

	}

	private static void indiqueDia() {
		do {
		    System.out.println("Indique el día (1 a 31):");
		    dia = sc.nextInt();
		    sc.nextLine();
		    if (dia < 1 || dia > 31) {
		        System.out.println("El día debe estar entre 1 y 31.");
		    }
		} while (dia < 1 || dia > 31);
	}

	private static void indiqueMes() {
		do {
		    System.out.println("Indique el mes (1 a 12):");
		    mes = sc.nextInt();
		    sc.nextLine();
		    if (mes < 1 || mes > 12) {
		        System.out.println("El mes debe estar entre 1 y 12.");
		    }
		} while (mes < 1 || mes > 12);
	}

	private static void indiqueAño() {
		do {
			System.out.println("Indique el año (mayor que 0):");
			año = sc.nextInt();
			sc.nextLine();
			if (año <= 0) {
				System.out.println("El año debe ser mayor que 0.");
			}
		} while (año <= 0);
	}

	private static void indicarMinutos() {
		do {
			System.out.println("Indique los minutos jugados");
			min = sc.nextInt();
			sc.nextLine();
			if (min < 0 || min > 59) {
				System.out.println("Las minutos tienen que estar en un rango de 0 a 59 pelele");
			}
		} while (min < 0 || min > 59);
	}

	private static void indicarHoras() {
		do {
			System.out.println("Indique las horas jugadas");
			hora = sc.nextInt();
			sc.nextLine();
			if (hora < 0 || hora > 23) {
				System.out.println("Las horas tienen que estar en un rango de 0 a 23 pelele");
			}
		} while (hora < 0 || hora > 23);
	}

	private static void opcionesEntidades() {
		System.out.println("1. Player");
		System.out.println("2. Games");
		System.out.println("3. Compras");
	}
}
