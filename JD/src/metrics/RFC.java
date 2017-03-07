package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodInvocationObject;
import ast.MethodObject;
import ast.SystemObject;

public class RFC 
{
	private Map<String, Integer> classMap = new HashMap<String, Integer>();
	private ArrayList<MethodObject> methods = new ArrayList<MethodObject>();
	private ArrayList<MethodInvocationObject> invo = new ArrayList<MethodInvocationObject>();
	
	public RFC(SystemObject system)
	{
		Set<String> methodSet = new HashSet<String>();
		Set<ClassObject> classes = system.getClassObjects();
		for(ClassObject classObject : classes)
		{
			methods = (ArrayList) classObject.getMethodList(); //get all methods in each class
			
			for(MethodObject m : methods)
			{
				invo = (ArrayList) m.getMethodInvocations();
				methodSet.add(m.getName());
			}
			
			for(MethodInvocationObject i:invo)
			{
				if(!classObject.getName().equals(i.getClass()))
				methodSet.add(i.getMethodName());
			}
			
			classMap.put(classObject.getName(), methodSet.size());
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