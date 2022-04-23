package searchzond.fayz.zond.Class;


public class User {
    private String image;
    private String thing;
    private String place;
    private String year;
    private String month;
    private String day;
    private String color;
    private String marka;
    private String content;



    public User() {
    }

    public User(String image, String thing, String place, String year, String month, String day, String color, String marka, String content) {
        this.image = image;
        this.thing = thing;
        this.place = place;
        this.year = year;
        this.month = month;
        this.day = day;
        this.color = color;
        this.marka = marka;
        this.content = content;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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

    public String getMarka() {
        return marka;
    }
    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
