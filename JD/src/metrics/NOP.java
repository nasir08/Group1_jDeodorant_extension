package metrics;

import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOP { 
	public int nopValue;
	
	public NOP(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		
		for(ClassObject classObj : classes) {
			nopValue += computeNOP(system, classObj);
		}
	}

	private int computeNOP(SystemObject system, ClassObject classObj) 
	{
		int counter = 0;
		List<MethodObject> methods = classObj.getMethodList();
		for(int i=0; i<methods.size(); i++)
		{
			if(methods.get(i).isAbstract())
			{
				counter++;
			}
		}
		return counter;
	}
	
	@Override
	public String toString() 
	{
		return "System_Value: "+nopValue;
	}
	
}