package Task3;

class MultiNotifier implements SmsNotifier, EmailNotifier {

    @Override
    public void send(String message) {
        SmsNotifier.super.send(message);

        EmailNotifier.super.send(message);
    }
}
