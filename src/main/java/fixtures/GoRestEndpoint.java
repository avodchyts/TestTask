package fixtures;

public enum GoRestEndpoint {
    READ_USERS("/player/get/all"),
    READ_USER("/player/create/{login}"),
    CREATE_USERS("/player/get"),
    UPDATE_USERS("/player/update/{editor}/{id}"),
    DELETE_USERS("/player/delete/{login}");

    private static final String BASE_URI = "http://3.68.165.45";
    private final String endPoint;

    GoRestEndpoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPoint() {
        return String.format("%s%s", BASE_URI, endPoint);
    }
}
