package funciones;

import java.time.LocalTime;
import java.util.List;

import accesobd.AccesoBD;
import entidades.Games;
import entidades.Games;

public class FuncionesGames {

	public static AccesoBD ins = new AccesoBD();

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
