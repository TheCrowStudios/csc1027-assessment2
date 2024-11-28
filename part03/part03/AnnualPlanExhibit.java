package part03;

public class AnnualPlanExhibit {
    private Exhibit exhibit;
    private int month;

    public AnnualPlanExhibit(Exhibit exhibit, int month) {
        this.exhibit = exhibit;
        this.month = month;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return exhibit.toString() + "\nMonth: " + month;
    }
}