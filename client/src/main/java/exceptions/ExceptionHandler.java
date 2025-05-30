package exceptions;

import okhttp3.Response;
import utils.GeneralUtils;

public class ExceptionHandler {

    public static Exception getException(Response response) {
        return switch (response.code()) {
            case 400 -> new BadRequestException("Bad Request");
            case 401 -> {
                try {
                    GeneralUtils.loadView("hello-view.fxml");
                } catch (Exception _) {
                }
                yield new UnauthorizedException("Unauthorized");
            }
            case 404 -> new NotFoundException("Not Found");
            default -> new Exception(response.message());
        };
    }
}
