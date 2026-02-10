package Task3;

interface SmsNotifier {
    default void send(String message) {
        System.out.println("SMS: " + message);
    }
}