package metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.FieldObject;
import ast.SystemObject;
import ast.TypeObject;

public class DCC { 
	private List<String> allClassesInSystem = new ArrayList<String>();
	public double dccValue;
	
	public DCC(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		allClassesInSystem = system.getClassNames();
		
		for(ClassObject classObj : classes) {
			/*Calls to compute Import, Export and Inheritance Coupling*/
			int importCoupling = computeExportCoupling(system, classObj);
			
			dccValue += importCoupling;
		}
		dccValue = dccValue / classes.size();
	}

	private int computeExportCoupling(SystemObject system, ClassObject classObj) 
	{
		Set<ClassObject> allClassObjectInSystem = system.getClassObjects();
		int counter = 0;
		for(ClassObject eachClass : allClassObjectInSystem) 
		{
			List<FieldObject> classFields = eachClass.getFieldList();
			for(int i=0; i<classFields.size(); i++)
			{
				TypeObject to =	classFields.get(i).getType();
				if(allClassesInSystem.contains(to.toString()))
				{
					if(to.toString().equals(classObj.getName()))
					{
						counter++; 
						break;
					}
				}
			}
		}
		return counter;
	}
	
	@Override
	public String toString() 
	{
		return "System_Value: "+dccValue;
	}
	
}