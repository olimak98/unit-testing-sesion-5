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
		stage ('Sonar Analysis') {
			environment {
				scannerHome = tool 'SONAR_SCANNER'
			}
			steps {
				withSonarQubeEnv('Sonar') {
					sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=deployBackendCI -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=8b659e23f55bc5c1dd90b73b01a04520d8d53d89 -Dsonar.java.binaries=target"
				}
			}
		}
	}
}
