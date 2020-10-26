pipeline {
	agent any
	stages {
		stage ('Build Backend') {
			steps {
				def mvnHome = tool name: "MAVEN_HOME", type: 'maven'
				sh "${mvnHome}/bin/mvn clean package -DskipTests=true"
			}
		}
	}
}
