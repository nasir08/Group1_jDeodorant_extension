package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import ast.Access;
import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.TypeObject;
import ast.ProjectUtils;


public class MIF {

	HashMap<String,LinkedList<String>> MIFMap;
	private static int InheritedClass = 0; // Total Numbers of Methods Inherited from Super Class
	private static int declaredClass = 0; // Total Numbers of Methods Declared in the class
	static double MIFactor; // Final Method Inheritance Factor
	public  MIF(SystemObject system) {
		ListIterator<ClassObject> iterate1 = system.getClassListIterator();
		while(iterate1.hasNext())
		{
			ClassObject cobj = iterate1.next();
			if(cobj.isInterface())
			{
				continue;
			}
			// Get inherited methods of the class
			Set<MethodObject> inmethobj = GetAllInheritedMeth(system, cobj);
			List<MethodObject> listclassmeth = cobj.getMethodList(); // List of Present Methods in a Class
			for (MethodObject presentClassMethod : listclassmeth) {
				if (presentClassMethod.overridesMethod()) {
					inmethobj.remove(presentClassMethod);
				}
			}
			// Count the total number of methods in a class
			InheritedClass = InheritedClass + inmethobj.size();
						
			// Count the total number of methods declared in a class
			declaredClass = declaredClass + cobj.getMethodList().size();
					
		}
		System.out.println("Total number of inherited methods :"+InheritedClass);
		System.out.println("Total number of declared methods :"+declaredClass);
		int tavailmeth = InheritedClass + declaredClass; // This is the total available methods of a class 
		MIFactor = (double) InheritedClass / tavailmeth;
		
		System.out.println("Final value of MIF :"+ MIFactor);
	}
	@Override
	public String toString() {
		return MIFactor+"";
	}
	public Set<MethodObject> GetAllInheritedMeth(SystemObject system, ClassObject classObject) {
		Set<MethodObject> inherdMeth = new HashSet<MethodObject>();
		Set<String> classesInPackage = ProjectUtils.packageDetails.get(ProjectUtils.extractPackageNameFromWholeClassName(classObject.getName()));
		TypeObject superClass = classObject.getSuperclass();
		while (superClass != null && system.getClassObject(superClass.getClassType())!=null) {

			ClassObject superClassObject = system.getClassObject(superClass.getClassType());
			List<MethodObject> superClassMethods = superClassObject.getMethodList();
			for (MethodObject method : superClassMethods) {
				// This condition checks the inherited methods in the class 
				if (method.getAccess().equals(Access.PUBLIC)|| method.getAccess().equals(Access.PROTECTED)&& !method.isStatic()|| (method.getAccess().equals(Access.NONE) && classesInPackage.contains(superClassObject.getName()))) {
					inherdMeth.add(method);
				}
			}
			superClass = superClassObject.getSuperclass();
		}
		
		return inherdMeth;
	}
}
