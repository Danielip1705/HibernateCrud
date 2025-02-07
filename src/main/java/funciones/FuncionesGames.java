package funciones;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import accesobd.AccesoBD;
import entidades.Games;

public class FuncionesGames {

	public static AccesoBD ins = new AccesoBD();
	static Scanner sc = new Scanner(System.in);
	public static void crearGames(Games game) {
		try {
			ins.abrir();
			long id = (long) ins.guardar(game);
			if (id != 0) {
				System.out.println("=============================");
				System.out.println("==       Juego creado      ==");
				System.out.println("=============================");
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
	public static void listaGames() {
		try {
			ins.abrir();
			List<Games> listadoGames = ins.getSesion().createNativeQuery("SELECT * FROM Games", Games.class)
					.getResultList();
			System.out.println("===========================");
			for (Games g : listadoGames) {
				System.out.println("IdGames: " + g.getIdGames());
				System.out.println("Nombre: " + g.getNombre());
				System.out.println("Tiempo Jugado: " + g.getTiempoJugado().toString());
				System.out.println("===========================");
			}

			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void ListarPorId(long id) {
		Games g = null;
		try {
			ins.abrir();
			g = ins.getSesion().load(Games.class, id);
			if (g != null) {
				System.out.println("==============================");
				System.out.println("ID: " + g.getIdGames());
				System.out.println("Nombre: " + g.getNombre());
				System.out.println("Tiempo Jugado: " + g.getTiempoJugado().toString());
				System.out.println("===========================");
			} else {
				System.out.println("No se ha encontrado el juego");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarPorNombre(String nombre) {
		List<Games> listado = null;
		try {
			ins.abrir();
			if (!nombre.equals("")) {
				listado = ins.getSesion()
						.createNativeQuery("SELECT * FROM Games WHERE nombre like :nombre ;", Games.class)
						.setParameter("nombre", "%" + nombre + "%").getResultList();
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Games g : listado) {
					System.out.println("ID: " + g.getIdGames());
					System.out.println("Nombre: " + g.getNombre());
					System.out.println("Tiempo Jugado: " + g.getTiempoJugado().toString());
					System.out.println("===========================");
				}
			} else {
				System.out.println("No hay juego con nombre " + nombre + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void listarPorTiempo(LocalTime tiempo, String filtro) {
		List<Games> listado = null;
		try {
			ins.abrir();

			listado = ins.getSesion()
					.createNativeQuery("SELECT * FROM Games WHERE tiempoJugado "+filtro+" :tiempo ;", Games.class)
					.setParameter("tiempo", tiempo).getResultList();

			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Games g : listado) {
					System.out.println("ID: " + g.getIdGames());
					System.out.println("Nombre: " + g.getNombre());
					System.out.println("Tiempo Jugado: " + g.getTiempoJugado().toString());
					System.out.println("===========================");
				}
			} else {
				System.out.println("No hay juego con tiempo jugado de " + tiempo.toString() + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// -------------------------------------------------------------------------------------------
	//Modificar Juego
	public static void ModificarJuego(long id,String nombreNuevo,String nombreFiltro,LocalTime tiempoNuevo,int filtro) {
		Games g = null;
		String confirmacion ="";
		List<Games> listado = null;
		try {
			if(id!=0) {
				g = ins.getSesion().get(Games.class, id);
			} else {
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Games where nombre like :nombre ;",Games.class)
						.setParameter("nombre", "%"+nombreFiltro+"%").getResultList();
			}
			if(g!=null) {
				switch (filtro) {
				case 1:
					g.setNombre(nombreFiltro);
					break;
				case 2:
					g.setTiempoJugado(tiempoNuevo);
					break;
				}
				System.out.println("ID: " + g.getIdGames());
				System.out.println("Nombre: " + g.getNombre());
				System.out.println("Tiempo Jugado: " + g.getTiempoJugado().toString());
				confirmacion = confirmarTransac();
				
				if(confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					ins.getSesion().merge(g);
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			} else {
				for (Games games : listado) {
					switch (filtro) {
					case 1:
						games.setNombre(nombreFiltro);
						break;
					case 2:
						games.setTiempoJugado(tiempoNuevo);
						break;
					}
				}
				for (Games games : listado) {
					System.out.println("ID: " + games.getIdGames());
					System.out.println("Nombre: " + games.getNombre());
					System.out.println("Tiempo Jugado: " + games.getTiempoJugado().toString());
				}
				confirmacion = confirmarTransac();
				if(confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					for (Games games : listado) {
						ins.getSesion().update(games);
					}
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			}
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
	
	//--------------------------------------------------------------------------------------------
	public static Games buscarGamesId(long id) {
		Games games = null;
		try {
			ins.abrir();
			games = ins.getSesion().load(Games.class, id);
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return games;
	}
}
