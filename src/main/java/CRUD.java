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
		long playerFiltroId = 0;
		long gameFiltroId = 0;
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
					fechaCompra = crearFecha();
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
						switch (opc) {
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
							fechaCompra = crearFecha();
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
			// Modificar
			case 4:
				System.out.println("¿Que tabla quieres modificar?");
				opcionesEntidades();
				opc = sc.nextInt();
				sc.nextLine();
				switch (opc) {
				// Modificar jugadores
				case 1:
					System.out.println("¿Indica el filtro?");
					System.out.println("1. Por Id");
					System.out.println("2. Por Nick");
					opc = sc.nextInt();
					sc.nextLine();
					FuncionesPlayer.listaPlayers();
					// Filtro elegido
					switch (opc) {
					case 1:
						System.out.println("Indica el id de la persona a modificar");
						idPlayer = sc.nextLong();
						sc.nextLine();
						atributosAModificarPlayer();
						opc = sc.nextInt();
						sc.nextLine();
						// Switch para modificar atributos player
						switch (opc) {
						case 1:
							System.out.println("Indica el nick nuevo");
							nick = sc.nextLine();
							FuncionesPlayer.modificarJugador(idPlayer, nick, null, null, null, opc);
							break;
						case 2:
							System.out.println("Indica la contraseña nueva");
							password = sc.nextLine();
							FuncionesPlayer.modificarJugador(idPlayer, null, null, password, null, opc);
							break;
						case 3:
							System.out.println("Indique el correo nuevo");
							email = sc.nextLine();
							FuncionesPlayer.modificarJugador(idPlayer, null, null, null, email, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					case 2:
						System.out.println("Indique el nombre o letra a modificar");
						filtro = sc.nextLine();
						atributosAModificarPlayer();
						opc = sc.nextInt();
						sc.nextLine();
						// Switch para modificar atributos player
						switch (opc) {
						case 1:
							System.out.println("Indica el nick nuevo");
							nick = sc.nextLine();
							FuncionesPlayer.modificarJugador(0, nick, filtro, null, null, opc);
							break;
						case 2:
							System.out.println("Indica la contraseña nueva");
							password = sc.nextLine();
							FuncionesPlayer.modificarJugador(0, null, filtro, password, null, opc);
							break;
						case 3:
							System.out.println("Indique el correo nuevo");
							email = sc.nextLine();
							FuncionesPlayer.modificarJugador(0, null, filtro, null, email, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					default:
						System.out.println(OPC_INVALIDA);
					}
					break;
				// Modificar juegos
				case 2:
					System.out.println("Indica el filtro");
					System.out.println("1. Id");
					System.out.println("2. Nombre");
					opc = sc.nextInt();
					sc.nextLine();
					FuncionesGames.listaGames();
					// Filtro elegido
					switch (opc) {
					// Elegir id(filtro)
					case 1:
						System.out.println("Indica el id a modificar");
						idGames = sc.nextLong();
						sc.nextLine();
						atributosAModificarGames();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						// Modificar nombre
						case 1:
							System.out.println("Indique el nombre nuevo");
							nombre = sc.nextLine();
							FuncionesGames.ModificarJuego(idGames, nombre, null, null, opc);
							break;
						// Modificar compra
						case 2:
							System.out.println("Indique el tiempo nuevo");
							indicarHoras();
							indicarMinutos();
							tiempoJugado = LocalTime.of(hora, min);
							FuncionesGames.ModificarJuego(idGames, null, null, tiempoJugado, opc);
							break;

						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					// Elegir nombre o letra(filtro)
					case 2:
						System.out.println("Indique el nombre del juego a modificar");
						filtro = sc.nextLine();
						atributosAModificarGames();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							System.out.println("Indique el nombre nuevo");
							nombre = sc.nextLine();
							FuncionesGames.ModificarJuego(0, nombre, filtro, null, opc);
							break;
						case 2:
							System.out.println("Indique el tiempo nuevo");
							indicarHoras();
							indicarMinutos();
							tiempoJugado = LocalTime.of(hora, min);
							FuncionesGames.ModificarJuego(0, null, filtro, tiempoJugado, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					default:
						System.out.println(OPC_INVALIDA);
					}
					break;
				// Modificar compras
				case 3:
					System.out.println("Indica el filtro");
					System.out.println("1. Id");
					System.out.println("2. Id Juego");
					System.out.println("3. Id Games");
					System.out.println("4. Cosa");
					opc = sc.nextInt();
					sc.nextLine();
					FuncionesCompra.listaCompras();
					switch (opc) {
					// Modificar por id
					case 1:
						System.out.println("indica el id a modificar");
						idCompra = sc.nextLong();
						sc.nextLine();
						atributosAModificarCompra();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							System.out.println("Indique el id del jugador nuevo");
							idPlayer = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(idCompra, idPlayer, 0, null, 0, null, 0, 0, null, opc);
							break;
						case 2:
							System.out.println("Indique el id del juego nuevo");
							idGames = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(idCompra, 0, idGames, null, 0, null, 0, 0, null, opc);
							break;
						case 3:
							System.out.println("Indica la cosa nueva");
							cosa = sc.nextLine();
							FuncionesCompra.modificarCompra(idCompra, 0, 0, cosa, 0, null, 0, 0, null, opc);
							break;
						case 4:
							System.out.println("Indica el precio nuevo");
							precio = sc.nextDouble();
							sc.nextLine();
							FuncionesCompra.modificarCompra(idCompra, 0, 0, null, precio, null, 0, 0, null, opc);
							break;
						case 5:
							System.out.println("Indica la fecha nueva:");
							indiqueAño();
							indiqueMes();
							indiqueDia();
							fechaCompra = crearFecha();
							FuncionesCompra.modificarCompra(idCompra, opc, opc, OPC_INVALIDA, opc, fechaCompra, 0, 0,
									null, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					// Modificar por juego
					case 2:
						System.out.println("Indique el id del jugador a modificar");
						playerFiltroId = sc.nextLong();
						sc.nextLine();
						atributosAModificarCompra();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							System.out.println("Indique el id del jugador nuevo");
							idPlayer = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, idPlayer, 0, null, 0, null, playerFiltroId, 0, null,
									opc);
							break;
						case 2:
							System.out.println("Indique el id del juego nuevo");
							idGames = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, idGames, null, 0, null, playerFiltroId, 0, null, opc);
							break;
						case 3:
							System.out.println("Indica la cosa nueva");
							cosa = sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, 0, cosa, 0, null, playerFiltroId, 0, null, opc);
							break;
						case 4:
							System.out.println("Indica el precio nuevo");
							precio = sc.nextDouble();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, 0, null, precio, null, playerFiltroId, 0, null, opc);
							break;
						case 5:
							System.out.println("Indica la fecha nueva:");
							indiqueAño();
							indiqueMes();
							indiqueDia();
							fechaCompra = crearFecha();
							FuncionesCompra.modificarCompra(0, opc, opc, OPC_INVALIDA, opc, fechaCompra, playerFiltroId,
									0, null, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					// Modificar por juego
					case 3:
						System.out.println("Indique el id del juego a modificar");
						gameFiltroId = sc.nextLong();
						sc.nextLine();
						atributosAModificarCompra();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							System.out.println("Indique el id del jugador nuevo");
							idPlayer = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, idPlayer, 0, null, 0, null, 0, gameFiltroId, null, opc);
							break;
						case 2:
							System.out.println("Indique el id del juego nuevo");
							idGames = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, idGames, null, 0, null, 0, gameFiltroId, null, opc);
							break;
						case 3:
							System.out.println("Indica la cosa nueva");
							cosa = sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, 0, cosa, 0, null, 0, gameFiltroId, null, opc);
							break;
						case 4:
							System.out.println("Indica el precio nuevo");
							precio = sc.nextDouble();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, 0, null, precio, null, 0, gameFiltroId, null, opc);
							break;
						case 5:
							System.out.println("Indica la fecha nueva:");
							indiqueAño();
							indiqueMes();
							indiqueDia();
							fechaCompra = crearFecha();
							FuncionesCompra.modificarCompra(0, opc, opc, OPC_INVALIDA, opc, fechaCompra, 0,
									gameFiltroId, null, opc);
							break;
						default:
							System.out.println(OPC_INVALIDA);
						}
						break;
					// Modificar por cosa
					case 4:
						System.out.println("Indique el objeto de compra de los jugadores a modificar");
						filtro = sc.nextLine();
						atributosAModificarCompra();
						opc = sc.nextInt();
						sc.nextLine();
						switch (opc) {
						case 1:
							System.out.println("Indique el id del jugador nuevo");
							idPlayer = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, idPlayer, 0, null, 0, null, 0, 0, filtro, opc);
							break;
						case 2:
							System.out.println("Indique el id del juego nuevo");
							idGames = sc.nextLong();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, idGames, null, 0, null, 0, 0, filtro, opc);
							break;
						case 3:
							System.out.println("Indica la cosa nueva");
							cosa = sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, 0, cosa, 0, null, 0, 0, filtro, opc);
							break;
						case 4:
							System.out.println("Indica el precio nuevo");
							precio = sc.nextDouble();
							sc.nextLine();
							FuncionesCompra.modificarCompra(0, 0, 0, null, precio, null, 0, 0, filtro, opc);
							break;
						case 5:
							System.out.println("Indica la fecha nueva:");
							indiqueAño();
							indiqueMes();
							indiqueDia();
							fechaCompra = crearFecha();
							FuncionesCompra.modificarCompra(0, opc, opc, OPC_INVALIDA, opc, fechaCompra, 0, 0, filtro,
									opc);
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
			// Eliminar
			case 5:
				System.out.println("¿De que elemento de la tabla quieres borrar?");
				opcionesEntidades();
				opc = sc.nextInt();
				sc.nextLine();
				switch(opc) {
				//Eliminar en la tabla Player
				case 1:
					System.out.println("Indique el filtro a borrar");
					System.out.println("1. Id");
					System.out.println("2. Nombre");
					opc = sc.nextInt();
					sc.nextLine();
					//Borrar por Id
					switch(opc) {
					case 1:
						FuncionesPlayer.listaPlayers();
						System.out.println("Indique el id del jugador a eliminar");
						idPlayer = sc.nextLong();
						sc.nextLine();
						FuncionesPlayer.eliminarPlayer(idPlayer, filtro);
						break;
					case 2:
						System.out.println("Indique el nombre o letra del jugador/es a eliminar");
						filtro = sc.nextLine();
						FuncionesPlayer.eliminarPlayer(0, filtro);
						break;
					default:
						System.out.println(OPC_INVALIDA);
					}
					break;
				//Eliminar en la tabla Games
				case 2:
					System.out.println("Indique el filtro a borrar");
					System.out.println("1. Id");
					System.out.println("2. Nombre");
					System.out.println("3. Tiempo Jugado");
					opc = sc.nextInt();
					sc.nextLine();
					FuncionesGames.listaGames();
					switch(opc) {
					case 1:
						System.out.println("Indica el id del juego a borrar");
						idGames = sc.nextLong();
						sc.nextLine();
						FuncionesGames.eliminarGame(idGames, nombre, tiempoJugado, opc);
						break;
					case 2:
						System.out.println("Indica el nombre del juego/s a borrar");
						nombre = sc.nextLine();
						FuncionesGames.eliminarGame(idGames, nombre, tiempoJugado, opc);
						break;
					case 3:
						System.out.println("Indica el tiempo del juego/s a borrar");
						indicarHoras();
						indicarMinutos();
						tiempoJugado = LocalTime.of(hora, min);
						FuncionesGames.eliminarGame(idGames, nombre, tiempoJugado, opc);
						break;
					default:
						System.out.println(OPC_INVALIDA);
					}
					break;
				//eliminar en la tabla Compras
				case 3:
					break;
				default:
					System.out.println(OPC_INVALIDA);
				}
				break;
			case 0:
				System.out.println("Saliendo del programa...");
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

	private static void atributosAModificarCompra() {
		System.out.println("¿Que quieres modificar?");
		System.out.println("1. Id Player");
		System.out.println("2. Id Games");
		System.out.println("3. Cosa");
		System.out.println("4. Precio");
		System.out.println("5. Fecha de la compra");
	}

	private static Date crearFecha() {
		return Date.valueOf(LocalDate.of(año, mes, dia));
	}

	private static void atributosAModificarGames() {
		System.out.println("¿Que quieres modificar?");
		System.out.println("1. Nombre");
		System.out.println("2. Tiempo jugado");
	}

	private static void atributosAModificarPlayer() {
		System.out.println("¿Que quieres modificar?");
		System.out.println("1. Nick");
		System.out.println("2. Contraseña");
		System.out.println("3. Correo");
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
