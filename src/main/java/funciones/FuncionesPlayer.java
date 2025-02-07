package funciones;
import java.util.List;
import java.util.Scanner;

import accesobd.AccesoBD;
import entidades.Player;
public class FuncionesPlayer {
	
	public static AccesoBD ins = new AccesoBD();
	static Scanner sc = new Scanner(System.in);
	
	
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
	
	//Modificar
	
	public static void modificarJugador(long id,String nuevoNick,String nickfiltro,String nuevaConstraseña,String nuevoEmail,int filtro) {
		Player p = null;
		String confirmacion = "";
		List<Player> listado = null;
		try {
			ins.abrir();
			
			if(id!=0) {
				p = ins.getSesion().get(Player.class, id);				
			} else {
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Player where nick like :nick ;",Player.class)
						.setParameter("nick", "%"+nickfiltro+"%").getResultList();
			}
			
			if(p!=null) {
				switch(filtro) {
				case 1:
					p.setNick(nuevoNick);
					break;
				case 2:
					p.setPassword(nuevaConstraseña);
					break;
				case 3:
					p.setEmail(nuevoEmail);
					break;
				}
				System.out.println("¿Seguro que quieres modificar este jugador?");
				System.out.println("IdPlayer: "+p.getIdPlayer());
				System.out.println("Nick: "+p.getNick());
				System.out.println("Contraseña: "+p.getPassword());
				System.out.println("Email: "+p.getEmail());
				System.out.println("===========================");	
				confirmacion = confirmarTransac();
				
				if(confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					ins.getSesion().update(p);
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			} else {
				//For para actualizar
				for (Player player : listado) {
					switch(filtro) {
					case 1:
						player.setNick(nuevoNick);
						break;
					case 2:
						player.setPassword(nuevaConstraseña);
						break;
					case 3:
						player.setEmail(nuevoEmail);
						break;
					}
				}
				//For para confirmar transaccion
				for (Player player : listado) {
					System.out.println("IdPlayer: "+player.getIdPlayer());
					System.out.println("Nick: "+player.getNick());
					System.out.println("Contraseña: "+player.getPassword());
					System.out.println("Email: "+player.getEmail());
					System.out.println("===========================");	
				}
				confirmacion = confirmarTransac();
				if(confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					for (Player player : listado) {
						ins.getSesion().update(player);
					}
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			}
			
			ins.cerrar();
		}catch (Exception e) {
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
	
	//----------------------------------------------------------------------------------------------------
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
