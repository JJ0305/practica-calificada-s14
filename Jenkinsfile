pipeline {
    agent any

    tools {
        maven 'Maven3'
    }
    
    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials' 
        DOCKER_IMAGE_NAME     = 'jj0305/sistema-gestion-academica'
        IMAGE_TAG             = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile "-Dspring.datasource.url=jdbc:mysql://host.docker.internal:3306/db_gestion_academica?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test "-Dspring.datasource.url=jdbc:mysql://host.docker.internal:3306/db_gestion_academica?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests "-Dspring.datasource.url=jdbc:mysql://host.docker.internal:3306/db_gestion_academica?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${IMAGE_TAG} -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        sh "docker push ${DOCKER_IMAGE_NAME}:${IMAGE_TAG}"
                        sh "docker push ${DOCKER_IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'kubectl apply --validate=false -f kubernetes/'
            }
        }
    }

    post {
        success {
            echo '¡Pipeline ejecutado exitosamente y desplegado en Kubernetes!'
        }
        failure {
            echo 'El pipeline ha fallado en alguna de sus etapas. Revisa la consola.'
        }
    }
}