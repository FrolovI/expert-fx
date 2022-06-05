package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private String outValue;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        onLoad(stage, root, 2);

        var scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void onLoad(Stage stage, Parent root, Integer nextPage) {
        if (nextPage != null && nextPage == 5) {
            ProgressIndicator indicator = (ProgressIndicator) root.lookup("#indicator");
            Parent nextRoot = tryLoad(nextPage);

            Button nextButton = (Button) nextRoot.lookup("#home");

            makeResult(nextRoot);

            Parent homeRoot = tryLoad(null);

            nextButton.setOnAction(actionEvent -> {
                stage.setScene(new Scene(homeRoot, 600, 400));
                onLoad(stage, homeRoot, 2);
            });

            IndicatorAnimation updateAnimation = new IndicatorAnimation(indicator, stage, nextRoot);
            updateAnimation.start();
        } else {
            Button nextButton = (Button) root.lookup("#next");
            Parent nextRoot = tryLoad(nextPage);

            nextButton.setOnAction(actionEvent -> {
                stage.setScene(new Scene(nextRoot, 600, 400));
                onLoad(stage, nextRoot, nextPage + 1);

                extractNeededValues(root);
            });
        }
    }

    private void preLoad(Parent parent) {
        ChoiceBox box1 = (ChoiceBox) parent.lookup("#main1");
        if (box1 != null) {
            box1.setItems(FXCollections.observableArrayList("adasd", ""));
        }
    }

    private void makeResult(Parent nextRoot) {
        Label diagnose = (Label) nextRoot.lookup("#diagnose");
        Label description = (Label) nextRoot.lookup("#description");

        if ("Кровь постоянно".equalsIgnoreCase(outValue)) {
            diagnose.setText("Диагноз: Хронический геморрой II");
            description.setText("Рекомендуемый метод лечения: Лазерная вапоризация");
        }
        if ("Слизь много".equalsIgnoreCase(outValue)) {
            diagnose.setText("Диагноз: Хроническая задняя анальная трещина");
            description.setText("Рекомендуемый метод лечения: Коррекция питания,\nприменение фармпрепаратов");
        }
        if ("Гной постоянно".equalsIgnoreCase(outValue)) {
            diagnose.setText("Диагноз: Передний параректальный свищ");
            description.setText("Рекомендуемый метод лечения: Хирургическое вмешательство,\nрассечение свища");
        }
        if ("Кровь периодически".equalsIgnoreCase(outValue)) {
            diagnose.setText("Диагноз: Хронический геморрой I");
            description.setText("Рекомендуемый метод лечения: Консервативаная и\nмалоинвазивная терапия");
        }
        if ("Слизь немного".equalsIgnoreCase(outValue)) {
            diagnose.setText("Диагноз: Острый подкожно-подслизистый парапроктит");
            description.setText("Рекомендуемый метод лечения: Хирургическое вмешательство,\nвскрытие и дренирование");
        }
    }

    private void extractNeededValues(Parent root) {
        ChoiceBox out = getField(root, "#out");

        if (out != null) {
            this.outValue = (String) out.getValue();
        }
    }

    private ChoiceBox getField(Parent root, String id) {
        Node lookup = root.lookup(id);
        if (lookup instanceof ChoiceBox) {
            return (ChoiceBox) lookup;
        }
        return null;
    }

    private String getPage(Integer page) {
        if (page == null) {
            return "/main.fxml";
        }
        return String.format("/main_%d.fxml", page);
    }

    private Parent tryLoad(Integer page) {
        try {
            return FXMLLoader.load(getClass().getResource(getPage(page)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }

}