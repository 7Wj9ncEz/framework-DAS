package das;

import java.util.Set;

import org.reflections.Reflections;

public class Utility {

	public String UserType(User user){
		String user_type = "";
		Reflections reflections = new Reflections("das");
		Set<Class<? extends User>> allClasses = reflections.getSubTypesOf(User.class);
		for(Class<?> child : allClasses){
			if(child.isInstance(user)){
				user_type = child.getSimpleName();
			}
		}
		return user_type;
	}
	
	public String ResourceType(Resource resource){
		String resource_type = "";
		Reflections reflections = new Reflections("das");
		Set<Class<? extends Resource>> allClasses = reflections.getSubTypesOf(Resource.class);
		for(Class<?> child : allClasses){
			if(child.isInstance(resource)){
				resource_type = child.getSimpleName();
			}
		}
		return resource_type;
	}

}
