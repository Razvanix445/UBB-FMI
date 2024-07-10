package app.network.jsonprotocol;

// TODO 1: IMPORT MODELS FOR JSON PROTOCOL UTILS NETWORKING

public class JsonProtocolUtils {

    /* TODO 2: CREATE RESPONSES FOR NETWORKING
    public static Response createSomethingResponse(Object something){
        Response resp = new Response();
        resp.setType(ResponseType.SOMETHING);
        resp.setSomething(something);
        return resp;
    }
    */

    public static Response createOkResponse(){
        Response response = new Response();
        response.setType(ResponseType.OK);
        return response;
    }

    public static Response createErrorResponse(String errorMessage){
        Response response=new Response();
        response.setType(ResponseType.ERROR);
        response.setErrorMessage(errorMessage);
        return response;
    }

    /* TODO 3: CREATE REQUESTS FOR NETWORKING
    public static Request createSomethingRequest(Object something){
        Request req = new Request();
        req.setType(RequestType.SOMETHING);
        req.setSomething(something);
        return req;
    }

    public static Request createLoginRequest(Something something){
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setSomething(something);
        return request;
    }

    public static Request createLogoutRequest(Something something){
        Request request = new Request();
        request.setType(RequestType.LOGOUT);
        request.setSomething(something);
        return request;
    }
     */
}