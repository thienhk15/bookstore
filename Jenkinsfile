pipeline {
  agent any
  tools {
        maven 'my-maven'
    }
  stages {
    stage('checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/thienhk15/bookstore.git'
      }
    }
    stage('Build') {
      steps {
        sh 'mvn --version'
        sh 'java --version'
        sh 'mvn clean package -Dmaven.test.failure.ignore=true -e'
      }
    }
    stage('Push') {
      steps {
        withDockerRegistry(credentialsId: 'docker-thienhk15', url: 'hub.docker.com') {
          sh 'docker version'
          sh 'docker build -t bookstore .'
          sh 'docker tag bookstore thienhk15/bookstore'
          sh 'docker push thienhk15/bookstore'
        }
      }
    }
    stage('Deploy') {
      steps {
        sh 'docker rm web -f'
        sh 'docker run -d --name web -p 80:3000 bookstore'
      }
    }
  }
}
