pipeline {
  environment {
    imagename = 'botirkhuja/usa-4-for-you-backend'
    registryCredential = 'docker-botirkhuja-password'
    DOCKER_IMAGE = 'openjdk:25-bookworm'
    MINIO_URL = 'https://minios3.local.gulruz.com'
    MINIO_BUCKET_NAME = 'american-karvon-images'
    MINIO_CLIENT_IMAGE = 'minio/mc'

    MINIO_ACCESS_NAME = credentials('minio-access-key')
    MINIO_ACCESS_SECRET = credentials('minio-secret-key')
    dockerBuildImage = ''
    IMAGE_NAME = 'usa4foryou-app-build'
    CONTAINER_NAME = 'botirkhuja/usa4foryou-java-backend'
  }
  agent any
  stages {
    stage('List items') {
      steps {
        sh 'ls'
      }
    }

    stage('Build maven project using docker') {
      agent {
        docker {
          image 'eclipse-temurin:21'
          // run as root user
          args '-u root'
          reuseNode true
        }
      }
      steps {
        script {
          // Build the Maven project
          sh './mvnw clean package -DskipTests'
        }
      }
    }

    stage('List items after build') {
      steps {
        sh 'ls'
      }
    }

        stage('Build Docker Image') {
      steps {
        script {
          // Build Docker image from Dockerfile
          sh "docker build -f Dockerfile -t ${IMAGE_NAME} ."
          dockerBuildImage = docker.build(IMAGE_NAME)
        }
      }
        }

        stage('Push Image to Docker Registry') {
          steps {
            script {
              docker.withRegistry('', registryCredential) {
                dockerBuildImage.push("$BUILD_NUMBER")
                dockerBuildImage.push('latest')
              }
            }
          }
        }

        stage('Delete deployed image') {
          steps {
            script {
              sh "docker rmi ${IMAGE_NAME}:${BUILD_NUMBER}"
              sh "docker rmi ${IMAGE_NAME}:latest"
            }
          }
        }
  }
}

    post {
    success {
      echo 'Build completed successfully.'
    }
    failure {
      echo 'Build failed.'
    }
    always {
        sh 'docker stop $(docker ps -a -q) || true'
        sh 'docker rm $(docker ps -a -q) || true'
        sh 'docker rmi $(docker images -q) || true'
        cleanWs()
    }
    }
