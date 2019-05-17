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
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GameController implements Initializable {
	
	private double chance = 0.3;
	
	private boolean isRunning = false;
	
	private int rowNum;
	private int colNum;
	
	private double rectangleSize;
	
	private GameLogic gameLogic;
	
	private Timeline timeline;

	@FXML
	private GridPane gridPane;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Slider gridSize;

	@FXML
	private void handleStartButton(ActionEvent event) {
		if (isRunning) {
			stop();
		}
		else {
			start();
		}
	}

	@FXML
	private void handleResetButton(ActionEvent event) {
		stop();
		gameLogic.randomPopulation(chance);
		regenerateGrid();
	}

	@FXML
	private void handleNextButton(ActionEvent event) {
		stop();
		gameLogic.next();
		regenerateGrid();
	}
	
	private void start() {
		isRunning = true;
		
		timeline.play();
		startButton.setText("Stop");
	}
	private void stop() {
		isRunning = false;
		
		timeline.stop();
		startButton.setText("Start");
	}

	private void handleSlider() {
		gridSize.valueProperty().addListener(e -> {
			if(gridSize.getValue() % 3 == 0) {
				timeline.stop();
				// will use n*n grid matrix for now
				colNum = rowNum = (int) gridSize.getValue();
				
				rectangleSize = gridPane.getPrefWidth() / (double) rowNum;
				
				gameLogic = new GameLogic(rowNum, colNum);
				gameLogic.randomPopulation(chance);
				
				regenerateGrid();
			}
		});
	}
	
	private void regenerateGrid() {
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
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		handleSlider();
		
		// will use n*n grid matrix for now
		colNum = rowNum = (int) gridSize.getValue();

		rectangleSize = gridPane.getPrefWidth() / (double) rowNum;

		gameLogic = new GameLogic(rowNum, colNum);
		gameLogic.randomPopulation(chance);

		double duration = 1;
		timeline = new Timeline(new KeyFrame(Duration.seconds(duration), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// for every duration generate new grid and ask gameLogic to generate next population
				regenerateGrid();

				gameLogic.next();
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
	}
}
