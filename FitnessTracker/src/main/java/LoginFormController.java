import com.company.Serializer;
import com.company.User;
import com.company.Users;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;


import static com.company.PathToFile.pathToFile;
import static com.company.UserManaging.registration;

public class LoginFormController {

    public TextField txtLogin;
    public Button btnLogin;
    private String login;
    Serializer serializer;
    static Optional<User> activeUser;

    public void registerUser() throws JAXBException {
        login = txtLogin.getText();
        if (login.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Необходимо ввести логин в поле");
            alert.showAndWait();
        } else {
            if (registration(login)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Регистрация прошла успешно! Входим");
                alert.showAndWait();
                loginUser();
            }
        }
    }

    public void loginUser() throws JAXBException {
        login = txtLogin.getText();
        if (login.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Необходимо ввести логин в поле");
            alert.showAndWait();
            return;
        }
        try {
            serializer = new Serializer();
            List<User> usersList = serializer.deserialize();
            if (usersList != null) {
                activeUser = usersList.stream().filter(i -> i.getLogin().equals(login)).findFirst();

                if (activeUser.isPresent()) {
                    Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                    alertSuccess.setHeaderText("Добро пожаловать, " + login);
                    alertSuccess.showAndWait();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/MainForm.fxml"));
                    try {
                        loader.load();

                        btnLogin.getScene().getWindow().hide();

                        Parent root = loader.getRoot();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        MainFormController controller =
                                loader.getController();
                        controller.initData(activeUser.get());
                        stage.show();

                    } catch (Exception e) {
                        // e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ОШИБКА!");
                        alert.setHeaderText(String.format("Ошибка при загрузке окна %s", "MainForm.fxml"));
                        alert.setContentText(String.valueOf(e));

                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Такой пользователь не найден, попробуйте еще раз или зарегистрируйтесь");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Такой пользователь не найден, попробуйте еще раз или зарегистрируйтесь");
                alert.showAndWait();
            }
        } catch (AssertionError e) {
            e.getCause().printStackTrace();
        }
    }
}
