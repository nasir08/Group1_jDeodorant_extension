# jdeodorant_extension
Extension of jDeodorant plug-in to compute software quality metrics

# Metrics included:
    A. External Metrics
        1. Extendibility
        2. Reusability
        3. Functionality
        
    B. Internal Metrics
        1. Coupling Between Objects  (CBO) 
        2. Coupling Factor (CF)
        3. Method Inheritance Factor (MIF)
        4. Attribute Inheritance Factor (AIF)
        5. Depth of Inheritance Tree (DIT)
        6. Number of Methods (NOM) 
        7. Response for a Class (RFC)
        8. Weighted Methods Per Class (WMC)
        9. Number of Children (NOC)
        10. Class Inerface Size (CIS)
        11. Design Size In Class (DSC)
        12. Number of Heirarchies (NOH)
        13. Average Number of Ancestors (ANA)
        14. Direct Class Coupling (DCC)
        15. Cohesion Among Methods of Class (CAMC)
        16. Measure of Functional Abstraction (MFA)
        17. Number of Polymorphic Methods (NOP)
        18. Lack of Cohesion In Methods (LCOM)

# To run the metrics on a java project in Eclipse
    1. Download/Clone the repository
    2. Import into Eclipse
    3. open views.MetricsAction.java - on line 152, change the file path to a path in your computer
    4. Run the jDeodorant As Eclipse Application
        This will open a new instance of Eclipse with jDeodorant running in it
    5. The project you wish to run the metrics on must be imported into the new instance of eclipse
    6. Right-click on the project and click "Metrics..."
        If you do not see the metrics option in the popup, expand the project in the project explorer and right-click on src
    7. The jDeodorant plug-in will parse the source code of the project, this might take a few minutes for larger projects
    8. After parsing, the metrics will be written to a txt file on the filepath specified in views.MetricsAction.java.
