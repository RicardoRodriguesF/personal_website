package rodrigues.ferreira.ricardo.website.personalwebsite.controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    MESSAGE_DEFAULT ("/defaut-error", "An error occurred while processing your request"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    SYSTEM_ERROR("/system-error", "System error"),
    INTERNAL_SERVER_ERROR("/internal-server-error", "Internal server error"),
    BAD_REQUEST("/bad-request", "Bad request"),
    UNAUTHORIZED("/unauthorized", "Unauthorized"),
    FORBIDDEN("/forbidden", "Forbidden"),
    INVALID_REQUEST("/invalid-request", "Invalid request"),
    REQUESTYPE_ERROR("/request-error", "Request error"),
    ENITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business error");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://ricardorferreira.com" + path;
        this.title = title;
    }
}
