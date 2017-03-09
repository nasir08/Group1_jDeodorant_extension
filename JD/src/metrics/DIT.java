package metrics;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ast.ClassObject;
import ast.SystemObject;

public class DIT {
private Map<String, Integer> classMap = new HashMap<String, Integer>();
	
	public DIT(SystemObject system){
		Set<ClassObject> classes = system.getClassObjects();
		
		for(ClassObject classObject: classes){
			int computeDit = computeDIT(classObject);
			
			classMap.put(classObject.getName(), computeDit);
		}
	}
	
		private int computeDIT(ClassObject classObject){
		int num=0;
		Class c = classObject.getClass().getSuperclass();
		Class superclass = classObject.getClass().getSuperclass();
		if(c == null){
			return 0;
		}
		else{
			while (c != null){
			c = superclass;
			superclass = c.getSuperclass();
			num++;
		}
		return num;
	}
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}

}
