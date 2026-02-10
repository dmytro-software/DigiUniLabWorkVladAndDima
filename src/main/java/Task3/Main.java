package Task3;

public class Main {
    public static void main(String[] args) {
        MultiNotifier notifier = new MultiNotifier();

        notifier.send("Ваше замовлення готове");

        SmsNotifier smsOnly = notifier;
        smsOnly.send("Викликається SMS");
    }
}
