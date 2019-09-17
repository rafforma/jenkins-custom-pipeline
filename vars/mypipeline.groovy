def call(Map pipelineParams) {

    pipeline {
        agent any
	parameters {
            string(defaultValue: 'blank', description: '', name: 'gitlabActionType')
    	}
        stages {
            stage('checkout git') {
                steps {
                    // git branch: pipelineParams.branch, credentialsId: 'GitCredentials', url: pipelineParams.scmUrl                   
		    echo "checkout"
                }
            }
	   stage('environment setup') {
                steps {
                    sh "/home/raffaele/prepare_vars.sh"
                }
            }
            stage('build') {
                steps {
                    //sh 'mvn package -Dmaven.test.skip=true -DgitlabBranch=${MygitlabBranch} -Drevision=${revision}'
                	echo "build"
                }
            }

            stage ('test') {
		
                steps {
                    //sh 'mvn test -Dmaven.test.failure.ignore=false -DgitlabBranch=${MygitlabBranch} -Drevision=${revision}'
			echo "test"
                }
            }

            stage('sonar'){
		
                steps {
                
                   // sh 'mvn sonar:sonar -Dmaven.test.failure.ignore=true -DgitlabBranch=${MygitlabBranch} -Drevision=${revision} -Dsonar.projectName=${My_Job} -Dsonar.projectKey=${My_Job} -Dsonar.projectVersion=${revision}'
			echo "sonar"
                }
            }
		
	    stage('fortify'){
	        
	        steps {	    
	    	   /* sh """
			if [[ "$MygitlabActionType" = "TAG_PUSH" ]] 
			then
			sudo ansible-playbook /etc/ansible/playbooks/openshift_deploy/fortify_report.yml --extra-vars "workspace=${Workspace_Home} project=${My_Job} tag=${revision} gitlabgroup=${git_prj_path}"
			fi		   
		   """*/
			echo "fortify"
	 	}
	   }

            stage('deploy staging'){
                steps {                
                    sh 'echo deploy staging'
                }
            }

            stage('deploy production'){
                steps {                   
                    sh 'echo deploy production'
                }
            }
        }        
    }
}
