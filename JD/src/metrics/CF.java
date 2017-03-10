package metrics;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;

public class CF {
	double TC,nume; // TC= total numbers of classes in the system , nume= numerator value
	double CFValue; // Coupling Factor Value
	String totalCFStr; 
	Set<String> doneClasses = new HashSet<String>();
	
	public CF(SystemObject system) {
		//classes = system.getClassObjects();
		
		ListIterator<ClassObject> iterator1 = system.getClassListIterator();
		ListIterator<ClassObject> iterator2 = system.getClassListIterator();
		
		while(iterator1.hasNext()) {
			
			ClassObject cObj1 = iterator1.next();
			if(cObj1.isInterface())
				continue;
			TC++;
			doneClasses.add(cObj1.getName());
			iterator2 = system.getClassListIterator();
			while(iterator2.hasNext()) {
				ClassObject cObj2 = iterator2.next();
				if(cObj2.isInterface())
					continue;
				if(!doneClasses.contains(cObj2.getName())){
					if(cObj2.equals(cObj1) || cObj2.isFriend(cObj1.getName())||
							cObj1.equals(cObj2) || cObj1.isFriend(cObj2.getName()))
					{
						nume++;
					}
						
				}
			}
		}
		
		CFValue=nume/((TC*TC)-TC); 
		totalCFStr=nume+"/"+((TC*TC)-TC);
	}

	public double getCF(){
		return CFValue;
	}
	
	public String toString() {
		return CFValue+"";
	}


}
