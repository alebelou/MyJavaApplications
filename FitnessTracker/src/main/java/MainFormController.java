import com.company.Training;
import com.company.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static com.company.UserManaging.updateUserInfo;


public class MainFormController {

    @FXML
    Label labelAllCalories;

    @FXML
    Button btnStartTraining = new Button();

    @FXML
    Button btnStopTraining = new Button();

    @FXML
    Button btnPauseTraining = new Button();

    @FXML
    Label labelTimer = new Label();

    @FXML
    Label labelCalories = new Label();

    @FXML
    RadioButton radioPushUps = new RadioButton();

    @FXML
    RadioButton radioSitDowns = new RadioButton();

    @FXML
    RadioButton radioSkippingRope = new RadioButton();

    @FXML
    ToggleGroup groupTrainingType = new ToggleGroup();

    String TrainingType;

    User activeUser;

    long spentCalories;

    void initData(User user) {
        activeUser = user;
        labelAllCalories.setText("Общее количество потраченных каллорий = " + activeUser.getTotalCalories());
    }
    public void startTraining() {

        if (groupTrainingType.getSelectedToggle() != null) {
            RadioButton selectedButton = (RadioButton) groupTrainingType.getSelectedToggle();
            TrainingType = selectedButton.getText();
            btnStopTraining.setDisable(false);
            btnStartTraining.setDisable(true);
            radioSitDowns.setDisable(true);
            radioPushUps.setDisable(true);
            radioSkippingRope.setDisable(true);
            btnPauseTraining.setDisable(false);
            Thread thread = new Thread(runnable);
            thread.start();
            isStopped = false;
            labelCalories.setText("");

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Нужно выбрать тип тренировки!");
            alert.showAndWait();
        }

    }

    public void pauseTraining() {
        if (isStopped) {
            btnPauseTraining.setText("Пауза");
            isStopped = false;
        } else {
            isStopped = true;
            btnPauseTraining.setText("Продолжить");
        }
    }

    public void stopTraining() {
        isStopped = true;
        spentCalories = Training.getCalories(secondsAll, TrainingType);
        activeUser.setLastCalories((double) spentCalories);
        activeUser.setTotalCalories(activeUser.getTotalCalories() + activeUser.getLastCalories());
        labelCalories.setText("Потраченные калории: " + activeUser.getLastCalories());
        labelAllCalories.setText("Общее количество потраченных каллорий = " + (activeUser.getTotalCalories()));
        labelTimer.setText("" + 0 + " : " + 0);
        seconds = 0;
        minutes = 0;
        secondsAll = 0;
        btnPauseTraining.setDisable(true);
        btnStartTraining.setDisable(false);
        radioSitDowns.setDisable(false);
        radioPushUps.setDisable(false);
        radioSkippingRope.setDisable(false);
        btnStopTraining.setDisable(true);
        btnPauseTraining.setText("Пауза");
        updateUserInfo(activeUser);

        //System.exit(1);
    }

    int secondsAll = 0;
    int seconds = 0;
    int minutes = 0;
    boolean isStopped = false;

    Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (!isStopped) {
                        seconds = seconds + 10;
                        secondsAll = secondsAll + 10;
                        if (seconds == 60) {
                            minutes++;
                            seconds = 0;
                        }
                        Platform.runLater(() -> {
                            labelTimer.setText(" " + minutes + " : " + seconds + " ");
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
