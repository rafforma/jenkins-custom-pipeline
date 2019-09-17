def call(Map pipelineParams) {

    pipeline {
        agent any
        stages {
            stage('checkout git') {
                steps {
                    // git branch: pipelineParams.branch, credentialsId: 'GitCredentials', url: pipelineParams.scmUrl
                    sh "./prepare_vars.sh"
                }
            }

            stage('build') {
                steps {
                    //sh 'mvn clean package -DskipTests=true'
                    sh 'echo "mvn clean package -DskipTests=true"'
                }
            }

            stage ('test') {
                steps {
                    sh 'echo parallel test'
                }
            }

            stage('deploy developmentServer'){
                steps {
                    //deploy(pipelineParams.developmentServer, pipelineParams.serverPort)
                    sh 'echo deploy developmentServer'
                }
            }

            stage('deploy staging'){
                steps {
                    //deploy(pipelineParams.stagingServer, pipelineParams.serverPort)
                    sh 'echo deploy staging'
                }
            }

            stage('deploy production'){
                steps {
                    //deploy(pipelineParams.productionServer, pipelineParams.serverPort)
                    sh 'echo deploy production'
                }
            }
        }        
    }
}
