package funciones;

import java.util.List;

import accesobd.AccesoBD;
import entidades.Compras;

public class FuncionesCompra {

	public static AccesoBD ins = new AccesoBD();

	public static void crearCompra(Compras compra) {
		try {
			ins.abrir();
			int id = (int) ins.guardar(compra);
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
		} catch (NullPointerException e) {
			System.out.println("La compra contiene un id de juego o jugador que no existe");
		} catch (IllegalStateException e) {
			System.out.println("Tiempo de espera finalizado: No se ha podido conectar a la BD");
		} catch (Exception e) {
			System.out.println("ERROR");
		}

	}

	public static void listaCompras() {
		try {
			ins.abrir();
			List<Compras> listadoCompras = ins.getSesion().createNativeQuery("SELECT * FROM Compras", Compras.class)
					.getResultList();
			System.out.println("===========================");
			for (Compras compras : listadoCompras) {
				System.out.println("IdCompras: " + compras.getIdCompras());
				System.out.println("IdPlayer: " + compras.getPlayer().getIdPlayer());
				System.out.println("IdGames: " + compras.getGame().getIdGames());
				System.out.println("Cosa : " + compras.getCosa());
				System.out.println("Precio: " + compras.getPrecio());
				System.out.println("Fecha Registro: " + compras.getFechaCompra().toString());
				System.out.println("===========================");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
