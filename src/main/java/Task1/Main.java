package Task1;

public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        PaymentMethod card = new CardPayment();
        PaymentMethod payPal = new PayPalPayment();

        processor.process(card, 600);
        processor.process(payPal, 200);

        System.out.println("Payment with service fee");
        card.payWithFee(100, 15);
        payPal.payWithFee(300,27);
    }
}