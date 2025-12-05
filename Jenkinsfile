pipeline {
    agent any

    environment {
        IMAGE = "mustafaguler4/deneme-service"
        REGISTRY = "docker.io"
        DOCKER_CREDENTIAL = "dockerhub-cred"   // Jenkins Credentials
        GIT_EMAIL = "you@example.com"
        GIT_NAME = "Mustafa Guler"
    }

    stages {

        stage('Build Maven') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    TAG = "${env.BUILD_NUMBER}"
                    sh "docker build -t ${IMAGE}:${TAG} ."
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: DOCKER_CREDENTIAL,
                        usernameVariable: 'USER',
                        passwordVariable: 'PASS'
                    )]) {
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                    }
                    sh "docker push ${IMAGE}:${TAG}"
                }
            }
        }

        stage('Update Kustomize Overlay') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                            sh """
                              git config user.email "${GIT_EMAIL}"
                              git config user.name "${GIT_NAME}"
                              git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/mustafaguler3/we.git
                              sed -i "s/newTag:.*/newTag: ${TAG}/" k8s/overlays/dev/kustomization.yaml
                              git add k8s/overlays/dev/kustomization.yaml
                              git commit -m "CI: Update dev image to ${TAG}" || echo "no changes to commit"
                              git push origin HEAD:master
                            """
                          }
                }
            }
        }
    }

    post {
        success {
            echo "CI/CD Pipeline Completed Successfully"
        }
        failure {
            echo "Pipeline Failed"
        }
    }
}