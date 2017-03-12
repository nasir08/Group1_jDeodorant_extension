package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import metrics.AIF;
import metrics.CBO;
import metrics.CF;
import metrics.CIS;
import metrics.DIT;
import metrics.LCOM;
import metrics.MIF;
import metrics.NOC;
import metrics.NOM;
import metrics.RFC;
import metrics.WMC;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import ast.ASTReader;
import ast.CompilationUnitCache;
import ast.SystemObject;

public class MetricsAction  implements IObjectActionDelegate {
	
	private IWorkbenchPart part;
	private ISelection selection;
	
	private IJavaProject selectedProject;
	private IPackageFragmentRoot selectedPackageFragmentRoot;
	private IPackageFragment selectedPackageFragment;
	private ICompilationUnit selectedCompilationUnit;
	private IType selectedType;
	private IMethod selectedMethod;
	
	public void run(IAction arg0) {
		try {
			CompilationUnitCache.getInstance().clearCache();
			if(selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection)selection;
				Object element = structuredSelection.getFirstElement();
				if(element instanceof IJavaProject) {
					selectedProject = (IJavaProject)element;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
					selectedMethod = null;
				}
				else if(element instanceof IPackageFragmentRoot) {
					IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot)element;
					selectedProject = packageFragmentRoot.getJavaProject();
					selectedPackageFragmentRoot = packageFragmentRoot;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
					selectedMethod = null;
				}
				else if(element instanceof IPackageFragment) {
					IPackageFragment packageFragment = (IPackageFragment)element;
					selectedProject = packageFragment.getJavaProject();
					selectedPackageFragment = packageFragment;
					selectedPackageFragmentRoot = null;
					selectedCompilationUnit = null;
					selectedType = null;
					selectedMethod = null;
				}
				else if(element instanceof ICompilationUnit) {
					ICompilationUnit compilationUnit = (ICompilationUnit)element;
					selectedProject = compilationUnit.getJavaProject();
					selectedCompilationUnit = compilationUnit;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedType = null;
					selectedMethod = null;
				}
				else if(element instanceof IType) {
					IType type = (IType)element;
					selectedProject = type.getJavaProject();
					selectedType = type;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedMethod = null;
				}
				else if(element instanceof IMethod) {
					IMethod method = (IMethod)element;
					selectedProject = method.getJavaProject();
					selectedMethod = method;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
				}
				IWorkbench wb = PlatformUI.getWorkbench();
				IProgressService ps = wb.getProgressService();
				ps.busyCursorWhile(new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						if(ASTReader.getSystemObject() != null && selectedProject.equals(ASTReader.getExaminedProject())) {
							new ASTReader(selectedProject, ASTReader.getSystemObject(), monitor);
						}
						else {
							new ASTReader(selectedProject, monitor);
						}
						SystemObject system = ASTReader.getSystemObject();
						
						//objects initialization for metrics classes
						//LCOM lcom = new LCOM(system);
						NOM nom = new NOM(system);
						CIS cis = new CIS(system);
						RFC rfc = new RFC(system);
						WMC wmc = new WMC(system);
						MIF mif = new MIF(system);
						NOC noc = new NOC(system);
						CBO cbo = new CBO(system);
						DIT dit = new DIT(system);
						CF cf = new CF(system);
						AIF aif = new AIF(system);
						
						String fileName = "metrics.txt";
						File file = new File("C:\\Users\\nasir\\Desktop\\"+fileName);
						try {
							file.createNewFile();
							System.out.print(file.getAbsolutePath());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						try {
							FileWriter fw = new FileWriter(file,false);
							
							String content = "";
							
							//metrics
							//content +="*******************LCOM********************";
							//content += lcom.toString()+"\n\n";
							
				
							content +="*******************CBO********************\n";
							content +=cbo.toString()+"\n\n";
							
							content +="*******************CF********************\n";
							content +=cf.toString()+"\n\n";
							
							content +="*******************AIF********************\n";
							content +=aif.toString()+"\n\n";
							
							content +="*******************MIF********************\n";
							content +=mif.toString()+"\n\n";
							
							content +="*******************DIT********************\n";
							content +=dit.toString()+"\n\n";
							
							content +="*******************NOC********************\n";
							content +=noc.toString()+"\n\n";
							
							content +="*******************RFC********************\n";
							content +=rfc.toString()+"\n\n";
							
							content +="*******************WMC********************\n";
							content +=wmc.toString()+"\n\n";
							
							content +="*******************CIS********************\n";
							content +=cis.toString()+"\n\n";
							
							content +="*******************NOM********************\n";
							content +=nom.toString()+"\n\n";
							
							
							
							fw.write(content);
							fw.close();
						} 
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						if(selectedPackageFragmentRoot != null) {
							// package fragment root selected
						}
						else if(selectedPackageFragment != null) {
							// package fragment selected
						}
						else if(selectedCompilationUnit != null) {
							// compilation unit selected
						}
						else if(selectedType != null) {
							// type selected
						}
						else if(selectedMethod != null) {
							// method selected
						}
						else {
							// java project selected
						}
					}
				});
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.part = targetPart;
	}
}
