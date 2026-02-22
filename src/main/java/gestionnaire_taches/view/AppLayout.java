package gestionnaire_taches.view;

import javafx.scene.layout.BorderPane;

public class AppLayout {
    private BorderPane rootLayout;
    
    public AppLayout() {
        this.rootLayout = new BorderPane();
    }
    
    public BorderPane getRootLayout() {
        return rootLayout;
    }
    
    public void setMainContent(BorderPane content) {
        rootLayout.setCenter(content);
    }
}