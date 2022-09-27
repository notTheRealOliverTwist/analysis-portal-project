module com.example.analysisportalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.analysisportalproject to javafx.fxml;
    exports com.example.analysisportalproject;
}