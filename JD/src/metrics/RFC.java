package metrics;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodInvocationObject;
import ast.MethodObject;
import ast.SystemObject;

public class RFC 
{
	
	private Map<String, Integer> classMap = new HashMap<String, Integer>();
	private Set<String> M;
	private Set<String> R;
	private Set<String> RS;
	private int RFC;
	
	public RFC(SystemObject system)
	{
		Set<ClassObject> classes = system.getClassObjects();
		int rfcValue;
		for(ClassObject classObject : classes)
		{
			rfcValue = computeRFC(system,classObject);
			classMap.put(classObject.getName(), rfcValue);
		}
	}
	
	private int computeRFC(SystemObject system, ClassObject classObject)
	{
		M = new HashSet<String>();
		R = new HashSet<String>();
		RS = new HashSet<String>();
		RFC = 0;
		
		java.util.List<String> classesNames = system.getClassNames();
		
		java.util.List<MethodObject> methods = classObject.getMethodList();
		
		for(int i=0; i<methods.size(); i++)
		{
			 M.add(methods.get(i).getClassName()+"."+methods.get(i).getSignature()); //methods in the current class
			 
			 java.util.List<MethodInvocationObject> methodInvocations = methods.get(i).getMethodInvocations();
			 
			 for(int j=0; j<methodInvocations.size(); j++)
			 {
				 String originalClassName = methodInvocations.get(j).getOriginClassName();
				 if(classesNames.contains(originalClassName))
				 {
					 R.add(originalClassName+"."+methodInvocations.get(j).getSignature());
				 }
			 }
			
		}
		RFC = unionOfSets(M,R);
		return RFC;
	}
	
	private int unionOfSets(Set set1, Set set2)
	{
		Iterator<String> itr = set1.iterator();
		while(itr.hasNext())
		{
			set2.add(itr);
		}
		return set2.size();
		
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