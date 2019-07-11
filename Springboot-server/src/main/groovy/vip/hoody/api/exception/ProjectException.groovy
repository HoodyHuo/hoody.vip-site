package vip.hoody.api.exception

class ProjectException extends RuntimeException {

    ProjectException(String message) {
        super(message)
    }

    ProjectException(String message, Throwable cause) {
        super(message, cause)
    }
}
