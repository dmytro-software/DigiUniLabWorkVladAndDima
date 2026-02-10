package Task1;

class PaymentProcessor {

    public void process(PaymentMethod method, int amount) {
        System.out.println("Method: " + method.name());
        method.pay(amount);
    }
}
