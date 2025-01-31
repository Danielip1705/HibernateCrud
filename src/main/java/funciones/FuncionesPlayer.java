package funciones;
import java.util.List;

import accesobd.AccesoBD;
import entidades.Player;
public class FuncionesPlayer {
	
	public static AccesoBD ins = new AccesoBD();
	
	public static void crearPlayer(Player player) {
			try {
				ins.abrir();
				int id = (int)ins.guardar(player);
				if(id !=0) {
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
	
	public static void listaPlayers() {
		try {
			ins.abrir();
			List<Player> listadoJugadores = ins.getSesion().createNativeQuery("SELECT * FROM Player",Player.class).getResultList();
			System.out.println("===========================");
			for (Player p : listadoJugadores) {
				System.out.println("IdPlayer: "+p.getIdPlayer());
				System.out.println("Nick: "+p.getNick());
				System.out.println("Contraseña: "+p.getPassword());
				System.out.println("Email: "+p.getEmail());
				System.out.println("===========================");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static boolean comprobarJugador(int id) {
		boolean existe = false;
		try {
			ins.abrir();
			Player p = null;
			p = ins.getSesion().get(Player.class, id);
			
			if(p!=null) {
				existe = true;
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return existe;
	}
	
}
