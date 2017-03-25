package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.TypeObject;


public class MFA {
	
	public float mfaValue;
	
	public MFA(SystemObject system)
	{
		Set<ClassObject> classes = system.getClassObjects();
		for(ClassObject classObject : classes)
		{
			if(!classObject.isInterface())
			{
				mfaValue += computeMFA(system, classObject);
			}
			else
			{
				mfaValue += 0;
			}
		}
		mfaValue = mfaValue/classes.size();
	}

	private float computeMFA(SystemObject system, ClassObject classObject) {
		List<MethodObject> methodsDeclaredINCurrentClass = classObject.getMethodList();
		List<MethodObject> inheritedMethods = getInheritedMethods(system, classObject);
		
		if(inheritedMethods.size() == 0)
		{
			return 0.0f;
		}
		
		else if(methodsDeclaredINCurrentClass.size() + inheritedMethods.size() > 0)
		{
			 return (float) inheritedMethods.size() / (methodsDeclaredINCurrentClass.size() + inheritedMethods.size());
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
		return "System_Value: "+mfaValue;
	}
}