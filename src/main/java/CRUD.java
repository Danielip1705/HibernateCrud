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
	static long idPlayer;
	static String nick;
	static String password;
	static String email;
	// Atributos Games
	static Games game = null;
	static long idGames;
	static String nombre;
	static int hora;
	static int min;
	static LocalTime tiempoJugado;
	// Atributos Compras
	static Compras compra = null;
	static long idCompra;
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
		String filtro = "";
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
					player = FuncionesPlayer.obtenerJugadorId(idPlayer);
					System.out.println("Indique el id del juego");
					idGames = sc.nextInt();
					sc.nextLine();
					game = FuncionesGames.buscarGamesId(idGames);
					System.out.println("Indique el producto que ha comprado el jugador");
					cosa = sc.nextLine();
					System.out.println("indique el precio del producto");
					precio = sc.nextDouble();
					sc.nextLine();
					indiqueAño();
					indiqueMes();
					indiqueDia();
					fechaCompra = Date.valueOf(LocalDate.of(año, mes, dia));
					if (player == null) {
						System.out.println("No se pudo realizar la creacion, el id del jugador no existe");
					} else if (game == null) {
						System.out.println("No se pudo realizar la creacion, el id del juego no existe");
					} else {
						compra = new Compras(player, game, cosa, precio, fechaCompra);
					}
					FuncionesCompra.crearCompra(compra);
					break;
				default:
					System.out.println(OPC_INVALIDA);
				}
				break;
			// Listar
			case 3:
				System.out.println("¿Que tabla quieres listar?");
				opcionesEntidades();
				opc = sc.nextInt();
				sc.nextLine();
				// Switch segun la entidad seleccionada
				switch (opc) {
				case 1:
					System.out.println("¿Como quieres listarlo?");
					System.out.println("1. Todos");
					System.out.println("2. Id");
					System.out.println("3. Nick");
					System.out.println("4. Correo");
					opc = sc.nextInt();
					sc.nextLine();
					switch (opc) {
					case 1:
						FuncionesPlayer.listaPlayers();
						break;
					case 2:
						System.out.println("Indique el id a buscar");
						idPlayer = sc.nextLong();
						FuncionesPlayer.ListarPorId(idPlayer);
						break;
					case 3:
						System.out.println("Indica el nombre a buscar");
						nick = sc.nextLine();
						FuncionesPlayer.listarPorNombre(nick);
						break;
					case 4:
						System.out.println("Indica el email a buscar");
						email = sc.nextLine();
						FuncionesPlayer.listarPorCorreo(email);
						break;
					default:
						System.out.println(OPC_INVALIDA);

					}
					break;

				case 2:
					System.out.println("¿Como quieres listarlo?");
					System.out.println("1. Todos");
					System.out.println("2. Id");
					System.out.println("3. Nombre");
					System.out.println("4. Tiempo Jugado");
					opc = sc.nextInt();
					sc.nextLine();
					switch (opc) {
					case 1:
						FuncionesGames.listaGames();
						break;
					case 2:
						System.out.println("Indique el id a buscar");
						idGames = sc.nextLong();
						sc.nextLine();
						FuncionesGames.ListarPorId(idGames);
						break;
					case 3:
						System.out.println("Indique el nombre a buscar");
						nombre = sc.nextLine();
						FuncionesGames.listarPorNombre(nombre);
						break;
					case 4:
						System.out.println("Indique el tiempo jugado a buscar");
						indicarHoras();
						indicarMinutos();
						tiempoJugado = LocalTime.of(hora, min);
						menuFiltro();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							filtro = "<";
							FuncionesGames.listarPorTiempo(tiempoJugado, filtro);
							break;
						case 2:
							filtro = ">";
							FuncionesGames.listarPorTiempo(tiempoJugado, filtro);
							break;
						case 3:
							filtro = "=";
							FuncionesGames.listarPorTiempo(tiempoJugado, filtro);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					default:
						System.out.println(OPC_INVALIDA);
					}
					break;

				case 3:
					System.out.println("¿Como quieres listarlo?");
					System.out.println("1. Todos");
					System.out.println("2. Id");
					System.out.println("3. Id del jugador");
					System.out.println("4. Id del juego");
					System.out.println("5. Objeto comprado");
					System.out.println("6. Precio");
					System.out.println("7. Fecha de registro");
					opc = sc.nextInt();
					sc.nextLine();
					switch (opc) {
					case 1:
						FuncionesCompra.listaCompras();
						break;
					case 2:
						System.out.println("Indique el id de la compra a buscar");
						idCompra = sc.nextLong();
						sc.nextLine();
						FuncionesCompra.listarIdCompra(idCompra);
						break;
					case 3:
						System.out.println("Indique el id del jugador a buscar");
						idPlayer = sc.nextLong();
						sc.nextLine();
						FuncionesCompra.listarIdPlayer(idPlayer);
						break;
					case 4:
						System.out.println("Indique el id del juego a buscar");
						idGames = sc.nextLong();
						sc.nextLine();
						FuncionesCompra.listarIdGames(idGames);
						break;
					case 5:
						System.out.println("Indica el objeto a buscar");
						cosa = sc.nextLine();
						FuncionesCompra.listarObjeto(cosa);
						break;
					case 6:
						System.out.println("Indica el precio a buscar");
						precio = sc.nextDouble();
						sc.nextLine();
						menuFiltro();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							filtro = "<";
							FuncionesCompra.listarPrecio(precio, filtro);
							break;
						case 2:
							filtro = ">";
							FuncionesCompra.listarPrecio(precio, filtro);
							break;
						case 3:
							filtro = "=";
							FuncionesCompra.listarPrecio(precio, filtro);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					case 7:
						System.out.println("¿Como quieres filtrar la fecha?");
						System.out.println("1. Año");
						System.out.println("2. Mes");
						System.out.println("3. Dia");
						System.out.println("4. Fecha Completa");
						opc = sc.nextInt();
						sc.nextLine();
						switch(opc) {
						case 1:
							indiqueAño();
							FuncionesCompra.listarFecha(año, 0, 0, null, opc);
							break;
						case 2:
							indiqueMes();
							FuncionesCompra.listarFecha(0, mes, 0, null, opc);
							break;
						case 3:
							indiqueDia();
							FuncionesCompra.listarFecha(0, 0, dia, null, opc);
							break;
						case 4:
							indiqueAño();
							indiqueMes();
							indiqueDia();
							fechaCompra = Date.valueOf(LocalDate.of(año, mes, dia));
							FuncionesCompra.listarFecha(0, 0, 0, fechaCompra, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;

					default:
						System.out.println(OPC_INVALIDA);
					}
					break;

				default:
					System.out.println(OPC_INVALIDA);
				}
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

	private static void menuFiltro() {
		System.out.println("¿Como lo quieres filtrar?");
		System.out.println("1. <");
		System.out.println("2. >");
		System.out.println("3. =");
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
