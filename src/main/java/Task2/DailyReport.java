package Task2;

class DailyReport extends ReportGenerator {

    @Override
    protected void collectData() {
        System.out.println("Генерація транзакції");
    }

    @Override
    protected void formatData() {
        System.out.println("Формування даних");
    }
}