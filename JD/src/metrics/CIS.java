package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;
import ast.MethodObject;

public class CIS 
{
	private Map<String, Integer> classMap = new HashMap<String, Integer>();
	public double systemValue;
	
	public CIS(SystemObject system) //passing an object of the current system to the constructor 
	{
		List<MethodObject> methods = new ArrayList<MethodObject>();
		int num=0;
		Set<ClassObject> classes = system.getClassObjects(); //hold all classes in the current system
		
		for(ClassObject classObject : classes)
		{
			num = 0;
			methods = classObject.getMethodList(); //get all methods in each class
			for(MethodObject m : methods)
			{
				if(m.getAccess().toString().equals("public")) //check if access modifier is public, if yes increment by 1
				num+=1;
				systemValue +=1;
			}
			classMap.put(classObject.getName(), num);
		}
		systemValue = systemValue/classes.size();
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
	
	public String toString2() {
		return "System_Value: "+systemValue;
	}

}
