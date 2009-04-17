#!/bin/sh
#a script to start Sherlog
echo "Starting sherlog"
JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6
$JAVA_HOME/Home/bin/java -Xmx512m -jar ../target.platform/equinox-3.4/plugins/org.eclipse.osgi_3.4.0.v20080529-1200.jar -classpath $JAVA_HOME/Classes/classes.jar:$JAVA_HOME/Classes/ui.jar -console -configuration ./configuration -dev bin -clean

