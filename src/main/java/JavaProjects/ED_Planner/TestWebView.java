package JavaProjects.ED_Planner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TestWebView extends Application {
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        webView.getEngine().load("https://services.onetcenter.org/embed/ip.js?client=edplanner");

        Scene scene = new Scene(webView, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Test WebView");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}