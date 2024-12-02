package part01;

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
        String monthStr = "";
        // convert month number to string for readability
        switch (month) {
            case 1 -> monthStr = "Jan";
            case 2 -> monthStr = "Feb";
            case 3 -> monthStr = "Mar";
            case 4 -> monthStr = "Apr";
            case 5 -> monthStr = "May";
            case 6 -> monthStr = "Jun";
            case 7 -> monthStr = "Jul";
            case 8 -> monthStr = "Aug";
            case 9 -> monthStr = "Sep";
            case 10 -> monthStr = "Oct";
            case 11 -> monthStr = "Nov";
            case 12 -> monthStr = "Dec";
        }
        return exhibit.toString() + "\nMonth: " + monthStr;
    }
}