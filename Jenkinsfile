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
		stage ('Run Tests') {
			environment {
				mvnHOME = tool 'MAVEN_HOME'
			}
			steps {
				sh "${mvnHome}/bin/mvn test"
			}
		}
	}
}
