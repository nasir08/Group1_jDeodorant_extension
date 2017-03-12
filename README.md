# jdeodorant_extension
Extension of jDeodorant plug-in to compute software quality metrics

# Metrics included:
    1. Coupling Between Objects  
    2. Coupling Factor
    3. Method Inheritance Factor
    4. Attribute Inheritance Factor
    5. Depth of Inheritance Tree
    6. Number of Children
    7. Response for a Class
    8. Weighted Methods Per Class
    9. Number of Children
    10. Class Inerface Ssize

# To run the metrics on a java project in Eclipse
    1. Download/Clone the repository
    2. Import into Eclipse
    3. open views.MetricsAction.java - on line 138, change the file path to a path in your computer
    4. Run the jDeodorant As Eclipse Application
        This will open a new instance of Eclipse with jDeodorant running in it
    5. The project you wish to run the metrics on must be imported into the new instance of eclipse
    6. Right-click on the project and click "Metrics..."
        If you do not see the metrics option in the popup, expand the project in the project explorer and right-click on src
    7. The jDeodorant plug-in will parse the source code of the project, this might take a few minutes for larger projects
    8. After parsing, the metrics will be written to a txt file on the filepath specified in views.MetricsAction.java.
