import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Item {

    private IntegerProperty id;
    private StringProperty itemName;
    private StringProperty description;
    private StringProperty location;
    private StringProperty date;
    private StringProperty status;

    public Item(
            int id,
            String itemName,
            String description,
            String location,
            String date,
            String status) {

        this.id = new SimpleIntegerProperty(id);
        this.itemName = new SimpleStringProperty(itemName);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty statusProperty() {
        return status;
    }
}