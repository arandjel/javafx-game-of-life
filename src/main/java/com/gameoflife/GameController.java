package com.gameoflife;

import java.net.URL;
import java.util.ResourceBundle;

import com.gameoflife.datamodel.Cell;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GameController implements Initializable {

	@FXML
	private GridPane gridPane;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private Button nextButton;

	@FXML
	private void handleStartButton(ActionEvent event) {
	}

	@FXML
	private void handleResetButton(ActionEvent event) {
	}

	@FXML
	private void handleNextButton(ActionEvent event) {
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		int rowNum = 15;
		// will use n*n grid matrix for now
		int colNum = rowNum;

		double rectangleSize = gridPane.getPrefWidth() / (double) rowNum;

		GameLogic gameLogic = new GameLogic(rowNum, colNum);
		gameLogic.randomPopulation(0.3);

		double duration = 1;
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(duration), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				gridPane.getChildren().clear();
				for (int row = 0; row < rowNum; row++) {
					for (int col = 0; col < colNum; col++) {
						Cell cell = new Cell();

						cell.setWidth(rectangleSize);
						cell.setHeight(rectangleSize);
						cell.setIsAlive(gameLogic.getGrid()[row][col]);

						gridPane.add(cell, col, row);
					}
				}

				gameLogic.next();
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
