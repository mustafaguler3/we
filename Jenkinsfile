pipeline {
  agent any

  environment {
    IMAGE = "mustafaguler4/deneme-service"
    REGISTRY_CRED = "dockerhub-cred"   // Jenkins'de Credential tanımla
    GIT_USER = "git-user"
    GIT_EMAIL = "you@example.com"
  }

  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('Docker Build & Push') {
      steps {
        script {
          def tag = "${env.BUILD_NUMBER}"
          sh "docker build -t ${IMAGE}:${tag} ."
          withCredentials([usernamePassword(credentialsId: REGISTRY_CRED, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
            sh "echo $PASS | docker login -u $USER --password-stdin"
          }
          sh "docker push ${IMAGE}:${tag}"
        }
      }
    }

    stage('Update kustomize overlay (dev)') {
      steps {
        script {
          def tag = "${env.BUILD_NUMBER}"
          sh """
            git config user.email "${GIT_EMAIL}"
            git config user.name "${GIT_USER}"
            git checkout -b jenkins/update-${tag}
            # Replace tag in overlay (sed for Linux/mac with compatible sed)
            sed -i "s|newTag: .*|newTag: \\"${tag}\\"|g" k8s/overlays/dev/kustomization.yaml || true
            git add k8s/overlays/dev/kustomization.yaml
            git commit -m "ci: update dev image to ${tag}"
            git push origin HEAD
          """
        }
      }
    }
  }

  post {
    failure {
      emailext subject: "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}", to: 'you@example.com'
    }
  }
}