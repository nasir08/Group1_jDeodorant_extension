package metrics;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.decomposition.CompositeStatementObject;

public class WMC {
	
	private Map<String, Integer> classMap;
	
	public WMC(SystemObject system)
	{
		Set<ClassObject> classes = system.getClassObjects();
		classMap = new HashMap<String, Integer>();
		int wmcValue;
		for(ClassObject classObject : classes)
		{
			wmcValue = computeWMC(classObject);
			classMap.put("", wmcValue);
		}
	}

	private int computeWMC(ClassObject classObject)
	{
		int wmcForEachClass=0;
		List<MethodObject> methods = classObject.getMethodList();
		
		for(int i=0; i<methods.size(); i++)
		{
			MethodObject methodObject = methods.get(i);
			int ccForEachMethod = computeCyclomaticComplexity(methodObject);
			wmcForEachClass += ccForEachMethod;
		}
		 
		return wmcForEachClass;
	}

	private int computeCyclomaticComplexity(MethodObject methodObject)
	{
		int CC = 0;
		int D = 0;
		if(methodObject.getMethodBody() != null)
		{
			CompositeStatementObject compStmtObj = methodObject.getMethodBody().getCompositeStatement();
			List<CompositeStatementObject> totalDecisionStatement = compStmtObj.getIfStatements();
			totalDecisionStatement.addAll(compStmtObj.getSwitchStatements());
			totalDecisionStatement.addAll(compStmtObj.getForStatements());
			totalDecisionStatement.addAll(compStmtObj.getWhileStatements());
			totalDecisionStatement.addAll(compStmtObj.getDoStatements());
			D = totalDecisionStatement.size();
			CC = D + 1;
		}
		return CC;
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
