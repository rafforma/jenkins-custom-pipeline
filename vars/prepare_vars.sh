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
