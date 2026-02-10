package Task1;

class PayPalPayment implements PaymentMethod {
    @Override
    public String name() {
        return "PayPal";
    }

    @Override
    public void pay(int amount) {
        System.out.println("Redirecting to PayPal:" + amount);
    }
}