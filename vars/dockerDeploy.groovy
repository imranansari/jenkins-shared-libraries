def call(String project, String hubUser) {
    script {
        try {
            sh "docker service update --image ${hubUser}/${project}:${env.BUILD_NUMBER} ${project}_main"
            sh "TAG=${env.BUILD_NUMBER} docker-compose -p ${project}-${env.BUILD_NUMBER} run --rm production"
        } catch (e) {
            sh "docker service update --rollback ${project}_main"
            error "Failed to update the service"
        }
    }
}