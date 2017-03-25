package metrics;

import java.util.Set;

import ast.SystemObject;
import ast.inheritance.InheritanceDetection;

public class NOH {
	public int nohValue;

	public NOH(SystemObject system) {
		InheritanceDetection inhDet = new InheritanceDetection(system);
		Set<String> inh = inhDet.getRoots();
		nohValue = inh.size();
	}
	
	public String toString() {
		return "System_Value: "+nohValue;
	}
	
}