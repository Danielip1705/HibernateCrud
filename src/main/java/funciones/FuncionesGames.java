package funciones;

import java.util.List;

import accesobd.AccesoBD;
import entidades.Games;

public class FuncionesGames {

	public static AccesoBD ins = new AccesoBD();

	public static void crearGames(Games game) {
		try {
			ins.abrir();
			int id = (int) ins.guardar(game);
			if(id !=0) {
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
	public static void listaGames() {
		try {
			ins.abrir();
			List<Games> listadoGames = ins.getSesion().createNativeQuery("SELECT * FROM Games",Games.class).getResultList();
			System.out.println("===========================");
			for (Games g : listadoGames) {
				System.out.println("IdGames: "+g.getIdGames());
				System.out.println("Nombre: "+g.getNombre());
				System.out.println("Tiempo Jugado: "+g.getTiempoJugado().toString());
				System.out.println("===========================");
			}
			
			ins.cerrar();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
