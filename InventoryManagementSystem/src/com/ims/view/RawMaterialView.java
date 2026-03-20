package com.ims.view;

import com.ims.controller.RawMaterialController;
import com.ims.model.RawMaterial;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RawMaterialView {

    private Stage stage;
    private RawMaterialController controller = new RawMaterialController();
    private TableView<RawMaterial> table     = new TableView<>();
    private ObservableList<RawMaterial> data = FXCollections.observableArrayList();

    private TextField nameField     = new TextField();
    private TextField quantityField = new TextField();
    private TextField minStockField = new TextField();
    private TextField supplierField = new TextField();
    private TextField unitField     = new TextField();
    private TextField searchField   = new TextField();

    private int selectedId = -1;

    public RawMaterialView(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        // ── TOP BAR ───────────────────────────────────────────
        Label titleLabel = new Label("Raw Materials Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-text-fill: white;");

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setStyle(
            "-fx-background-color: #1B3A6B;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6px 14px;" +
            "-fx-background-radius: 6;"
        );
        backBtn.setOnAction(e -> {
            DashboardView dashboard = new DashboardView(stage);
            dashboard.show();
        });

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(14, 20, 14, 20));
        topBar.setStyle("-fx-background-color: #2E5FA3;");
        topBar.setLeft(titleLabel);
        topBar.setRight(backBtn);
        BorderPane.setAlignment(titleLabel, Pos.CENTER_LEFT);
        BorderPane.setAlignment(backBtn, Pos.CENTER_RIGHT);

        // ── SEARCH BAR ────────────────────────────────────────
        searchField.setPromptText("Search by item name...");
        searchField.setPrefWidth(250);
        searchField.setPrefHeight(34);
        searchField.setStyle(
            "-fx-background-color: #F0F4FF;" +
            "-fx-border-color: #C0CCE0;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 6px;"
        );

        Button searchBtn = new Button("Search");
        searchBtn.setPrefHeight(34);
        searchBtn.setStyle(
            "-fx-background-color: #2E5FA3;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 6px 14px;"
        );

        Button showAllBtn = new Button("Show All");
        showAllBtn.setPrefHeight(34);
        showAllBtn.setStyle(
            "-fx-background-color: #27AE60;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 6px 14px;"
        );

        searchBtn.setOnAction(e -> {
            data.clear();
            data.addAll(controller.searchRawMaterials(searchField.getText()));
        });

        showAllBtn.setOnAction(e -> {
            searchField.clear();
            loadTableData();
        });

        Label searchLabel = new Label("Search:");
        searchLabel.setStyle("-fx-text-fill: #1B3A6B; -fx-font-weight: bold;");

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10, 20, 10, 20));
        searchBar.setStyle("-fx-background-color: #EEF2FF;");
        searchBar.getChildren().addAll(searchLabel, searchField, searchBtn, showAllBtn);

        // ── TABLE ─────────────────────────────────────────────
        TableColumn<RawMaterial, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell ->
            new SimpleIntegerProperty(cell.getValue().getId()).asObject());
        idCol.setPrefWidth(50);
        idCol.setMinWidth(50);

        TableColumn<RawMaterial, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().getName()));
        nameCol.setPrefWidth(160);
        nameCol.setMinWidth(160);

        TableColumn<RawMaterial, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(cell ->
            new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());
        qtyCol.setPrefWidth(90);
        qtyCol.setMinWidth(90);

        TableColumn<RawMaterial, Integer> minCol = new TableColumn<>("Min Stock");
        minCol.setCellValueFactory(cell ->
            new SimpleIntegerProperty(cell.getValue().getMinStockLevel()).asObject());
        minCol.setPrefWidth(90);
        minCol.setMinWidth(90);

        TableColumn<RawMaterial, String> supplierCol = new TableColumn<>("Supplier");
        supplierCol.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().getSupplierName()));
        supplierCol.setPrefWidth(160);
        supplierCol.setMinWidth(160);

        TableColumn<RawMaterial, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().getUnit()));
        unitCol.setPrefWidth(80);
        unitCol.setMinWidth(80);

        table.getColumns().addAll(idCol, nameCol, qtyCol, minCol, supplierCol, unitCol);
        table.setItems(data);
        table.setStyle("-fx-font-size: 13px;");

        // Row click fills the form
        table.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    selectedId = newVal.getId();
                    nameField.setText(newVal.getName());
                    quantityField.setText(String.valueOf(newVal.getQuantity()));
                    minStockField.setText(String.valueOf(newVal.getMinStockLevel()));
                    supplierField.setText(newVal.getSupplierName());
                    unitField.setText(newVal.getUnit());
                }
            }
        );

        loadTableData();

        // ── FORM ──────────────────────────────────────────────
        String fieldStyle =
            "-fx-background-color: #F0F4FF;" +
            "-fx-border-color: #C0CCE0;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 6px;" +
            "-fx-font-size: 13px;";

        nameField.setPromptText("e.g. Paper");
        nameField.setStyle(fieldStyle);
        nameField.setPrefHeight(34);

        quantityField.setPromptText("e.g. 100");
        quantityField.setStyle(fieldStyle);
        quantityField.setPrefHeight(34);

        minStockField.setPromptText("e.g. 20");
        minStockField.setStyle(fieldStyle);
        minStockField.setPrefHeight(34);

        supplierField.setPromptText("e.g. ABC Suppliers");
        supplierField.setStyle(fieldStyle);
        supplierField.setPrefHeight(34);

        unitField.setPromptText("e.g. Kg, Pcs, Litres");
        unitField.setStyle(fieldStyle);
        unitField.setPrefHeight(34);

        String labelStyle =
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #1B3A6B;";

        Label nameLabel     = new Label("Item Name");
        Label qtyLabel      = new Label("Quantity");
        Label minLabel      = new Label("Min Stock Level");
        Label supplierLabel = new Label("Supplier Name");
        Label unitLabel     = new Label("Unit");

        nameLabel.setStyle(labelStyle);
        qtyLabel.setStyle(labelStyle);
        minLabel.setStyle(labelStyle);
        supplierLabel.setStyle(labelStyle);
        unitLabel.setStyle(labelStyle);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(16, 16, 10, 16));

        // Set column widths
        form.getColumnConstraints().add(
            new javafx.scene.layout.ColumnConstraints(110)
        );
        javafx.scene.layout.ColumnConstraints col2 =
            new javafx.scene.layout.ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().add(col2);

        form.add(nameLabel,     0, 0); form.add(nameField,     1, 0);
        form.add(qtyLabel,      0, 1); form.add(quantityField, 1, 1);
        form.add(minLabel,      0, 2); form.add(minStockField, 1, 2);
        form.add(supplierLabel, 0, 3); form.add(supplierField, 1, 3);
        form.add(unitLabel,     0, 4); form.add(unitField,     1, 4);

        // Make fields fill full width
        GridPane.setHgrow(nameField,     Priority.ALWAYS);
        GridPane.setHgrow(quantityField, Priority.ALWAYS);
        GridPane.setHgrow(minStockField, Priority.ALWAYS);
        GridPane.setHgrow(supplierField, Priority.ALWAYS);
        GridPane.setHgrow(unitField,     Priority.ALWAYS);

        nameField.setMaxWidth(Double.MAX_VALUE);
        quantityField.setMaxWidth(Double.MAX_VALUE);
        minStockField.setMaxWidth(Double.MAX_VALUE);
        supplierField.setMaxWidth(Double.MAX_VALUE);
        unitField.setMaxWidth(Double.MAX_VALUE);

        // ── FORM BUTTONS ──────────────────────────────────────
        Button addBtn    = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button clearBtn  = new Button("Clear");

        addBtn.setPrefWidth(80);
        addBtn.setPrefHeight(36);
        addBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        addBtn.setStyle(
            "-fx-background-color: #27AE60;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;"
        );

        updateBtn.setPrefWidth(80);
        updateBtn.setPrefHeight(36);
        updateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        updateBtn.setStyle(
            "-fx-background-color: #F39C12;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;"
        );

        deleteBtn.setPrefWidth(80);
        deleteBtn.setPrefHeight(36);
        deleteBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        deleteBtn.setStyle(
            "-fx-background-color: #C0392B;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;"
        );

        clearBtn.setPrefWidth(80);
        clearBtn.setPrefHeight(36);
        clearBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        clearBtn.setStyle(
            "-fx-background-color: #7F8C8D;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;"
        );

        addBtn.setOnAction(e -> {
            boolean success = controller.addRawMaterial(
                nameField.getText(), quantityField.getText(),
                minStockField.getText(), supplierField.getText(),
                unitField.getText()
            );
            if (success) { clearForm(); loadTableData(); }
        });

        updateBtn.setOnAction(e -> {
            if (selectedId == -1) {
                com.ims.util.AlertHelper.showError("Please select a row first.");
                return;
            }
            boolean success = controller.updateRawMaterial(
                selectedId, nameField.getText(), quantityField.getText(),
                minStockField.getText(), supplierField.getText(),
                unitField.getText()
            );
            if (success) { clearForm(); loadTableData(); }
        });

        deleteBtn.setOnAction(e -> {
            if (selectedId == -1) {
                com.ims.util.AlertHelper.showError("Please select a row first.");
                return;
            }
            boolean success = controller.deleteRawMaterial(selectedId);
            if (success) { clearForm(); loadTableData(); }
        });

        clearBtn.setOnAction(e -> clearForm());

        HBox formButtons = new HBox(10);
        formButtons.setPadding(new Insets(10, 16, 16, 16));
        formButtons.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn);

        // ── RIGHT PANEL ───────────────────────────────────────
        Label formTitle = new Label("Item Details");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        formTitle.setStyle("-fx-text-fill: #1B3A6B;");

        VBox formTitleBox = new VBox(formTitle);
        formTitleBox.setPadding(new Insets(16, 16, 8, 16));
        formTitleBox.setStyle(
            "-fx-border-color: #DDEEFF;" +
            "-fx-border-width: 0 0 1 0;"
        );

        VBox rightPanel = new VBox();
        rightPanel.setPrefWidth(320);
        rightPanel.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #DDEEFF;" +
            "-fx-border-width: 0 0 0 1.5;"
        );
        rightPanel.getChildren().addAll(formTitleBox, form, formButtons);

        // ── MAIN LAYOUT ───────────────────────────────────────
        BorderPane centerLayout = new BorderPane();
        centerLayout.setCenter(table);
        centerLayout.setRight(rightPanel);
        centerLayout.setStyle("-fx-background-color: #F0F4FF;");
        BorderPane.setMargin(table, new Insets(10));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(new VBox(topBar, searchBar));
        mainLayout.setCenter(centerLayout);

        Scene scene = new Scene(mainLayout, 1000, 640);
        stage.setTitle("IMS - Raw Materials");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    private void loadTableData() {
        data.clear();
        data.addAll(controller.getAllRawMaterials());
    }

    private void clearForm() {
        selectedId = -1;
        nameField.clear();
        quantityField.clear();
        minStockField.clear();
        supplierField.clear();
        unitField.clear();
        table.getSelectionModel().clearSelection();
    }
}