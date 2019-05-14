
// The call(body) method in any file in ami-jenkins-librar.git/vars is exposed as a
// method with the same name as the file.
def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        stage('checkout'){
            checkout scm
            removeTrailingSlash "foo/"
        }
        stage('validate') {
                sh "${config.packer} validate ${config.packerFile}"
        }
        stage('build') {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'AWS_CREDENTIALS']]) {
                    sh "${config.packer} build ${config.packerFile}"
                }
        }
    }

}
