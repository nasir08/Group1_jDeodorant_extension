package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

import ast.Access;
import ast.ClassObject;
import ast.FieldObject;
import ast.ProjectUtils;
import ast.SystemObject;
import ast.TypeObject;

public class AIF {
	HashMap<String, LinkedList<String>> AIFMap;
	private static int InheritedAttribute = 0; //Total Numbers of Attributes Inherited from Super Class
	//private static int declaredmethod = 0; // Total Numbers of Methods Declared in the class
	private static int declaredattribute = 0; // Total Numbers of Attributes Declared in the class
	static int totalavailattributes = 0; // This is the total available attributes of a class (Inherited Attributes + Declared Attributes)
	static double AIFactor; // Final Attribute Inheritance Factor
	public AIF(SystemObject system) {
		ListIterator<ClassObject> iterate1 = system.getClassListIterator();
		FieldObject presentClassField;
		totalavailattributes = 0;
		while (iterate1.hasNext()) {
			ClassObject cobj = iterate1.next();		
			
			if(cobj.isInterface()){
				continue;
			}
			declaredattribute =0;
			// Get inherited methods of the class
			Set<String> inheritedAttributesobj = GetAllInheritedMeth(system, cobj);
			ListIterator<FieldObject> presentClassFields = cobj.getFieldIterator();
			while (presentClassFields.hasNext()) {				
				presentClassField = presentClassFields.next();
								
				if (inheritedAttributesobj.contains(presentClassField.toString())) {
					
					inheritedAttributesobj.remove(presentClassField.toString());
					presentClassFields.remove();
				}
				declaredattribute++;
			}
			
			// Count total number of inherited Attributes
			InheritedAttribute = InheritedAttribute + inheritedAttributesobj.size();
			
			// Count total number of available attributes
			totalavailattributes = totalavailattributes + (inheritedAttributesobj.size() + declaredattribute);
			

		}
		
		
		AIFactor = (double) InheritedAttribute / totalavailattributes;
		
	}
	@Override
	public String toString() {
		return AIFactor+"";
	}

	private Set<String> GetAllInheritedMeth(SystemObject system,ClassObject classObject) {
		
		Set<String> inheritedFields = new HashSet<String>();
		Set<String> classesInPackage = ProjectUtils.packageDetails
				.get(ProjectUtils
						.extractPackageNameFromWholeClassName(classObject
								.getName()));
		TypeObject superClass = classObject.getSuperclass();
		while (superClass != null && system.getClassObject(superClass
				.getClassType())!=null) {

			ClassObject superClassObject = system.getClassObject(superClass
					.getClassType());
			 ListIterator<FieldObject> superClassFields = superClassObject
					.getFieldIterator();
			while(superClassFields.hasNext()) {
				FieldObject field = superClassFields.next();
				
			 if (field.getAccess().equals(Access.PUBLIC)
						|| field.getAccess().equals(Access.PROTECTED)
						&& !field.isStatic()
						|| (field.getAccess().equals(Access.NONE) && classesInPackage
								.contains(superClassObject.getName()))) {
					inheritedFields.add(field.toString());
				}
			}
			superClass = superClassObject.getSuperclass();
		}
		
		return inheritedFields;
	}
}
