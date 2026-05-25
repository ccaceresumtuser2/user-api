pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 15, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ccaceresumtuser2/user-api.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw && ./mvnw clean package -DskipTests -q'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker-compose down || true
                    docker-compose up --build -d
                '''
            }
        }

    }

    post {
        success {
            echo "Desplegado correctamente en http://localhost:8099"
        }
        failure {
            echo "Pipeline fallido. Revisar logs."
        }
    }
}
