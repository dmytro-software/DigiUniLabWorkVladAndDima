package Task2;

abstract class ReportGenerator {

    public final void generate() {
        collectData();
        formatData();
        printReport();
    }

    protected abstract void collectData();
    protected abstract void formatData();

    private void printReport() {
        System.out.println("Звіт сформовано");
    }
}