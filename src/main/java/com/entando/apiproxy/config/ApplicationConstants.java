package com.entando.apiproxy.config;

/**
 * Application constants
 */
public final class ApplicationConstants {
    
    public static final String API_CONFIG_DOES_NOT_EXIST_MSG = "The Api Configuration does not exist.";
    
    public static final String API_CONFIG_DELETED_MSG = "Api Configuration deleted successfully.";
    
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static final String ADMIN = "et-first-role";

    public static final String AUTHOR = "eh-author";

    public static final String MANAGER = "eh-manager";

    /* Date formats */
    public static final String API_CONFIG_CREATED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String API_CONFIG_UPDATED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /* Parameter values */
    public static final String API_CONFIG_SEARCH_PARAM_ALL = "ALL";
    public static final String API_CONFIG_SORT_PARAM_UPDATAED_AT = "updatedAt";
    public static final String API_CONFIG_SORT_PARAM_NAME = "contextPath";
    
    public static final short APPLICATION_NAME_MAX_LENGTH = 50;
    
    public static final String API_CONFIG_NOT_FOUND_ERR_MSG = "The Api Configuration with %s: %s not found";
    public static final String API_CONFIG_ALREADY_EXISTS_ERR_MSG = "An Api Configuration with same code already exists";
    public static final String API_CONFIG_NOT_AVAILABLE= "No Api Configuration is present";

}
