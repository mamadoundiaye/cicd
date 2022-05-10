pipeline {
    agent {
        label "master"
    }
    
    tools {
        maven "doumaven"
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "10.160.2.145:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo"
        NEXUS_CREDENTIAL_ID = "nexus"
    }
    
    stages {
        stage("Clone code from VCS") {
            steps {
                script {
                    git 'https://github.com/mamadoundiaye/cicd.git';
                }
            }
        }
        
        stage("Maven Build") {
            steps {
                script {
                    sh "mvn package -Dmaven.test.skip=true  "
                }
            }
        }
        stage('SonarQube Analytics') {
            steps {
                withSonarQubeEnv('sonarQube') {
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=test"
                }
            }
        }
        stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
        stage ('Deploy to tomcat') {
      steps {
        script {
          deploy adapters: [tomcat9(credentialsId: 'aaa1', path: '', url: 'http://192.168.1.55:8080/')], contextPath: '/doumavenx', onFailure: false, war: 'target/*.war'                             
        }
      }
    }
    
    
    }
  post {
        always {
            echo 'Hello'
            
            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
            
        }
    }
    
}
