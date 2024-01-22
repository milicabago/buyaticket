package ba.sum.fsre.buyaticket.models;

public class CardModel {

    private String date;
    private String cardName;
    private String location;
    private String image;
    private double price;

    public CardModel() {
    }

    public CardModel(String date, String cardName, String location, String image, double price) {
        this.date = date;
        this.cardName = cardName;
        this.location = location;
        this.image = image;
        this.price = price;
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

    public String getEventType() {
        if (cardName != null && cardName.contains("-")) {
            String[] parts = cardName.split("-");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        }
        return "";
    }
}
