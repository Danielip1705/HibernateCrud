package funciones;

import java.util.List;
import java.util.Scanner;

import accesobd.AccesoBD;
import entidades.Compras;
import entidades.Games;
import entidades.Player;

public class FuncionesConectar {
	public static AccesoBD ins = new AccesoBD();
	public static Scanner sc = new Scanner(System.in);

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

			confirmacion = confirmarTransac();
			if (confirmacion.equals("s")) {
				System.out.println("Transaccion confirmada");
				for (Compras com : listadoCompras) {
					FuncionesCompra.eliminarPlayer(com.getIdCompras());
				}
				for (Player player : listadoPlayer) {
					FuncionesPlayer.eliminarPlayer(player.getIdPlayer());
				}
				for (Games g : listadoGames) {
					FuncionesGames.eliminarJuego(g.getIdGames());
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
}
