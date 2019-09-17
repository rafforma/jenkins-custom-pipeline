def call(Map pipelineParams) {

    pipeline {
        agent any
        stages {
            stage('checkout git') {
                steps {
                    // git branch: pipelineParams.branch, credentialsId: 'GitCredentials', url: pipelineParams.scmUrl                   
                }
            }
	   stage('environment setup') {
                steps {
                    // git branch: pipelineParams.branch, credentialsId: 'GitCredentials', url: pipelineParams.scmUrl                   
                }
            }
            stage('build') {
                steps {
                    //sh 'mvn package -Dmaven.test.skip=true -DgitlabBranch=${MygitlabBranch} -Drevision=${revision}'
                    sh 'echo "mvn clean package -DskipTests=true"'
                }
            }

            stage ('test') {
		when {not env.fastdeploy}
                steps {
                    //sh 'mvn test -Dmaven.test.failure.ignore=false -DgitlabBranch=${MygitlabBranch} -Drevision=${revision}'
                }
            }

            stage('sonar'){
		when {not env.fastdeploy}
                steps {
                    //deploy(pipelineParams.developmentServer, pipelineParams.serverPort)
                    sh 'mvn sonar:sonar -Dmaven.test.failure.ignore=true -DgitlabBranch=${MygitlabBranch} -Drevision=${revision} -Dsonar.projectName=${My_Job} -Dsonar.projectKey=${My_Job} -Dsonar.projectVersion=${revision}'
                }
            }
		
	    stage('fortify'){
	         when {not env.fastdeploy}
	        steps {	    
	    	   sh """
			if [[ "$MygitlabActionType" = "TAG_PUSH" ]] 
			then
			sudo ansible-playbook /etc/ansible/playbooks/openshift_deploy/fortify_report.yml --extra-vars "workspace=${Workspace_Home} project=${My_Job} tag=${revision} gitlabgroup=${git_prj_path}"
			fi		   
		   """
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
