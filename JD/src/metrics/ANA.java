package metrics;

import java.util.Set;
import ast.ClassObject;
import ast.SystemObject;

public class ANA {
	public double anaValue;

	public ANA(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		DIT dit = new DIT(system);

		for (ClassObject classObject : classes) {
			anaValue += dit.computeDIT(system, classObject);
		}
		anaValue = anaValue/classes.size();
	}

	
	public String toString() 
	{
		return "System_Value: "+anaValue;
	}

}