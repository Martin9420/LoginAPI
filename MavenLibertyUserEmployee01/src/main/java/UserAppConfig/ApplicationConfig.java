package UserAppConfig;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest/*")
public class ApplicationConfig extends Application {
	
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Resources.UserJPA01.class);
		return classes;
	}

}
