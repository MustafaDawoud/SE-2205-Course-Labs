package ca.uwo.eng.se2205b.lab03;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Simple AutoComplete example to utilize a Trie.
 */
public class AutoComplete extends Application {
    
    @FXML
    private ListView<String> options;

    @FXML
    private TextField input;

    @FXML
    private Spinner<Integer> resultCounter;

    @FXML
    private Label countLabel;

    /**
     * Trie of words
     */
    private Trie prefixTrie;

    /**
     * Load the {@link #prefixTrie} field with the values from the provided dictionary.
     * There is a single word per line.
     */
    private void loadTrie() {
        // TODO SE2205B

        // InputStream io = AutoComplete.class.getResourceAsStream("/dictionary.txt");
    }

    /**
     * Read from the input box, and the spinner and calculate the auto complete and update the "countLabel"
     * with the number of results.
     */
    private void loadAutoComplete() {
        // TODO SE2205B
    }


    @FXML
    protected void initialize() {

        // TODO SE2205B

        ////////////////////////////////////////
        // DO NOT CHANGE BELOW
        ////////////////////////////////////////

        resultCounter.valueProperty().addListener((obs, oldValue, newValue) -> {
            loadAutoComplete();
        });

        countLabel.setText("0");

        loadTrie();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AutoComplete.fxml"));
    
        Scene scene = new Scene(root);
    
        stage.setTitle("Auto-complete Example");
        stage.setScene(scene);
        stage.show();
    }
    
    
}
