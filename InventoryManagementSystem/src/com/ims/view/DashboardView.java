package com.ims.view;

import com.ims.dao.RawMaterialDAO;
import com.ims.model.RawMaterial;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardView {

    private Stage stage;
    private RawMaterialDAO rawMaterialDAO = new RawMaterialDAO();

    public DashboardView(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        // ── SIDEBAR ───────────────────────────────────────────
        Label logoLabel = new Label("IMS");
        logoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        logoLabel.setStyle("-fx-text-fill: white;");

        Label logoSub = new Label("Inventory Management System");
        logoSub.setFont(Font.font("Arial", 11));
        logoSub.setStyle("-fx-text-fill: #A8C4E0;");
        logoSub.setWrapText(true);

        VBox logoBox = new VBox(4);
        logoBox.setPadding(new Insets(30, 20, 30, 20));
        logoBox.getChildren().addAll(logoLabel, logoSub);

        Button rawMaterialBtn  = createSidebarBtn("Raw Materials");
        Button finishedProdBtn = createSidebarBtn("Finished Products");
        Button transactionBtn  = createSidebarBtn("Transactions");
        Button reportBtn       = createSidebarBtn("Reports");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #2E5FA3;");
        VBox.setMargin(sep, new Insets(10, 0, 10, 0));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setPrefHeight(42);
        logoutBtn.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        logoutBtn.setStyle(
            "-fx-background-color: #C0392B;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 6;"
        );
        VBox.setMargin(logoutBtn, new Insets(0, 16, 30, 16));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox sidebar = new VBox();
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #1B3A6B;");
        sidebar.getChildren().addAll(
            logoBox, sep,
            rawMaterialBtn, finishedProdBtn, transactionBtn, reportBtn,
            spacer, logoutBtn
        );

        // ── LIVE STATS FROM DATABASE ──────────────────────────
        List<RawMaterial> allItems = rawMaterialDAO.getAllRawMaterials();

        int totalItems     = allItems.size();
        int totalStock     = allItems.stream()
                                .mapToInt(RawMaterial::getQuantity).sum();
        long lowStockCount = allItems.stream()
                                .filter(r -> r.getQuantity() < r.getMinStockLevel())
                                .count();

        List<RawMaterial> lowStockItems = allItems.stream()
                                .filter(r -> r.getQuantity() < r.getMinStockLevel())
                                .collect(Collectors.toList());

        // ── CONTENT AREA ──────────────────────────────────────
        Label pageTitle = new Label("Dashboard");
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        pageTitle.setStyle("-fx-text-fill: #1B3A6B;");

        Label pageSubtitle = new Label("Overview of your inventory at a glance");
        pageSubtitle.setFont(Font.font("Arial", 13));
        pageSubtitle.setStyle("-fx-text-fill: #777777;");

        // ── STAT CARDS ────────────────────────────────────────
        VBox card1 = createStatCard(
            "Total Raw Materials", String.valueOf(totalItems), "#2E5FA3"
        );
        VBox card2 = createStatCard(
            "Total Stock Units", String.valueOf(totalStock), "#27AE60"
        );
        VBox card3 = createStatCard(
            "Low Stock Alerts", String.valueOf(lowStockCount), "#C0392B"
        );

        GridPane statsRow = new GridPane();
        statsRow.setHgap(16);
        statsRow.add(card1, 0, 0);
        statsRow.add(card2, 1, 0);
        statsRow.add(card3, 2, 0);

        // ── LOW STOCK TABLE ───────────────────────────────────
        Label lowStockTitle = new Label("Low Stock Items");
        lowStockTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lowStockTitle.setStyle("-fx-text-fill: #1B3A6B;");

        VBox lowStockBox = new VBox(8);
        lowStockBox.setPadding(new Insets(16));
        lowStockBox.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #DDEEFF;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1.5;"
        );
        lowStockBox.getChildren().add(lowStockTitle);

        if (lowStockItems.isEmpty()) {
            Label noAlert = new Label("All items are sufficiently stocked.");
            noAlert.setStyle("-fx-text-fill: #27AE60; -fx-font-size: 13px;");
            lowStockBox.getChildren().add(noAlert);
        } else {
            GridPane header = new GridPane();
            header.setHgap(10);
            header.setPadding(new Insets(6, 0, 6, 0));
            header.setStyle(
                "-fx-border-color: #DDEEFF;" +
                "-fx-border-width: 0 0 1 0;"
            );

            Label h1 = new Label("Item Name");
            Label h2 = new Label("Current Qty");
            Label h3 = new Label("Min Stock");
            Label h4 = new Label("Status");

            String hStyle =
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #1B3A6B;";

            h1.setStyle(hStyle); h1.setMinWidth(180);
            h2.setStyle(hStyle); h2.setMinWidth(100);
            h3.setStyle(hStyle); h3.setMinWidth(100);
            h4.setStyle(hStyle);

            header.add(h1, 0, 0);
            header.add(h2, 1, 0);
            header.add(h3, 2, 0);
            header.add(h4, 3, 0);
            lowStockBox.getChildren().add(header);

            for (RawMaterial item : lowStockItems) {
                GridPane row = new GridPane();
                row.setHgap(10);
                row.setPadding(new Insets(6, 0, 6, 0));

                Label rName = new Label(item.getName());
                Label rQty  = new Label(String.valueOf(item.getQuantity()));
                Label rMin  = new Label(String.valueOf(item.getMinStockLevel()));
                Label rStat = new Label("LOW STOCK");

                rName.setMinWidth(180);
                rQty.setMinWidth(100);
                rMin.setMinWidth(100);

                rName.setStyle("-fx-font-size: 13px;");
                rQty.setStyle(
                    "-fx-font-size: 13px;" +
                    "-fx-text-fill: #C0392B;" +
                    "-fx-font-weight: bold;"
                );
                rMin.setStyle("-fx-font-size: 13px;");
                rStat.setStyle(
                    "-fx-background-color: #FADBD8;" +
                    "-fx-text-fill: #C0392B;" +
                    "-fx-font-size: 11px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-padding: 3px 8px;" +
                    "-fx-background-radius: 10;"
                );

                row.add(rName, 0, 0);
                row.add(rQty,  1, 0);
                row.add(rMin,  2, 0);
                row.add(rStat, 3, 0);
                lowStockBox.getChildren().add(row);
            }
        }

        // ── CONTENT LAYOUT ────────────────────────────────────
        VBox contentArea = new VBox(20);
        contentArea.setPadding(new Insets(30, 30, 30, 30));
        contentArea.setAlignment(Pos.TOP_LEFT);
        contentArea.setStyle("-fx-background-color: #F0F4FF;");
        contentArea.getChildren().addAll(
            pageTitle,
            pageSubtitle,
            statsRow,
            lowStockBox
        );

        // ── BUTTON ACTIONS ────────────────────────────────────
        rawMaterialBtn.setOnAction(e -> {
            new RawMaterialView(stage).show();
        });

        finishedProdBtn.setOnAction(e -> {
            com.ims.util.AlertHelper.showWarning(
                "Finished Products module coming soon."
            );
        });

        transactionBtn.setOnAction(e -> {
            com.ims.util.AlertHelper.showWarning(
                "Transactions module coming soon."
            );
        });

        reportBtn.setOnAction(e -> {
            com.ims.util.AlertHelper.showWarning(
                "Reports module coming soon."
            );
        });

        logoutBtn.setOnAction(e -> {
            new LoginView(stage).show();
        });

        // ── MAIN LAYOUT ───────────────────────────────────────
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(contentArea);

        Scene scene = new Scene(mainLayout, 900, 600);
        stage.setTitle("IMS - Dashboard");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    private Button createSidebarBtn(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(46);
        btn.setFont(Font.font("Arial", 13));
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(0, 0, 0, 24));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #A8C4E0;" +
            "-fx-cursor: hand;" +
            "-fx-border-color: transparent;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #2E5FA3;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-border-color: transparent;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #A8C4E0;" +
            "-fx-cursor: hand;" +
            "-fx-border-color: transparent;"
        ));
        return btn;
    }

    private VBox createStatCard(String title, String value, String color) {
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        valueLabel.setStyle("-fx-text-fill: " + color + ";");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 12));
        titleLabel.setStyle("-fx-text-fill: #777777;");

        VBox card = new VBox(6);
        card.setPadding(new Insets(20));
        card.setPrefWidth(190);
        card.setPrefHeight(90);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #DDEEFF;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1.5;"
        );
        card.getChildren().addAll(valueLabel, titleLabel);
        return card;
    }
}