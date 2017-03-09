package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;
import ast.inheritance.InheritanceDetection;
import ast.inheritance.InheritanceTree;

public class NOC {
	private Map<String, Integer> classMap = new HashMap<String, Integer>();

	public NOC(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();

		for (ClassObject classObject : classes) {
			int computeDit = computeNOC(system, classObject);

			classMap.put(classObject.getName(), computeDit);
		}

	}

	private int computeNOC(SystemObject system, ClassObject classObject) {
		InheritanceDetection inhDet = new InheritanceDetection(system);
		InheritanceTree inhTree = inhDet.getTree(classObject.getName());
		int childCount = 0;
		if(inhTree != null)
		{
			childCount = inhTree.getRootNode().getChildCount();
		}
		return childCount;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}
	
}
