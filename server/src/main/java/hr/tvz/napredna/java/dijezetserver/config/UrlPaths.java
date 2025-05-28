package hr.tvz.napredna.java.dijezetserver.config;

public interface UrlPaths {
    String BASE_URL = "/di-je-zet";
    String BASE_API_URL = BASE_URL + "/api";

    String STATION = BASE_API_URL + "/station";

    String LINE = BASE_API_URL + "/line";

    String USER = BASE_API_URL + "/user";

    String PIN = BASE_API_URL + "/pin";

    String COMMENT = BASE_API_URL + "/comment";
}
