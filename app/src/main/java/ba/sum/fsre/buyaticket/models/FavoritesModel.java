package ba.sum.fsre.buyaticket.models;

public class FavoritesModel {

    private Integer userId;
    private Integer cardId;
    private String date;
    private String cardName;
    private String location;
    private String image;
    private double price;

    public FavoritesModel() {
    }

    public FavoritesModel(Integer userId, Integer cardId, String date, String cardName, String location, String image, double price) {
        this.userId = userId;
        this.cardId = cardId;
        this.date = date;
        this.cardName = cardName;
        this.location = location;
        this.image = image;
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
