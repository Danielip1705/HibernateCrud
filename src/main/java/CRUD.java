import java.sql.Time;
import java.time.LocalDate;
import java.util.Scanner;

public class CRUD {
	//Atributos Player
	static int idPlayer;
	static String nick;
	static String password;
	static String email;
	//Atributos Games
	static int idGames;
	static String nombre;
	static Time tiempoJugado;
	//Atributos Compras
	static String cosa;
	static double precio;
	static LocalDate fechaCompra;
	public static void main(String[] args) {
		//Variables para el funcionamiento del programa
		int opc=-1;
		final String OPC_INVALIDA="Pelelín, esta opcion no existe >:[";
		Scanner sc = new Scanner(System.in);
		do {
			
			//imprimimos las opciones disponibles
			System.out.println("Indique que opcion quieres realizar");
			System.out.println("1. Conectarse");
			System.out.println("2. Crear Tablas");
			System.out.println("3. Insertar");
			System.out.println("4. Listar");
			System.out.println("5. Modificar");
			System.out.println("6. Eliminar");
			//el usuario indica la opcion
			opc = sc.nextInt();
			//Limpiamos buffer
			sc.nextLine();
			
			switch(opc) {
			case 1:
			
				break;
			
			
			
			default:
				System.out.println(OPC_INVALIDA);
			}
			
		} while(opc!=0);
		
		//Imprimimos en consola que has salido del programa
		System.out.println("Cerrado.");
		//Cerramos scanner
		sc.close();
		
	}
}
