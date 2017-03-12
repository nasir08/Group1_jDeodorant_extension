package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldObject;
import ast.SystemObject;
import ast.TypeObject;


public class AIF {
	private Map<String, Float> classMap;
	
	public AIF(SystemObject system)
	{
		Set<ClassObject> classes = system.getClassObjects();
		classMap = new HashMap<String, Float>();
		float aifValue = 0.0f;
		for(ClassObject classObject : classes)
		{
			if(!classObject.isInterface())
			{
				aifValue = computeAIF(system, classObject);
				classMap.put("", aifValue);
			}
			else
			{
				classMap.put("", 0.0f);
			}
		}
	}

	private float computeAIF(SystemObject system, ClassObject classObject) {
		List<FieldObject> attributesDeclaredINCurrentClass = classObject.getFieldList();
		List<FieldObject> inheritedAttributess = getInheritedAttributes(system, classObject, attributesDeclaredINCurrentClass); 
		
		if(attributesDeclaredINCurrentClass.size()>0)
		{
			 return (float) inheritedAttributess.size() / attributesDeclaredINCurrentClass.size();
		}
		else
		{
			return 0.0f;
		}
	}

	private List<FieldObject> getInheritedAttributes(SystemObject system, ClassObject classObject, List<FieldObject> attributesDeclaredINCurrentClass) 
	{
		List<FieldObject> fields = new ArrayList<FieldObject>();
		TypeObject superCType = classObject.getSuperclass();
		if(classObject.getSuperclass() != null)
		{
			ClassObject superCClass = system.getClassObject(superCType.getClassType());
			try 
			{ 
				fields = superCClass.getFieldList();
				for(int i=0; i<fields.size(); i++)
				{
					if(attributesDeclaredINCurrentClass.contains(fields.get(i)))
					{
						fields.remove(i);
					}
					if((fields.get(i).getAccess().toString() != "public") && (fields.get(i).getAccess().toString() != "protected") && (fields.get(i).isStatic()))
					{
						fields.remove(i);
					}
				}
				while(superCClass != null && superCClass.getSuperclass() != null)
				{
					if(superCClass != null && superCClass.getSuperclass() != null)
					{
						superCType = superCClass.getSuperclass();
						superCClass = system.getClassObject(superCType.getClassType());
						try{  
							fields.addAll(superCClass.getFieldList());
							for(int i=0; i<fields.size(); i++)
							{
								if(attributesDeclaredINCurrentClass.contains(fields.get(i)))
								{
									fields.remove(i);
								}
								if((fields.get(i).getAccess().toString() != "public") && (fields.get(i).getAccess().toString() != "protected") && (fields.get(i).isStatic()))
								{
									fields.remove(i);
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
		return fields;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}	
}