package funciones;
import java.util.List;

import accesobd.AccesoBD;
import entidades.Player;
public class FuncionesPlayer {
	
	public static AccesoBD ins = new AccesoBD();
	
	public static void crearPlayer(Player player) {
			try {
				ins.abrir();
				long id = (long)ins.guardar(player);
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
	
	
	//Listado
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
	
	public static void ListarPorId(long id) {
		Player p = null;
		try {
			ins.abrir();
			p = ins.getSesion().load(Player.class, id);
			if(!p.getNick().equals(null)) {
				System.out.println("==============================");
				System.out.println("IdPlayer: "+p.getIdPlayer());
				System.out.println("Nick: "+p.getNick());
				System.out.println("Contraseña: "+p.getPassword());
				System.out.println("Email: "+p.getEmail());
				System.out.println("===========================");				
			} else {
				System.out.println("No se ha encontrado el jugador");
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
			if(!nombre.equals("")) {
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Player WHERE nick like :nick ;",Player.class)
						.setParameter("nick", "%"+nombre+"%").getResultList();
			}
			
			if(listado.size() >0) {
				System.out.println("==============================");
				for (Player p : listado) {
					System.out.println("IdPlayer: "+p.getIdPlayer());
					System.out.println("Nick: "+p.getNick());
					System.out.println("Contraseña: "+p.getPassword());
					System.out.println("Email: "+p.getEmail());
					System.out.println("===========================");	
				}
			} else {
				System.out.println("No hay jugador con nombre "+ nombre+" en la BD");
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
			if(!correo.equals("")) {
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Player WHERE email like :correo ;",Player.class)
						.setParameter("correo", "%"+correo+"%").getResultList();
			}
			
			if(listado.size()>0) {
				System.out.println("==============================");
				for (Player p : listado) {
					System.out.println("IdPlayer: "+p.getIdPlayer());
					System.out.println("Nick: "+p.getNick());
					System.out.println("Contraseña: "+p.getPassword());
					System.out.println("Email: "+p.getEmail());
					System.out.println("===========================");	
				}
			} else {
				System.out.println("No hay jugador con email "+ correo+" en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//-----------------------------------------------------------------------------------------------------
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
	
	public static Player obtenerJugadorId(long id) {
		Player player = null;
		try {
			ins.abrir();
			player = ins.getSesion().load(Player.class, id);
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return player;
	}
	
}
