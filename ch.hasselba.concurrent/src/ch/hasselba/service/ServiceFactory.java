package ch.hasselba.service;

/**
 * ServiceFactory 
 * A Factory pattern for the ServiceLocator
 * 
 * @author Sven Hasselbach
 */
public class ServiceFactory {

	private static ServiceFactory instance;
	protected static IServiceLocator serviceLocator;
	
	/**
	 * Static initalization
	 */
	static {
		synchronized (ServiceFactory.class) {
			if (instance == null) {
				instance = new ServiceFactory();
				serviceLocator = new ServiceLocator();
			}
		}
	}
	/**
	 * gets the instance of the factory
	 * 
	 * @return
	 * 	ServiceFactory instance
	 */
	static public ServiceFactory getInstance() {
		return instance;
	}

	/**
	 * gets the service locator
	 * 
	 * @return
	 * 	a service locator
	 */
	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

}
