package Sample.MavenLibertyRest01;

import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest/*")
public class HelloWorldAppConfig extends Application{
	
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Sample.MavenLibertyRest01.HelloWorldResource01.class);
		return classes;
	}

}
