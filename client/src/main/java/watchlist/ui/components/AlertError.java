package watchlist.ui.components;

import javafx.scene.control.Alert;

public class AlertError extends Alert {
    public AlertError(String header, String des) {
        super(AlertType.INFORMATION);
        this.setTitle("Fehler Meldung");
        this.setHeaderText(header);
        this.setContentText(des);
        this.getDialogPane().setPrefSize(400, 200);
        showAndWait();
    }
}
