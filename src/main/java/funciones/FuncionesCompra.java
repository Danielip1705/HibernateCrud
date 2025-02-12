package funciones;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.exception.ConstraintViolationException;

import accesobd.AccesoBD;
import entidades.Compras;
import entidades.Games;
import entidades.Player;

public class FuncionesCompra {

	public static AccesoBD ins = new AccesoBD();
	static Scanner sc = new Scanner(System.in);

	public static void crearCompra(Compras compra) {
		try {
			ins.abrir();
			long id = (long) ins.guardar(compra);
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
		} catch (ConstraintViolationException e) {
			System.out.println("La compra contiene un id de juego o jugador que no existe");
		} catch (IllegalStateException e) {
			System.out.println("Tiempo de espera finalizado: No se ha podido conectar a la BD");
		} catch (Exception e) {
			System.out.println("ERROR");
		}

	}

//Listado
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

	public static void listarIdCompra(long id) {
		Compras c = null;
		try {
			ins.abrir();
			c = ins.getSesion().get(Compras.class, id);
			if(c!= null) {
				System.out.println("==============================");
				System.out.println("IdCompras: " + c.getIdCompras());
				System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
				System.out.println("IdGames: " + c.getGame().getIdGames());
				System.out.println("Cosa : " + c.getCosa());
				System.out.println("Precio: " + c.getPrecio());
				System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
				System.out.println("===========================");				
			} else {
				System.out.println("No se ha encontrado la compra");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
	public static void listarIdPlayer(long id) {
		List<Compras> listado = null;
		try {
			ins.abrir();

			listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE id_player = :id ;", Compras.class)
					.setParameter("id", id).getResultList();

			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Compras c : listado) {
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
				}
			} else {
				System.out.println("No hay compra con id de jugador " + id + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void listarIdGames(long id) {
		List<Compras> listado = null;
		try {
			ins.abrir();

			listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE id_games = :id ;", Compras.class)
					.setParameter("id", id).getResultList();

			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Compras c : listado) {
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
				}
			} else {
				System.out.println("No hay compra con id de juego " + id + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void listarObjeto(String objeto) {
		List<Compras> listado = null;
		try {
			ins.abrir();
			if(!objeto.equals("")) {
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE cosa like :cosa ;", Compras.class)
						.setParameter("cosa", "%"+objeto+"%").getResultList();				
			}

			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Compras c : listado) {
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
				}
			} else {
				System.out.println("No hay compra con objeto " + objeto + " en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void listarPrecio(double precio,String filtro) {
		List<Compras> listado = null;
		try {
			ins.abrir();
			switch(filtro) {
			case "<":
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE precio < :precio ;", Compras.class)
				.setParameter("precio", precio).getResultList();	
				break;
				
			case ">":
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE precio > :precio ;", Compras.class)
				.setParameter("precio", precio).getResultList();	
				break;
				
			case "=":
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE precio = :precio ;", Compras.class)
				.setParameter("precio", precio).getResultList();	
				break;
			}
			
			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Compras c : listado) {
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
				}
			} else {
				System.out.println("No hay compra que tenga un precio de " +precio +" en la BD");
			}
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void listarFecha(int año,int mes,int dia,Date fechaCompleta,int filtro) {
		List<Compras> listado = null;
		try {
			ins.abrir();
			
			switch (filtro) {
			case 1:
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE year(fechaCompra) = :año ;", Compras.class)
				.setParameter("año",año).getResultList();
				break;
			case 2:
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE month(fechaCompra) = :mes ;", Compras.class)
				.setParameter("mes",mes).getResultList();
				break;
			case 3:
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE day(fechaCompra) = :dia ;", Compras.class)
				.setParameter("dia",dia).getResultList();
				break;
			case 4:
				listado = ins.getSesion().createNativeQuery("SELECT * FROM Compras WHERE fechaCompra = :fecha ;", Compras.class)
				.setParameter("fecha",fechaCompleta).getResultList();
				break;
			}
			
			if (listado.size() > 0) {
				System.out.println("==============================");
				for (Compras c : listado) {
					System.out.println("IdCompras: " + c.getIdCompras());
					System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + c.getGame().getIdGames());
					System.out.println("Cosa : " + c.getCosa());
					System.out.println("Precio: " + c.getPrecio());
					System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
					System.out.println("===========================");
				}
			} else {
				if(fechaCompleta==null) {
					if(año!=0) {
						System.out.println("No hay compras que tenga fecha que empiece con el año " + año);
					} else if(mes!=0) {
						System.out.println("No hay compras que tenga fecha que empiece con el mes " + mes);
					} else if(dia!=0) {
						System.out.println("No hay compras que tenga fecha que empiece con el dia " + dia);
					}
				}else {
					System.out.println("No hay comprar que tenga la fecha "+ fechaCompleta.toString());
				}
			}
			
			ins.cerrar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// ---------------------------------------------------------------------------------------------------------

	//Modificar
	
	public static void modificarCompra(long id,long idPlayer,long idGames,String cosa,double precio,Date fechaCompra,long idPlayerFiltro,long idGamesFiltro,String cosaFiltro,int opc) {
		Compras c = null;
		Player p = null;
		Games g = null;
		String confirmacion = "";
		List<Compras> listado = null;
		try {
			ins.abrir();
			if(id!=0) {
				c = ins.getSesion().get(Compras.class, id);
			} else if(idPlayerFiltro!=0) {
				listado = ins.getSesion().createNativeQuery("select * from Compras where id_player = :id ;",Compras.class)
						.setParameter("id", idPlayerFiltro).getResultList();
			} else if(idGamesFiltro!=0) {
				listado = ins.getSesion().createNativeQuery("select * from Compras where id_games = :id ;",Compras.class)
						.setParameter("id", idGamesFiltro).getResultList();
			} else {
				listado = ins.getSesion().createNativeQuery("select * from Compras where cosa = :cosa ;",Compras.class)
						.setParameter("cosa", cosaFiltro).getResultList();
			}
			if(c!=null) {
				switch(opc) {
				case 1:
					p = FuncionesPlayer.obtenerJugadorId(idPlayer);
					c.setPlayer(p);
					break;
				case 2:
					g = FuncionesGames.buscarGamesId(idGames);
					c.setGame(g);
					break;
				case 3:
					c.setCosa(cosa);
					break;
				case 4:
					c.setPrecio(precio);
					break;
				case 5:
					c.setFechaCompra(fechaCompra);
					break;
				}
				System.out.println("IdCompras: " + c.getIdCompras());
				System.out.println("IdPlayer: " + c.getPlayer().getIdPlayer());
				System.out.println("IdGames: " + c.getGame().getIdGames());
				System.out.println("Cosa : " + c.getCosa());
				System.out.println("Precio: " + c.getPrecio());
				System.out.println("Fecha Registro: " + c.getFechaCompra().toString());
				System.out.println("===========================");
				confirmacion = confirmarTransac();
				if(confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					ins.getSesion().merge(c);
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			} else if(listado.size() >0) {
				for (Compras com : listado) {
					switch(opc) {
					case 1:
						p = FuncionesPlayer.obtenerJugadorId(idPlayer);
						com.setPlayer(p);
						break;
					case 2:
						g = FuncionesGames.buscarGamesId(idGames);
						com.setGame(g);
						break;
					case 3:
						com.setCosa(cosa);
						break;
					case 4:
						com.setPrecio(precio);
						break;
					case 5:
						com.setFechaCompra(fechaCompra);
						break;
					}
				}
				for (Compras com : listado) {
					System.out.println("IdCompras: " + com.getIdCompras());
					System.out.println("IdPlayer: " + com.getPlayer().getIdPlayer());
					System.out.println("IdGames: " + com.getGame().getIdGames());
					System.out.println("Cosa : " + com.getCosa());
					System.out.println("Precio: " + com.getPrecio());
					System.out.println("Fecha Registro: " + com.getFechaCompra().toString());
					System.out.println("===========================");
				}
				confirmacion = confirmarTransac();
				if(confirmacion.equals("s")) {
					System.out.println("Transaccion confirmada");
					for (Compras com : listado) {
						ins.getSesion().update(com);
					}
				} else {
					System.out.println("Transaccion cancelada");
					ins.getTransaction().rollback();
				}
			}
			ins.cerrar();
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
	//----------------------------------------------------------------------------------------------------------
}
