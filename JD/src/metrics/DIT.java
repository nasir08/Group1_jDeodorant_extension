package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ast.ClassObject;
import ast.SystemObject;
import ast.TypeObject;

public class DIT {
	private Map<String, Integer> classMap = new HashMap<String, Integer>();

	public DIT(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();

		for (ClassObject classObject : classes) {
			int computeDit = computeDIT(system, classObject);

			classMap.put(classObject.getName(), computeDit);
		}
	}

	private int computeDIT(SystemObject system, ClassObject classObject) {
		TypeObject superCType = classObject.getSuperclass();
		int ditCount = 0;
		if(classObject.getSuperclass() != null)
		{
			ditCount++;
			
			ClassObject superCClass = system.getClassObject(superCType.getClassType());
			while(superCClass != null && superCClass.getSuperclass() != null)
			{
				if(superCClass != null && superCClass.getSuperclass() != null)
				{
					ditCount++;
				}
				else
				{
					break;
				}
				superCType = superCClass.getSuperclass();
				superCClass = system.getClassObject(superCType.getClassType());
			}
		}
		return ditCount;
	}
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}

}
