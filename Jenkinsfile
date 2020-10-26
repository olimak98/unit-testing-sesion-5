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
					sh "${scannerHome}/bin/sonar-scanner -e"
				}
			}
		}
	}
}
