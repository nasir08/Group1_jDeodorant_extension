package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.TypeObject;


public class MIF {
	private Map<String, Float> classMap;
	
	public MIF(SystemObject system)
	{
		Set<ClassObject> classes = system.getClassObjects();
		classMap = new HashMap<String, Float>();
		float mifValue = 0.0f;
		for(ClassObject classObject : classes)
		{
			if(!classObject.isInterface())
			{
				mifValue = computeMIF(system, classObject);
				classMap.put("", mifValue);
			}
			else
			{
				classMap.put("", 0.0f);
			}
		}
	}

	private float computeMIF(SystemObject system, ClassObject classObject) {
		List<MethodObject> methodsDeclaredINCurrentClass = classObject.getMethodList();
		List<MethodObject> inheritedMethods = getInheritedMethods(system, classObject); 
		
		if(methodsDeclaredINCurrentClass.size()>0)
		{
			 return (float) inheritedMethods.size() / methodsDeclaredINCurrentClass.size();
		}
		else
		{
			return 0.0f;
		}
	}

	private List<MethodObject> getInheritedMethods(SystemObject system, ClassObject classObject) 
	{
		List<MethodObject> allMethodsInSub = classObject.getMethodList();
		List<MethodObject> methods = new ArrayList<MethodObject>();
		TypeObject superCType = classObject.getSuperclass();
		if(classObject.getSuperclass() != null)
		{
			ClassObject superCClass = system.getClassObject(superCType.getClassType());
			try 
			{ 
				methods = superCClass.getMethodList();
				for(int i=0; i<methods.size(); i++)
				{
					if(allMethodsInSub.contains(methods.get(i)))
					{
						methods.remove(i);
					}
					if((methods.get(i).getAccess().toString() != "public") && (methods.get(i).getAccess().toString() != "protected") && (methods.get(i).isStatic()))
					{
						methods.remove(i);
					}
				}
				while(superCClass != null && superCClass.getSuperclass() != null)
				{
					if(superCClass != null && superCClass.getSuperclass() != null)
					{
						superCType = superCClass.getSuperclass();
						superCClass = system.getClassObject(superCType.getClassType());
						try{  
							methods.addAll(superCClass.getMethodList());
							for(int i=0; i<methods.size(); i++)
							{
								if(allMethodsInSub.contains(methods.get(i)))
								{
									methods.remove(i);
								}
								if((methods.get(i).getAccess().toString() != "public") && (methods.get(i).getAccess().toString() != "protected") && (methods.get(i).isStatic()))
								{
									methods.remove(i);
								}
							}
						}
						catch(Exception e)
						{
							return Collections.emptyList();
						}
					}
					else
					{
						break;
					}
				}
			}
			catch(Exception e)
			{
				return Collections.emptyList();
			}
		}
		return methods;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}	
}