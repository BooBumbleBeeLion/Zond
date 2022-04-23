package searchzond.fayz.zond.Class;

public class MainItems {
    private String imageurl;
    private String marka;
    private String thing;
    private String place;
    private String year;
    private String month;
    private String day;
    private String color;
    private String content;

    public MainItems(String imageurl, String marka, String thing, String place, String year, String month, String day, String color, String content) {
        this.imageurl = imageurl;
        this.marka = marka;
        this.thing = thing;
        this.place = place;
        this.year = year;
        this.month = month;
        this.day = day;
        this.color = color;
        this.content = content;
    }

    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getMarka() {
        return marka;
    }
    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getThing() {
        return thing;
    }
    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
