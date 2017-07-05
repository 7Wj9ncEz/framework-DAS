package das;
import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
public class UserFinder {
	private Class<?> myClass;

	public UserFinder(Class<?> clazz) {
	    myClass = clazz;
	}
	public UserFinder(String className) throws ClassNotFoundException {
	    myClass = Class.forName(className);
	}

	public Vector<String> getChildren() {
		User1 user1 = new User1();
	    Collection<Class<?>> children = new ArrayList<Class<?>>();
	    Vector<String> names = new Vector<String>();
	    Class<?>[] relatedClasses = myClass.getClasses();
	    for (Class<?> potentialChild : relatedClasses) {
	        if (potentialChild.isAssignableFrom(myClass)){
	            children.add(potentialChild);
	            names.add(potentialChild.getName());
	        }
	    }
	    return names;
	} 
}
