module com.example.recipebookprojectfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires junit;
    requires java.datatransfer;

    opens com.example.recipebookprojectfinal to javafx.fxml;
    exports com.example.recipebookprojectfinal;
}