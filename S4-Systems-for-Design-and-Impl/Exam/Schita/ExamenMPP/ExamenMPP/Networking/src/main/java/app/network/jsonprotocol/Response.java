package app.network.jsonprotocol;

// TODO 1: IMPORT MODELS FOR RESPONSE CLASS

public class Response {

    private ResponseType type;
    private String errorMessage;

    // TODO 2: ADD MODELS FOR RESPONSE CLASS

    public Response(){}

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /* TODO 3: CREATE toString() FOR RESPONSE CLASS
    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", reservationManager=" + reservationManager +
                ", trip=" + trip +
                ", reservation=" + reservation +
                ", seat=" + seat +
                '}';
     */
}
