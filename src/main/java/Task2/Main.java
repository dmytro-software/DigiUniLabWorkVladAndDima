package Task2;

public class Main {
    public static void main(String[] args) {

        ReportGenerator daily = new DailyReport();
        daily.generate();

        ReportGenerator monthly = new ReportGenerator() {
            @Override
            protected void collectData() {
                System.out.println("Отримання даних з бази");
            }

            @Override
            protected void formatData() {
                System.out.println("Зведення даних у діаграми");
            }
        };
        monthly.generate();
    }
}
