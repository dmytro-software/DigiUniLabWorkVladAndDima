package Task1;

class CardPayment implements PaymentMethod {
    @Override
    public String name() {
        return "Visa/MasterCard";
    }

    @Override
    public void pay(int amount) {
        System.out.println("Card transaction:" + amount);
    }
}

