package hr.tvz.napredna.java.dijezetserver.config;

public final class ApiPaths {
    public static final String BASE_URL = "/di-je-zet";
    public static final String BASE_API_URL = BASE_URL + "/api";

    public static final String STATION = BASE_API_URL + "/stations";

    public static final String AUTH = BASE_API_URL + "/auth";
    public static final String LOGIN = AUTH + "/login";
    public static final String REFRESH_TOKEN = AUTH + "/refresh-token";
    public static final String REGISTER = AUTH + "/register";

    public static final String LINE = BASE_API_URL + "/lines";

    public static final String USER = BASE_API_URL + "/users";
    public static final String USER_PROFILE = USER + "/profile";

    public static final String PIN = BASE_API_URL + "/pins";

    public static final String COMMENT = BASE_API_URL + "/comments";

    public static final String SWAGGER = BASE_API_URL + "/docs/**";

}
