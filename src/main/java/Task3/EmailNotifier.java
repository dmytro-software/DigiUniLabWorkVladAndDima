package Task3;

interface EmailNotifier {
    default void send(String message) {
        System.out.println("Email: " + message);
    }
}