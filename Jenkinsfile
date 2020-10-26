pipeline {
	agent any
	stages {
		environment {
			mvnHOME = tool 'MAVEN_HOME'
		}
		stage ('Build Backend') {	
			steps {
				sh "${mvnHome}/bin/mvn clean package -DskipTests=true"
			}
		}
		stage ('Run Tests') {	
			steps {
				sh "${mvnHome}/bin/mvn test"
			}
		}
	}
}
