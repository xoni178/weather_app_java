package weatherApp;

public class AppError {
    private static String errorMsg;

    public static void setErrorMesage(String msg) {
        errorMsg = msg;
    }

    public static String getErrorMesage() {
        return errorMsg;
    }
}
