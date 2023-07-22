pipeline {
  agent none
  environment{
    DOCKER_IMAGE = "nhtua/flask-docker"
  }
  stages {
    stage('Checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/thienhk15/bookstore.git'
      }
    }
    stage('Build') {
      steps {
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