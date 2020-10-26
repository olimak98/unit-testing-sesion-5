pipeline {
	agent any
	stages {
		stage ('Build Backend') {
			environment {
				mvnHOME = tool 'MAVEN_HOME'
			}
			
			steps {
				sh "${mvnHome}/bin/mvn clean package -DskipTests=true"
			}
		}
	}
}
