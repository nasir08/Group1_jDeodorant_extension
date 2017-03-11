package metrics;
	
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Set;
	
	import ast.ClassObject;
	import ast.MethodObject;
	import ast.SystemObject;
	
		public class MIF {
		
		private int p; // p is total number of methods inherited
		private int q; // q is total number of methods
		private SystemObject system;
		
		/**
		 * Constructor for MIF.
		 * @param system SystemObject
		 */
		public MIF(SystemObject system){
			
			this.system=system;
			Set<ClassObject> classes = system.getClassObjects();
			
			for(ClassObject classObject : classes) {
				
				int m= 0; // total number of methods inherited for current class
				m = getMethodsInherited(classObject);
				int n= 0; // total number of methods declared for current class
				n = getMethodsDeclared(classObject);
				
				p += m;
				q += (m + n);		
			}
		}
		
		
		/**
		 * Method getMethodsInherited.
		 * @param classObject ClassObject
		 * @return int
		 */
		public int getMethodsInherited(ClassObject classObject)
		{
			int m = 0;
					
			List<MethodObject> methods=classObject.getMethodList();
			List<MethodObject> overridingMethods = new ArrayList<MethodObject>();
			List<MethodObject> inheritedMethods =  new ArrayList<MethodObject>();
			ClassObject subClassObject = classObject;
			
			// Get overriding methods
			for(MethodObject methodObject : methods){
				
				if(methodObject.overridesMethod()){
					overridingMethods.add(methodObject);
				}
			}
			
			// Get inherited methods from all the super classes
			 while((subClassObject.getSuperclass()!=null) && (system.getClassObject(subClassObject.getSuperclass().getClassType()))!=null){
			 
				 ClassObject superClassObject = system.getClassObject(subClassObject.getSuperclass().getClassType());
				 inheritedMethods.addAll(superClassObject.getMethodList());
				 subClassObject=superClassObject;
			 }
			 
			// Check if the inherited classes are overridden 
			for(MethodObject inheritedMethodObject : inheritedMethods){
				for(MethodObject overridingMethodObject : overridingMethods){
					if(!overridingMethodObject.getSignature().equals(inheritedMethodObject.getSignature())){
						m++;
					}
				}	
			}
			
			return m;
		}
		
		/**
		 * Method getMethodsDeclared.
		 * @param classObject ClassObject
		 * @return int
		 */
		public int getMethodsDeclared(ClassObject classObject)
		{
			return classObject.getNumberOfMethods();
		}
	
		/**
		 * Method getMIF.
		 * @return double
		 */
		public double getMIF(){
	
			return 100 * ((double) p / q);
					
		}
		
		/**
		 * Method toString.
		 * @return String
		 */
		public String toString(){
			return "MIF metric for the project is: "+ getMIF()+ " %\n";
		}
		
	}
