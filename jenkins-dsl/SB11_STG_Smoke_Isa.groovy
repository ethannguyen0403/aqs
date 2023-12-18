pipelineJob('QA-Automation/AQS/SB11_STG_Smoke_Isa') {
    parameters {
        stringParam('BRANCH', 'keanu-branch', 'Repo branch')
    }
    definition {
        cps {
            script("""
                pipeline {
                    agent {
                        kubernetes {
                            yaml \"""
                            kind: Pod
                            spec:
                              containers:
                              - name: automation
                                image: registry.internal.techtank9.com/registry/analyst/automation/aqs:\${params.BRANCH}
                                imagePullPolicy: Always
                                securityContext:
                                  privileged: true
                                  capabilities:
                                    add: ["SYS_ADMIN"]
                                env:
                                - name: DISPLAY
                                  value: ":0"
                                command:
                                - sleep
                                args:
                                - 99d
                                volumeMounts:
                                  - name: shm
                                    mountPath: /dev/shm
                              volumes:
                              - name: shm
                                hostPath:
                                    path: /dev/shm
                              imagePullSecrets:
                              - name: docker-credentials
                            \"""
                        }
                    }

                    stages {
                      stage ('Run') {
                        steps {
                          container(name: 'automation') {
                            sh '''cp /home/automation/chromedriver \$WORKSPACE'''
                            sh '''chmod +x \$WORKSPACE/chromedriver'''
                            sh '''sudo chown automation:automation /home/automation/'''
                            sh '''xvfb-run --server-args="-screen 0 1920x1080x24" java -cp '/home/automation/target/classes/:/home/automation/target/dependency/*' org.testng.TestNG /home/automation/src/main/suites/sb11/stgsmokeisa.xml'''
                          }
                        }
                      }
                    }

                    post { 
                      always { 
                        testNG(showFailedBuilds: true,
                               reportFilenamePattern: '**/testng-results.xml')
                        logParser parsingRulesPath: '/var/jenkins_config/log-parser/log-parser-rules.txt',
                                  showGraphs: true,
                                  useProjectRule: false,
                                  projectRulePath: ""
                        archiveArtifacts artifacts: '**',
                                        allowEmptyArchive: true,
                                        fingerprint: true
                      }
                      success {
                        slackSend channel: "#alert-sb11-test-result",
                                  message: "SUCCESSFUL: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}] (<${env.BUILD_URL}|Open>)",
                                  tokenCredentialId: "merito/jenkins/slack_creds",
                                  color: "#00FF00"
                      }
                      failure {
                        slackSend channel: "#alert-sb11-test-result",
                                  message: "FAILED: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})",
                                  tokenCredentialId: "merito/jenkins/slack_creds",
                                  color: "#FF0000"
                      }
                    }
                }""".stripIndent()
            )
        }
    }
}
