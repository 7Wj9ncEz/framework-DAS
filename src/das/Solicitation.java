package das;

import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class Solicitation {
	public void MakeSolicitation(User user, Resource resource){
		String user_type = "";
		Reflections reflections = new Reflections("das");
		Set<Class<? extends User>> allClasses = reflections.getSubTypesOf(User.class);
		for(Class<?> child : allClasses){
			if(child.isInstance(user)){
				user_type = child.getSimpleName();
			}
		}
		if(resource.getBorrowed() == false){
			System.out.println("Emprestando para " +  user.getName() + " do tipo " + user_type);
			resource.setBorrowed(true);
		}else{
			System.out.println("Já está emprestado");
		}
	}
	
	public void returnResource(User user, Resource resource){
		if(resource.getBorrowed()){
			System.out.println("Recurso devolvido, obrigado(a)!");
			resource.setBorrowed(false);
		}else{
			System.out.println("Esse item não foi emprestado");
		}
	}
	
}
