package funciones;

import java.util.List;
import java.util.Scanner;

import accesobd.AccesoBD;
import entidades.Games;
import entidades.Player;

public class FuncionesPlayer {

	public static AccesoBD ins = new AccesoBD();
	static Scanner sc = new Scanner(System.in);

	public static void crearPlayer(Player player) {
		try {
			ins.abrir();
			long id = (long) ins.guardar(player);
			if (id != 0) {
				System.out.println("================================");
				System.out.println("==       jugador creado      ==");
				System.out.println("================================");
			} else {
				System.out.println("======================================");
				System.out.println("==       No se ha podido crear      ==");
				System.out.println("======================================");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Listado
	public static void listaPlayers() {
		try {
			ins.abrir();
			List<Player> listadoJugadores = ins.getSesion().createNativeQuery("SELECT * FROM Player", Player.class)
					.getResultList();
			if(listadoJugadores.size()>0) {
				System.out.println("===========================");
				mostrarListadoPlayer(listadoJugadores);				
			} else {
				System.out.println("No hay jugadores en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void ListarPorId(long id) {
		Player p = null;
		try {
			ins.abrir();
			p = ins.getSesion().get(Player.class, id);
			if (p != null) {
				System.out.println("==============================");
				System.out.println("IdPlayer: " + p.getIdPlayer());
				System.out.println("Nick: " + p.getNick());
				System.out.println("Contraseña: " + p.getPassword());
				System.out.println("Email: " + p.getEmail());
				System.out.println("===========================");
			} else {
				System.out.println("No se ha encontrado el jugador con id: " + id);
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarPorNombre(String nombre) {
		List<Player> listado = null;
		try {
			ins.abrir();
			if (!nombre.equals("")) {
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Player WHERE nick like :nick ;", Player.class)
						.setParameter("nick", "%" + nombre + "%").getResultList();
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarListadoPlayer(listado);
			} else {
				System.out.println("No hay jugador con nombre " + nombre + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarPorCorreo(String correo) {
		List<Player> listado = null;
		try {
			ins.abrir();
			if (!correo.equals("")) {
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Player WHERE email like :correo ;", Player.class)
						.setParameter("correo", "%" + correo + "%").getResultList();
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				mostrarListadoPlayer(listado);
			} else {
				System.out.println("No hay jugador con email " + correo + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// -----------------------------------------------------------------------------------------------------

	// Modificar

	public static void modificarJugador(long id, String nuevoNick, String nickfiltro, String nuevaConstraseña,
			String nuevoEmail, int filtro) {
		Player p = null;
		String confirmacion = "";
		boolean comprobarJugador = comprobarJugador(id);
		List<Player> listado = null;
		try {
			ins.abrir();

			if (id != 0) {
				if(comprobarJugador) {
					p = ins.getSesion().get(Player.class, id);
					
				} else {
					System.out.println("No se pudo modificar");
					System.out.println("El id "+ id +" no existe en la bd");
				}
			} else {
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Player where nick like :nick ;", Player.class)
						.setParameter("nick", "%" + nickfiltro + "%").getResultList();
			}

			if (p != null) {
				switch (filtro) {
				case 1:
					p.setNick(nuevoNick);
					break;
				case 2:
					p.setPassword(nuevaConstraseña);
					break;
				case 3:
					p.setEmail(nuevoEmail);
					break;
				case 4:
					p.setNick(nuevoNick);
					p.setPassword(nuevaConstraseña);
					p.setEmail(nuevoEmail);
					break;
				}
				System.out.println("¿Seguro que quieres modificar este jugador?");
				System.out.println("IdPlayer: " + p.getIdPlayer());
				System.out.println("Nick: " + p.getNick());
				System.out.println("Contraseña: " + p.getPassword());
				System.out.println("Email: " + p.getEmail());
				System.out.println("===========================");
				confirmacion = confirmarTransac();

				if (confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					ins.getSesion().update(p);
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			} else if (listado.size() > 0) {
				// For para actualizar
				for (Player player : listado) {
					switch (filtro) {
					case 1:
						player.setNick(nuevoNick);
						break;
					case 2:
						player.setPassword(nuevaConstraseña);
						break;
					case 3:
						player.setEmail(nuevoEmail);
						break;
					case 4:
						player.setNick(nuevoNick);
						player.setPassword(nuevaConstraseña);
						player.setEmail(nuevoEmail);
						break;
					}
				}
				mostrarListadoPlayer(listado);
				confirmacion = confirmarTransac();
				if (confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					for (Player player : listado) {
						ins.getSesion().update(player);
					}
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			} else {
				System.out.println("No se pudo modificar");
				System.out.println("No existe jugadores con el nombre o letras "+nickfiltro);
			}

			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static String confirmarTransac() {
		String confirmacion;
		System.out.println("¿Quieres confirmar la transaccion? Indique S(Si) o N(No)");
		confirmacion = sc.nextLine().toLowerCase();

		while (!confirmacion.equals("s") && !confirmacion.equals("n")) {
			System.out.println("Indique S o N, no es tan conplicado");
			confirmacion = sc.nextLine();
		}
		return confirmacion;
	}

	// ----------------------------------------------------------------------------------------------------
	// Eliminar

	public static void eliminarPlayer(long id, String nombreFiltro, int opc) {
		Player p = null;
		List<Player> listado = null;
		List<Player> listadoPlayer = obtenerListadoPlayer();
		String confirmacion = "";
		boolean confirmarJugador = comprobarJugador(id);
		try {
			ins.abrir();
			switch (opc) {
			case 1:
				if (confirmarJugador) {
					p = ins.getSesion().get(Player.class, id);
					System.out.println("IdPlayer: " + p.getIdPlayer());
					System.out.println("Nick: " + p.getNick());
					System.out.println("Contraseña: " + p.getPassword());
					System.out.println("Email: " + p.getEmail());
					System.out.println("================================");
					System.out.println("AVISO: Si eliminas a este jugador se eliminara de la tabla Compras");
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						ins.getSesion().delete(p);
						System.out.println("Jugador eliminado");
					} else {
						System.out.println("Transaccion cancelada");
						ins.getTransaction().rollback();
					}
				} else {
					System.out.println("No hay jugador con id: " + id + " en la BD");
				}
				break;
			case 2:
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Player where nick like :nick ;", Player.class)
						.setParameter("nick", "%" + nombreFiltro + "%").getResultList();
				if (listado.size() > 0) {
					mostrarListadoPlayer(listado);
					System.out.println("AVISO: Si eliminas a estos jugadores se eliminaran de la tabla Compras");
					confirmacion = confirmarTransac();
					if (confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						for (Player player : listado) {
							ins.getSesion().delete(player);
						}
						System.out.println("Jugadores eliminados");
					} else {
						System.out.println("Transaccion candelada");
						ins.getTransaction().rollback();
					}
				} else {
					System.out.println("No se han encontrado jugador con el nombre o letras: " + nombreFiltro);
				}
				break;
			case 3:
				if(listadoPlayer.size() > 0) {
					mostrarListadoPlayer(listadoPlayer);
					confirmacion = confirmarTransac();
					if(confirmacion.equals("s")) {
						System.out.println("Transaccion confirmada");
						for (Player player : listadoPlayer) {
							ins.getSesion().delete(player);
						}
						System.out.println("Jugadores eliminados");
					} else {
						System.out.println("Transaccion cancelada");
					}
				}
				break;
			}

			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void mostrarListadoPlayer(List<Player> listado) {
		for (Player player : listado) {
			System.out.println("IdPlayer: " + player.getIdPlayer());
			System.out.println("Nick: " + player.getNick());
			System.out.println("Contraseña: " + player.getPassword());
			System.out.println("Email: " + player.getEmail());
			System.out.println("===========================");
		}
	}
	public static List<Player> obtenerListadoPlayer() {
		List<Player> listadoPlayer = null;
		try {
			ins.abrir();
			listadoPlayer = ins.getSesion().createNativeQuery("SELECT * FROM Player", Player.class)
					.getResultList();
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listadoPlayer;
	}
	// ----------------------------------------------------------------------------------------------------
	public static boolean comprobarJugador(long id) {
		boolean existe = false;
		try {
			ins.abrir();
			Player p = null;
			p = ins.getSesion().get(Player.class, id);

			if (p != null) {
				existe = true;
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return existe;
	}

	public static Player obtenerJugadorId(long id) {
		Player player = null;
		try {
			ins.abrir();
			player = ins.getSesion().get(Player.class, id);
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return player;
	}

	public static void eliminarPlayer(long id) {
		Player p = obtenerJugadorId(id);
		try {
			ins.abrir();
			ins.getSesion().delete(p);
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
