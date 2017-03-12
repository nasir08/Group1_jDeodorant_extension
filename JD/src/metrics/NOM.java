package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;

public class NOM 
{
	private Map<String, Integer> classMap = new HashMap<String, Integer>();
	public NOM(SystemObject system) //passing an object of the current system to the constructor 
	{
		Set<ClassObject> classes = system.getClassObjects(); //hold all classes in the current system
		
		for(ClassObject classObject : classes)
		{
			classMap.put("", classObject.getNumberOfMethods()); //get the name and number of methods in each class
		}
		
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for(String key : classMap.keySet()) 
		{
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}

}
