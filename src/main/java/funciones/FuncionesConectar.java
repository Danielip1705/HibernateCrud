package funciones;

import java.util.List;

import accesobd.AccesoBD;
import entidades.Compras;
import entidades.Games;
import entidades.Player;

public class FuncionesConectar {
	public static AccesoBD ins = new AccesoBD();

	public static void conectarse() {
		try {
			ins.abrir();
			System.out.println("La conexion esta establecida");
			System.out.println("Coneccion establecida: " + ins.getSesion());
			ins.cerrar();
		} catch (Exception e) {
			System.out.println("No se pudo establecer la conexion");
		}
	}

	public static void eliminarTodosElementos() {
		List<Player> listadoPlayer = FuncionesPlayer.obtenerListadoPlayer();
		List<Games> listadoGames = FuncionesGames.obtenerListadoGames();
		List<Compras> listadoCompras = FuncionesCompra.obtenerListadoCompras();
		boolean todasEliminadas = false;
		String confirmacion = "";
		try {
			ins.abrir();
			System.out.println("TABLA PLAYERS");
			if (listadoPlayer.size() > 0) {
				FuncionesPlayer.mostrarListadoPlayer(listadoPlayer);
			} else {
				System.out.println("No hay datos en la tabla player");
			}
			System.out.println("TABLA GAMES");
			if (listadoGames.size() > 0) {
				FuncionesGames.mostrarListadoGames(listadoGames);
			} else {
				System.out.println("No hay datos en la tabla games");
			}
			if (listadoCompras.size() > 0) {
				FuncionesCompra.mostrarCompra(listadoCompras);
			} else {
				System.out.println("No hay datos en la tabla compras");
			}

			confirmacion = FuncionesPlayer.confirmarTransac();
			if (confirmacion.equals("s")) {
				System.out.println("Transaccion confirmada");
				for (Player player : listadoPlayer) {
					ins.getSesion().delete(player);
				}
				for (Games games : listadoGames) {
					ins.getSesion().delete(games);
				}
				for (Compras com : listadoCompras) {
					ins.getSesion().delete(com);
				}
				System.out.println("Se han eliminado los datos de todas las tablas");

			} else {
				System.out.println("Transaccion cancelada");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
