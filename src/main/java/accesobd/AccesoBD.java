package accesobd;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class AccesoBD {
	private static SessionFactory sf;
	private Session sesion;
	private Transaction transaction;

	protected void setUp() throws Exception {

		// Creación del registro de servicios estándar de Hibernate usando la
		// configuración por defecto (hibernate.cfg.xml)
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

		try {
			// Construcción de la fábrica de sesiones (SessionFactory) usando la metadata de
			// Hibernate
			sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			// Si ocurre algún error, destruye el registro de servicios para liberar
			// recursos
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	public Session getSesion() {
		return sesion;
	}
	

	public Transaction getTransaction() {
		return transaction;
	}

	// Método para abrir una nueva sesión de Hibernate y comenzar una transacción
	public void abrir() throws Exception {
		// Llama al método setUp() para configurar la fábrica de sesiones
		// (SessionFactory)
		setUp();

		// Abre una nueva sesión utilizando la SessionFactory previamente configurada
		sesion = sf.openSession();

		// Inicia una nueva transacción en la sesión abierta
		transaction = sesion.beginTransaction();
	}

	// Método para cerrar la sesión de Hibernate y manejar la transacción
	public void cerrar() {
		try {
			// Intenta hacer commit de la transacción (guardar cambios en la base de datos)
			transaction.commit();
		} catch (Exception e) {
			// Si ocurre una excepción, hace rollback para deshacer los cambios realizados
			transaction.rollback();
		}

		// Cierra la sesión de Hibernate para liberar los recursos
		sf.close();
	}

	// Método para guardar un objeto en la base de datos utilizando la sesión activa
	public Object guardar(Object cosa) {
		// Llama al método save de la sesión para persistir el objeto en la base de
		// datos
		return sesion.save(cosa);
	}


}
