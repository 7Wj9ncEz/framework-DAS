package das;

import java.util.Set;

import org.reflections.Reflections;

public class Utility {

	public String UserType(UserBase user){
		String user_type = "";
		Reflections reflections = new Reflections("das");
		Set<Class<? extends UserBase>> allClasses = reflections.getSubTypesOf(UserBase.class);
		for(Class<?> child : allClasses){
			if(child.isInstance(user)){
				user_type = child.getSimpleName();
			}
		}
		return user_type;
	}
	
	public String ResourceType(ResourceBase resource){
		String resource_type = "";
		Reflections reflections = new Reflections("das");
		Set<Class<? extends ResourceBase>> allClasses = reflections.getSubTypesOf(ResourceBase.class);
		for(Class<?> child : allClasses){
			if(child.isInstance(resource)){
				resource_type = child.getSimpleName();
			}
		}
		return resource_type;
	}

}
