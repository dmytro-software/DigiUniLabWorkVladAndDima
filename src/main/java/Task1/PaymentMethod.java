package Task1;

interface PaymentMethod {
    String name();
    void pay(int amount);

    default void payWithFee(int amount, int fee) {
        System.out.println("Fee: " + fee + ", amount: " + amount);
        pay(amount + fee);
    }
}
