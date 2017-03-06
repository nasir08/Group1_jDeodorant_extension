package metrics;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.Access;
import ast.ClassObject;
import ast.ConstructorObject;
import ast.SystemObject;
import ast.MethodObject;

public class CIS 
{
	private Map<String, Integer> classMap = new HashMap<String, Integer>();
	public CIS(SystemObject system) //passing an object of the current system to the constructor 
	{
		ArrayList<MethodObject> methods = new ArrayList<MethodObject>();
		int num=0;
		Set<ClassObject> classes = system.getClassObjects(); //hold all classes in the current system
		
		for(ClassObject classObject : classes)
		{
			num = 0;
			methods = (ArrayList) classObject.getMethodList(); //get all methods in each class
			for(MethodObject m : methods)
			{
				if(m.getAccess().toString().equals("public")) //check if access modifier is public, if yes increment by 1
					num+=1;
			}
			classMap.put(classObject.getName(), num);
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
