package ch.hasselba.service;

public class ServiceFactory {

	// ***************************************************************************
	// ** Attributes
	// ***************************************************************************

	/** Singleton instance. */
	private static ServiceFactory instance;

	// Singleton
	static {
		synchronized (ServiceFactory.class) {
			if (instance == null) {
				instance = new ServiceFactory();
				serviceLocator = new ServiceLocator();
			}
		}
	}

	protected static IServiceLocator serviceLocator;

	// ***************************************************************************
	// ** Singleton-Access
	// ***************************************************************************

	/**
	 * Instance Getter for Singleton
	 * 
	 * @return
	 */
	static public ServiceFactory getInstance() {
		return instance;
	}

	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

}
