package funciones;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.exception.ConstraintViolationException;

import accesobd.AccesoBD;
import entidades.Compras;
import entidades.Games;
import entidades.Player;

public class FuncionesCompra {

	public static AccesoBD ins = new AccesoBD();
	static Scanner sc = new Scanner(System.in);

	public static void crearCompra(Compras compra) {
		try {
			ins.abrir();
			long id = (long) ins.guardar(compra);
			if (id != 0) {
				System.out.println("==============================");
				System.out.println("==       Compra creada      ==");
				System.out.println("==============================");
			} else {
				System.out.println("======================================");
				System.out.println("==       No se ha podido crear      ==");
				System.out.println("======================================");
			}
			ins.cerrar();
		} catch (ConstraintViolationException e) {
			System.out.println("La compra contiene un id de juego o jugador que no existe");
		} catch (IllegalStateException e) {
			System.out.println("Tiempo de espera finalizado: No se ha podido conectar a la BD");
		} catch (Exception e) {
			System.out.println("ERROR");
		}

	}

//Listado
	public static void listaCompras() {
		try {
			ins.abrir();
			List<Compras> listadoCompras = ins.getSesion().createNativeQuery("SELECT * FROM Compras", Compras.class)
					.getResultList();
			if (listadoCompras.size() > 0) {
				System.out.println("===========================");
				mostrarCompra(listadoCompras);
			} else {
				System.out.println("No existe compras en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void listarIdCompra(long id) {
		Compras c = null;
		try {
			ins.abrir();
			c = ins.getSesion().get(Compras.class, id);
			if (c != null) {
				System.out.println("==============================");
				System.out.println("IdCompras: " + c.getIdCompras());
				System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
				System.out.println("IdGames: " + c.getGame().getIdGames());
				System.out.println("Cosa : " + c.getCosa());
				System.out.println("Precio: " + c.getPrecio());
				System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
				System.out.println("===========================");
			} else {
				System.out.println("No se ha encontrado la compra con id: " + id);
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarIdPlayer(long id) {
		List<Compras> listado = null;
		try {
			ins.abrir();

			listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE id_player = :id ;", Compras.class)
					.setParameter("id", id).getResultList();

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarCompra(listado);
			} else {
				System.out.println("No hay compra con id de jugador " + id + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarIdGames(long id) {
		List<Compras> listado = null;
		try {
			ins.abrir();

			listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE id_games = :id ;", Compras.class)
					.setParameter("id", id).getResultList();

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarCompra(listado);
			} else {
				System.out.println("No hay compra con id de juego " + id + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarObjeto(String objeto) {
		List<Compras> listado = null;
		try {
			ins.abrir();
			if (!objeto.equals("")) {
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE cosa like :cosa ;", Compras.class)
						.setParameter("cosa", "%" + objeto + "%").getResultList();
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarCompra(listado);
			} else {
				System.out.println("No hay compra con nombre de objeto: " + objeto + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarPrecio(double precio, String filtro) {
		List<Compras> listado = null;
		try {
			ins.abrir();
			switch (filtro) {
			case "<":
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE precio < :precio ;", Compras.class)
						.setParameter("precio", precio).getResultList();
				break;

			case ">":
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE precio > :precio ;", Compras.class)
						.setParameter("precio", precio).getResultList();
				break;

			case "=":
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE precio = :precio ;", Compras.class)
						.setParameter("precio", precio).getResultList();
				break;
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarCompra(listado);
			} else {
				System.out.println("No hay compra que tenga un precio de " + precio + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarFecha(int año, int mes, int dia, Date fechaCompleta, int filtro) {
		List<Compras> listado = null;
		try {
			ins.abrir();

			switch (filtro) {
			case 1:
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE year(fechaCompra) = :año ;", Compras.class)
						.setParameter("año", año).getResultList();
				break;
			case 2:
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE month(fechaCompra) = :mes ;", Compras.class)
						.setParameter("mes", mes).getResultList();
				break;
			case 3:
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE day(fechaCompra) = :dia ;", Compras.class)
						.setParameter("dia", dia).getResultList();
				break;
			case 4:
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Compras WHERE fechaCompra = :fecha ;", Compras.class)
						.setParameter("fecha", fechaCompleta).getResultList();
				break;
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarCompra(listado);
			} else {
				if (fechaCompleta == null) {
					if (año != 0) {
						System.out.println("No hay compras que tenga fecha que empiece con el año " + año);
					} else if (mes != 0) {
						System.out.println("No hay compras que tenga fecha que empiece con el mes " + mes);
					} else if (dia != 0) {
						System.out.println("No hay compras que tenga fecha que empiece con el dia " + dia);
					}
				} else {
					System.out.println("No hay comprar que tenga la fecha " + fechaCompleta.toString());
				}
			}

			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static List<Compras> obtenerListadoCompras() {
		List<Compras> listadoCompras = null;
		try {
			ins.abrir();
			listadoCompras = ins.getSesion().createNativeQuery("SELECT * FROM Compras", Compras.class).getResultList();

			ins.cerrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listadoCompras;
	}

	// ---------------------------------------------------------------------------------------------------------

	// Modificar

	public static void modificarCompra(long id, long idPlayer, long idGames, String cosa, double precio,
			Date fechaCompra, long idPlayerFiltro, long idGamesFiltro, String cosaFiltro, int opc) {
		Compras c = null;
		Player p = null;
		Games g = null;
		String confirmacion = "";
		boolean seModifica = false;
		boolean confirmarCompra = comprobarCompra(id);
		boolean confirmarJugador = FuncionesPlayer.comprobarJugador(idPlayerFiltro);
		boolean confirmarGames = FuncionesGames.comprobarGame(idGamesFiltro);
		boolean confirmarJugadorNuevo = FuncionesPlayer.comprobarJugador(idPlayer);
		boolean confirmarGamesNuevo = FuncionesGames.comprobarGame(idGames);
		List<Compras> listado = null;
		try {
			ins.abrir();
			if (id != 0) {
				if (confirmarCompra) {
					c = ins.getSesion().get(Compras.class, id);
				} else {
					System.out.println("No existe id " + id + " en la BD");
				}
			} else if (idPlayerFiltro != 0) {
				if (confirmarJugador) {
					listado = ins.getSesion()
							.createNativeQuery("select * from Compras where id_player = :id ;", Compras.class)
							.setParameter("id", idPlayerFiltro).getResultList();
				} else {
					System.out.println("No existe el id del jugador " + idPlayerFiltro + " en la BD");
				}
			} else if (idGamesFiltro != 0) {
				if (confirmarGames) {
					listado = ins.getSesion()
							.createNativeQuery("select * from Compras where id_games = :id ;", Compras.class)
							.setParameter("id", idGamesFiltro).getResultList();
				} else {
					System.out.println("No existe el id del juego " + idGamesFiltro + " en la BD");
				}
			} else {
				listado = ins.getSesion().createNativeQuery("select * from Compras where cosa = :cosa ;", Compras.class)
						.setParameter("cosa", cosaFiltro).getResultList();
			}
			if (c != null) {
				switch (opc) {
				case 1:
					p = FuncionesPlayer.obtenerJugadorId(idPlayer);
					c.setPlayer(p);

					break;
				case 2:
					g = FuncionesGames.buscarGamesId(idGames);
					c.setGame(g);
					break;
				case 3:
					c.setCosa(cosa);
					break;
				case 4:
					c.setPrecio(precio);

					break;
				case 5:
					c.setFechaCompra(fechaCompra);

					break;
				case 6:
					p = FuncionesPlayer.obtenerJugadorId(idPlayer);
					c.setPlayer(p);
					g = FuncionesGames.buscarGamesId(idGames);
					c.setGame(g);
					c.setCosa(cosa);
					c.setPrecio(precio);
					c.setFechaCompra(fechaCompra);
					break;
				}
				if (seModifica) {
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						ins.getSesion().update(c);
						System.out.println("Compra modificada");
						System.out.println();
					} else {
						System.out.println("Transaccion cancelada");
						ins.getTransaction().rollback();
					}
				}
			} else if (listado.size() > 0) {
				for (Compras com : listado) {
					switch (opc) {
					case 1:
						if (confirmarJugadorNuevo) {
							p = FuncionesPlayer.obtenerJugadorId(idPlayer);
							com.setPlayer(p);

						} else {
							System.out.println("El id del jugador: " + idPlayer + " no existe en la BD");
						}
						break;
					case 2:
						if (confirmarGamesNuevo) {
							g = FuncionesGames.buscarGamesId(idGames);
							com.setGame(g);

						} else {
							System.out.println("El id del juego: " + idGames + " no existe en la BD");
						}
						break;
					case 3:
						com.setCosa(cosa);

						break;
					case 4:
						com.setPrecio(precio);

						break;
					case 5:
						com.setFechaCompra(fechaCompra);

						break;
					case 6:
						if (confirmarJugadorNuevo) {
							p = FuncionesPlayer.obtenerJugadorId(idPlayer);
							com.setPlayer(p);
						} else {
							System.out.println("El id del jugador: " + idPlayer + " no existe en la BD");
							break;
						}
						if (confirmarGamesNuevo) {
							g = FuncionesGames.buscarGamesId(idGames);
							com.setGame(g);
						} else {
							System.out.println("El id del juego: " + idGames + " no existe en la BD");
							break;
						}
						com.setCosa(cosa);
						com.setPrecio(precio);
						com.setFechaCompra(fechaCompra);
						break;
					}
				}

				mostrarCompra(listado);
				confirmacion = confirmarTransac();
				if (confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					for (Compras com : listado) {
						ins.getSesion().update(com);
					}
					System.out.println("Compra modificada");
				} else {
					System.out.println("Transaccion cancelada");
				}

			} else {
				System.out.println("No existe cosa con el nombre o letra " + cosaFiltro + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static String confirmarTransac() {
		String confirmacion;
		System.out.println("¿Quieres confirmar la transaccion? Indique S(Si) o N(No)");
		confirmacion = sc.nextLine().toLowerCase();

		while (!confirmacion.equals("s") && !confirmacion.equals("n")) {
			System.out.println("Indique S o N, no es tan conplicado");
			confirmacion = sc.nextLine();
		}
		return confirmacion;
	}
	// ----------------------------------------------------------------------------------------------------------
	// Eliminar

	public static void eliminarCompras(long idCompra, long idPlayer, long idGames, int opc) {
		Compras c = null;
		List<Compras> listado = null;
		List<Compras> listadoCompras = obtenerListadoCompras();
		String confirmacion = "";
		boolean confirmarCompra = comprobarCompra(idCompra);
		boolean confirmarJugador = FuncionesPlayer.comprobarJugador(idPlayer);
		boolean confirmarGames = FuncionesGames.comprobarGame(idGames);
		try {
			ins.abrir();
			switch (opc) {
			case 1:
				if (confirmarCompra) {
					c = ins.getSesion().get(Compras.class, idCompra);
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						ins.getSesion().delete(c);
						System.out.println("Compra eliminada");
					}
				} else {
					System.out.println("El id " + idCompra + " no existe en la base de datos");
				}
				break;
			case 2:
				if (confirmarJugador) {
					listado = ins.getSesion()
							.createNativeQuery("SELECT * FROM Compras WHERE id_player = :id_player ;", Compras.class)
							.setParameter("id_player", idPlayer).getResultList();
					mostrarCompra(listado);
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						for (Compras com : listado) {
							ins.getSesion().delete(com);
						}
						System.out.println("Compra/s eliminada/s");
					}
				} else {
					System.out.println("El id " + idPlayer + " no existe en la BD");
				}
				break;
			case 3:
				if (confirmarGames) {
					listado = ins.getSesion()
							.createNativeQuery("SELECT * FROM Compras WHERE id_games = :id_games ;", Compras.class)
							.setParameter("id_games", idGames).getResultList();
					mostrarCompra(listado);
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						for (Compras com : listado) {
							ins.getSesion().delete(com);
						}
						System.out.println("Compra/s eliminada/s");
					}
				} else {
					System.out.println("El id " + idGames + " no existe en la BD");
				}
				break;
			case 4:
				if (listadoCompras.size() > 0) {
					mostrarCompra(listadoCompras);
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						for (Compras com : listadoCompras) {
							ins.getSesion().delete(com);
						}
						System.out.println("Compra/s eliminada/s");
					}
				}
				break;
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void mostrarCompra(List<Compras> listadoCompras) {
		for (Compras com : listadoCompras) {
			System.out.println("IdCompras: " + com.getIdCompras());
			System.out.println("IdPlayer: " + com.getPlayer().getIdPlayer());
			System.out.println("IdGames: " + com.getGame().getIdGames());
			System.out.println("Cosa : " + com.getCosa());
			System.out.println("Precio: " + com.getPrecio());
			System.out.println("Fecha Registro: " + com.getFechaCompra().toString());
			System.out.println("===========================");
		}
	}

	// ----------------------------------------------------------------------------------------------------------

	public static boolean comprobarCompra(long id) {
		boolean existe = false;
		try {
			ins.abrir();
			Compras p = null;
			p = ins.getSesion().get(Compras.class, id);

			if (p != null) {
				existe = true;
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return existe;
	}
	public static void eliminarPlayer(long id) {
		Compras c = null;
		try {
			ins.abrir();
			c = ins.getSesion().get(Compras.class, id);
			ins.getSesion().delete(c);
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
