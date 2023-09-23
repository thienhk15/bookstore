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
        sh 'mvn clean package -Dmaven.test.failure.ignore=true'
        sh 'docker version'
        sh 'docker build -t bookstore .'
      }
    }
    stage('Push') {
      steps {
        sh 'docker tag bookstore thienhk15/bookstore'
        sh 'docker push thienhk15/bookstore'
      }
    }
    stage('Deploy') {
      steps {
        sh 'docker rm bookstore-image'
        sh 'docker run -d --name bookstore-image -p 80:8080 bookstore'
      }
    }
  }
}
