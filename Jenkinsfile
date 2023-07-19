pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/thienhk15/bookstore.git'
      }
    }
    stage('Build') {
      steps {
        docker build -t bookstore .
      }
    }
    stage('Push') {
      steps {
        docker tag bookstore thienhk15/bookstore
        docker push thienhk15/bookstore
      }
    }
    stage('Deploy') {
          steps {
            docker rm bookstore-image
            docker run -d --name bookstore-image -p 80:8080 bookstore
          }
        }
  }
}