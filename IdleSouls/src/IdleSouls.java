//import java.util.Timer;
//import java.util.TimerTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sun.prism.paint.Color;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class IdleSouls extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) throws FileNotFoundException {
		
		//Data
		Label soulsCount = new Label("");
		SoulManager sl = new SoulManager(soulsCount);
		Agency startAgency = new Agency("Shoe Store");
		
		File soulf = new File("Soul.png");
		FileInputStream fis = new FileInputStream(soulf);
		Image soulpng = new Image(fis);
		ImageView soulImg = new ImageView(soulpng);
		Label agencyTitle = new Label(startAgency.name);
		BorderPane agencyDisplay = new BorderPane(soulImg);
		//agencyDisplay.getCenter().setStyle("-fx-background-color: BLACK");
		agencyDisplay.setTop(agencyTitle);
		
		BorderPane main = new BorderPane(agencyDisplay);
		
		HBox upgradeMenu = new HBox();
		
		Label gameTitle = new Label("Idol Soles");
		ImageView scytheImg = new ImageView(new Image("file:scytheplaceholder.jpg"));
		scytheImg.setFitWidth(50);
		scytheImg.setPreserveRatio(true);
		
		Button agencyButton = new Button("Manage Agencies");
		Button reaperButton = new Button("Hire a Reaper");
		Button cultFollowersButton = new Button("Buy More Cult Followers");
		
		upgradeMenu.getChildren().addAll(gameTitle, scytheImg, agencyButton, reaperButton, cultFollowersButton);
		
		Label day = new Label("Current Day: ");
		
		Label soulsText = new Label("Souls: ");
		
		HBox soulsDisplay = new HBox(soulsText, soulsCount);
		
		main.setBottom(soulsDisplay);
		
		main.setTop(upgradeMenu);
		
		Scene s = new Scene(main, 1000, 800);
		
		
		//Cult Follower Window
		GridPane cultFollowerShop = new GridPane();
		Button internBtn = new Button("Unpaid Intern");
//		internBtn.setGraphic();
		Label internLbl = new Label("Owned: ");
		Label internCount = new Label("");
		
		cultFollowerShop.add(internBtn, 0, 0);
		cultFollowerShop.add(internLbl, 1, 0);
		cultFollowerShop.add(internCount, 2, 0);
		
		
		internBtn.setOnAction(event -> {
			if (sl.souls >= CultFollowers.internCost) {
				sl.souls -= CultFollowers.internCost;
				startAgency.internCount++;
				sl.soulsPerSecond += CultFollowers.internBoost;
				sl.updateFrequency();
				sl.restartAddSoulsTimer();
			}
		});
		
		
		cultFollowersButton.setOnAction(event -> {
			main.setRight(cultFollowerShop);
		});
		
		
		//Reaper shop window
		GridPane reaperShop = new GridPane();
		Label bobTitle = new Label("Bob");
		Label bobDescription = new Label("Bob likes interns.");
		VBox bobBox = new VBox(bobTitle, bobDescription);
		Button bobHire = new Button("Hire");
		
		//Reaper View window
		GridPane reaperView = new GridPane();
		Label reaperTitle = new Label("Bob");
		Label reaperDescription = new Label("Bob likes interns");
		VBox reaperText = new VBox(reaperTitle, reaperDescription);
		Button fireReaper = new Button("Fire");
		Image bobImg = new Image("file:images/unpaidintern.jpg");
		ImageView reaperImg = new ImageView(bobImg);
		reaperView.add(reaperText, 0, 0);
		reaperView.add(fireReaper, 1, 0);
		reaperView.add(reaperImg, 0, 1, 2, 3);
		
		bobHire.setOnAction(event -> {
			reaperButton.setText("View Reaper");
			main.setRight(reaperView);
		});
		
		reaperShop.add(bobBox, 0, 0);
		reaperShop.add(bobHire, 1, 0);
		
		reaperButton.setOnAction(event -> {
			main.setRight(reaperShop);
		});
		
		stage.setScene(s);
		stage.show();
		
		sl.startAddSoulsTimer();
		sl.startUpdateLabelTimer();
		
		soulImg.setOnMouseClicked(event -> {
			sl.souls++;
			soulsCount.setText(String.format("%.0f", sl.souls));
		});
		
		stage.setOnCloseRequest(event -> {
			sl.cancelAddSoulsTimer();
			sl.cancelUpdateLabelTimer();
		});
	}
}
