package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    BufferedReader in;
    PrintWriter out;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane mainPane;

    @FXML
    private TextArea messagesArea;

    @FXML
    private TextField messageInput;

    @FXML
    void sendMessage(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("Enter");
            out.println(messageInput.getText());
            messageInput.setText("");
        }
    }

    @FXML
    void initialize() throws IOException {
        String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(){
            @Override
            public void run() {
                while(true) {
                    String line = null;
                    try {
                        line = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    messagesArea.setText(messagesArea.getText() + line.substring(8) + "\n");
                }
            }
        }.start();

    }
}
