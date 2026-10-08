import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {

    BorderPane root;
    VBox mainArea;

    String RED = "#8B1E2D";
    String LIGHT_RED = "#B52B3A";
    String LIGHT_GRAY = "#F5F5F5";

    @Override
    public void start(Stage stage) {

        root = new BorderPane();

        VBox sidebar = createSidebar();

        mainArea = new VBox();
        mainArea.setPadding(new Insets(30));
        mainArea.setStyle(
                "-fx-background-color: " + LIGHT_GRAY + ";"
        );

        root.setLeft(sidebar);
        root.setCenter(mainArea);

        showDashboard();

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("Lost & Found Management System");
        stage.setScene(scene);
        stage.show();
    }

    // ================= SIDEBAR =================

    private VBox createSidebar() {

        VBox sidebar = new VBox(20);

        sidebar.setPadding(
                new Insets(30, 20, 30, 20)
        );

        sidebar.setPrefWidth(220);

        sidebar.setStyle(
                "-fx-background-color: " + RED + ";"
        );

        Label title = new Label("LOST & FOUND");

        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", 22));

        Button dashboardButton =
                new Button("DASHBOARD");

        Button addButton =
                new Button("+ ADD ITEM");

        Button viewButton =
                new Button("VIEW ITEMS");

        styleSidebarButton(dashboardButton);
        styleSidebarButton(addButton);
        styleSidebarButton(viewButton);

        dashboardButton.setOnAction(
                e -> showDashboard()
        );

        addButton.setOnAction(
                e -> showAddForm()
        );

        viewButton.setOnAction(
                e -> showItems()
        );

        sidebar.getChildren().addAll(
                title,
                dashboardButton,
                addButton,
                viewButton
        );

        return sidebar;
    }

    private void styleSidebarButton(Button button) {

        button.setPrefWidth(180);
        button.setPrefHeight(45);

        button.setStyle(
                "-fx-background-color: white;" +
                "-fx-text-fill: " + RED + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;"
        );
    }

    // ================= DASHBOARD =================

    private void showDashboard() {

        mainArea.getChildren().clear();

        int total = getCount(
                "SELECT COUNT(*) FROM items"
        );

        int lost = getCount(
                "SELECT COUNT(*) FROM items WHERE status = 'Lost'"
        );

        int found = getCount(
                "SELECT COUNT(*) FROM items WHERE status = 'Found'"
        );

        Label title =
                new Label("Dashboard");

        title.setFont(
                Font.font("Arial", 30)
        );

        title.setTextFill(
                Color.web(RED)
        );

        Label subtitle =
                new Label(
                        "Lost & Found Management System"
                );

        subtitle.setTextFill(Color.GRAY);

        subtitle.setFont(
                Font.font("Arial", 16)
        );

        HBox cards = new HBox(25);

        cards.setPadding(
                new Insets(30, 0, 20, 0)
        );

        VBox totalCard =
                createCard(
                        String.valueOf(total),
                        "TOTAL ITEMS"
                );

        VBox lostCard =
                createCard(
                        String.valueOf(lost),
                        "LOST ITEMS"
                );

        VBox foundCard =
                createCard(
                        String.valueOf(found),
                        "FOUND ITEMS"
                );

        cards.getChildren().addAll(
                totalCard,
                lostCard,
                foundCard
        );

        mainArea.getChildren().addAll(
                title,
                subtitle,
                cards
        );
    }

    // ================= CARD =================

    private VBox createCard(
            String number,
            String text) {

        VBox card = new VBox(10);

        card.setPrefSize(210, 130);

        card.setAlignment(
                Pos.CENTER
        );

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #D0D0D0;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;"
        );

        Label numberLabel =
                new Label(number);

        numberLabel.setFont(
                Font.font("Arial", 32)
        );

        numberLabel.setTextFill(
                Color.web(RED)
        );

        Label textLabel =
                new Label(text);

        textLabel.setFont(
                Font.font("Arial", 14)
        );

        textLabel.setTextFill(
                Color.GRAY
        );

        card.getChildren().addAll(
                numberLabel,
                textLabel
        );

        return card;
    }

    // ================= ADD ITEM =================

    private void showAddForm() {

        mainArea.getChildren().clear();

        Label title =
                new Label("Add Item");

        title.setFont(
                Font.font("Arial", 28)
        );

        title.setTextFill(
                Color.web(RED)
        );

        TextField itemName =
                new TextField();

        itemName.setPromptText(
                "Item Name"
        );

        TextField description =
                new TextField();

        description.setPromptText(
                "Description"
        );

        TextField location =
                new TextField();

        location.setPromptText(
                "Location"
        );

        DatePicker date =
                new DatePicker();

        ComboBox<String> status =
                new ComboBox<>();

        status.getItems().addAll(
                "Lost",
                "Found"
        );

        status.setPromptText(
                "Status"
        );

        Button saveButton =
                new Button("SAVE ITEM");

        Button backButton =
                new Button("BACK");

        saveButton.setStyle(
                "-fx-background-color: " + RED + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        backButton.setStyle(
                "-fx-background-color: #B0B0B0;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        saveButton.setOnAction(e -> {

            if (
                    itemName.getText().isEmpty() ||
                    description.getText().isEmpty() ||
                    location.getText().isEmpty() ||
                    date.getValue() == null ||
                    status.getValue() == null
            ) {

                showAlert(
                        "Please fill all fields."
                );

                return;
            }

            String sql =
                    "INSERT INTO items " +
                    "(item_name, description, location, item_date, status) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (
                    Connection con =
                            DatabaseConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setString(
                        1,
                        itemName.getText()
                );

                ps.setString(
                        2,
                        description.getText()
                );

                ps.setString(
                        3,
                        location.getText()
                );

                ps.setDate(
                        4,
                        java.sql.Date.valueOf(
                                date.getValue()
                        )
                );

                ps.setString(
                        5,
                        status.getValue()
                );

                ps.executeUpdate();

                showAlert(
                        "Item added successfully!"
                );

                showDashboard();

            } catch (Exception ex) {

                showAlert(
                        "Error: "
                        + ex.getMessage()
                );
            }
        });

        backButton.setOnAction(
                e -> showDashboard()
        );

        VBox form = new VBox(15);

        form.setMaxWidth(500);

        form.getChildren().addAll(
                itemName,
                description,
                location,
                date,
                status,
                saveButton,
                backButton
        );

        mainArea.getChildren().addAll(
                title,
                form
        );
    }

    // ================= VIEW ITEMS =================

    private void showItems() {

        mainArea.getChildren().clear();

        Label title =
                new Label("All Items");

        title.setFont(
                Font.font("Arial", 28)
        );

        title.setTextFill(
                Color.web(RED)
        );

        TableView<Item> table =
                new TableView<>();

        TableColumn<Item, String> nameColumn =
                new TableColumn<>("Item");

        TableColumn<Item, String> descriptionColumn =
                new TableColumn<>("Description");

        TableColumn<Item, String> locationColumn =
                new TableColumn<>("Location");

        TableColumn<Item, String> dateColumn =
                new TableColumn<>("Date");

        TableColumn<Item, String> statusColumn =
                new TableColumn<>("Status");

        nameColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .itemNameProperty()
        );

        descriptionColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .descriptionProperty()
        );

        locationColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .locationProperty()
        );

        dateColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .dateProperty()
        );

        statusColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .statusProperty()
        );

        table.getColumns().addAll(
                nameColumn,
                descriptionColumn,
                locationColumn,
                dateColumn,
                statusColumn
        );

        loadItems(table);

        Button deleteButton =
                new Button(
                        "DELETE SELECTED ITEM"
                );

        deleteButton.setStyle(
                "-fx-background-color: " + RED + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        deleteButton.setOnAction(e -> {

            Item selectedItem =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selectedItem == null) {

                showAlert(
                        "Please select an item to delete."
                );

                return;
            }

            String sql =
                    "DELETE FROM items WHERE id = ?";

            try (
                    Connection con =
                            DatabaseConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setInt(
                        1,
                        selectedItem
                                .idProperty()
                                .get()
                );

                ps.executeUpdate();

                showAlert(
                        "Item deleted successfully!"
                );

                showItems();

            } catch (Exception ex) {

                showAlert(
                        "Error deleting item: "
                        + ex.getMessage()
                );
            }
        });

        Button backButton =
                new Button("BACK");

        backButton.setStyle(
                "-fx-background-color: " + RED + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        backButton.setOnAction(
                e -> showDashboard()
        );

        VBox box = new VBox(
                20,
                title,
                table,
                deleteButton,
                backButton
        );

        mainArea.getChildren().add(
                box
        );
    }

    // ================= LOAD ITEMS =================

    private void loadItems(
            TableView<Item> table) {

        String sql =
                "SELECT * FROM items";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Item item =
                        new Item(

                                rs.getInt("id"),

                                rs.getString(
                                        "item_name"
                                ),

                                rs.getString(
                                        "description"
                                ),

                                rs.getString(
                                        "location"
                                ),

                                rs.getDate(
                                        "item_date"
                                ).toString(),

                                rs.getString(
                                        "status"
                                )
                        );

                table.getItems().add(
                        item
                );
            }

        } catch (Exception ex) {

            showAlert(
                    "Error loading items: "
                    + ex.getMessage()
            );
        }
    }

    // ================= DATABASE COUNT =================

    private int getCount(String sql) {

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception ex) {

            showAlert(
                    "Error: "
                    + ex.getMessage()
            );
        }

        return 0;
    }

    // ================= ALERT =================

    private void showAlert(
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(
                "Lost & Found"
        );

        alert.setHeaderText(
                null
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }

    // ================= MAIN =================

    public static void main(
            String[] args) {

        launch(args);
    }
}