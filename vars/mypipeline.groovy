def call(Map pipelineParams) {

    pipeline {
        agent any
        stages {
            stage('checkout git') {
                steps {
                    // git branch: pipelineParams.branch, credentialsId: 'GitCredentials', url: pipelineParams.scmUrl
                    sh ''
                        echo My_Job=${JOB_NAME} > ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo Workspace_Home=${HOME}/workspace/${JOB_NAME} >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo MygitlabBranch=${gitlabBranch} >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo revision=${gitlabBranch#refs/tags/} >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo MygitlabActionType=${gitlabActionType} >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        if grep -q "\/*\.fast$" ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties; then
	                        echo fastdeploy=true >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        else
	                        echo fastdeploy=false >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        fi
                        if grep -q "\/*\.release$" ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties; then
	                        echo release=true >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        else
	                        echo release=false >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        fi

                        echo docker_img=false >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo git_prj_path=DEV_ACN >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo fortify=false >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo ng_options= >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        echo appURL=webapp-bu >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties

                        echo gitlabURL=${gitlabSourceRepoHttpUrl} >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties

                        if [[ $gitlabBranch = *".dev"* ]]; then
                            echo Base_Dir=/appl_bin/apache/2.4.33/htdocs >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                            echo PaaS=pipeline-dev >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                            echo host=hosts_dev >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        elif [[ $gitlabBranch = *".evo"* ]]; then
                            echo Base_Dir=/appl_bin/apache/2.4.33/htdocs/evo >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                            echo PaaS=pipeline-evo >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                            echo host=hosts_evo >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        elif [[ "${gitlabBranch}" =~ "pre" ]]; then
                            echo Base_Dir=null >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                            echo PaaS=null >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        else
                            echo Base_Dir=null >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                            echo PaaS=null >> ${HOME}/jobs/${JOB_NAME}/${JOB_NAME}.properties
                        fi                    
                    ''
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
