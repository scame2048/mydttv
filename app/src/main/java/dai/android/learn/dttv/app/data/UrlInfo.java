package dai.android.learn.dttv.app.data;

public class UrlInfo {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String url) {
        address = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "title: " + name + ", url: " + address;
    }
}
