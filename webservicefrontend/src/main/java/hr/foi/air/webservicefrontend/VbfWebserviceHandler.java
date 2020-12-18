package hr.foi.air.webservicefrontend;

public interface VbfWebserviceHandler {
    void onDataArrived(
            Object result,
            boolean ok,
            long timeStamp, String product);
}
