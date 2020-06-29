package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Builder;
import model.Player;
import model.Turn;
import utils.Coordinate;
import view.*;
import view.screens.BoardScreen;

import java.net.URL;
import java.util.List;

/**
 * This class represents the GUI controller during the real game (after the pick god phase)
 */
public class GUIBoard extends BoardScreen implements GUIController {

    @FXML GridPane keyboard;
    @FXML GridPane highlight;
    @FXML GridPane buildings;
    @FXML GridPane domes;
    @FXML GridPane builders;
    @FXML VBox gods;
    @FXML Label turnLabel;
    @FXML Button endPhase;
    @FXML Button toggleSpecialPower;
    @FXML Button resetPhase;
    @FXML Rectangle underlineSP;
    @FXML ImageView endPhaseGraphic;
    @FXML ImageView toggleSpecialPowerGraphic;
    @FXML ImageView resetPhaseGraphic;

    public static final float SQUARE_SIZE = 80;
    public static final float GOD_SIZE = 120;
    public List<String> color = List.of("green", "red", "blue");


    /**
     * GUIBoard constructor
     * @param view the viewmanager used
     * @param activePlayer the active player name
     * @param players the list of players
     * @param preHighCoords the coordinates of highlighted squares
     */
    public GUIBoard(ViewManager view, String activePlayer, List<Player> players, List<Coordinate> preHighCoords) {
        super(view, activePlayer, players, preHighCoords);
    }

    /**
     * initializes the GUIBoard
     */
    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);

        updateTurnLabel();

        int colorNum = 0;
        //Creating god images and player names
        for(Player p : getPlayers()){
            HBox hBox = new HBox();                             //HBox will contain the image of god + name of player
            hBox.setSpacing(10);
            StackPane stackPane = new StackPane();
            stackPane.setMinHeight(GOD_SIZE);
            stackPane.setMaxHeight(GOD_SIZE*3/2);


            ImageView godImage = new ImageView();
            Image img = AssetLoader.getGodAssetsBundle(p.getGod().getName()).loadGodFigureImage();
            godImage.setImage(img);
            godImage.setPreserveRatio(true);
            godImage.setFitHeight(GOD_SIZE);

            Button description = new Button();
            description.setPrefSize(GOD_SIZE*4/3, GOD_SIZE*4/3);
            description.setOpacity(0);
            Tooltip tooltip = new Tooltip();
            tooltip.setText(p.getGod().getName().toUpperCase() + ":\n" + AssetLoader.getGodAssetsBundle(p.getGod().getName()).getDescription());
            description.setTooltip(tooltip);

            ImageView podium = new ImageView(String.valueOf(getClass().getResource("/assets/podium.png")));
            podium.setPreserveRatio(true);
            podium.setFitHeight(GOD_SIZE/2);

            stackPane.getChildren().addAll(podium, godImage, description);
            StackPane.setAlignment(podium, Pos.BOTTOM_CENTER);
            StackPane.setAlignment(godImage, Pos.TOP_CENTER);

            VBox nameColor = new VBox();
            Label name = new Label(p.getName());
            name.setStyle("-fx-font-family: 'Arial Black'; -fx-text-fill: #ffffff");
            ImageView colorRectangle = new ImageView(String.valueOf(getClass().getResource("/assets/"+color.get(colorNum)+"_rectangle.png")));
            colorRectangle.setPreserveRatio(true);
            colorRectangle.setFitHeight(50);
            colorNum++;

            nameColor.getChildren().addAll(name, colorRectangle);
            hBox.getChildren().addAll(stackPane, nameColor);
            gods.getChildren().add(hBox);
        }


        Button button;
        ImageView mark, building, dome, builder;
        //Creating layers of the board
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {

                //keyboard layer
                button = new Button();
                button.setPrefSize(300, 300);
                button.setOpacity(0);
                keyboard.add(button, j, i, 1, 1);

                //highlight layer
                mark = new ImageView();
                mark.setImage(new Image(String.valueOf(getClass().getResource("/assets/highlight.gif"))));
                mark.setPreserveRatio(true);
                mark.setFitHeight(SQUARE_SIZE);
                mark.setVisible(false);
                highlight.add(mark, j, i, 1, 1);

                GridPane.setHalignment(mark, HPos.CENTER);
                GridPane.setValignment(mark, VPos.CENTER);

                //buildings layer
                building = new ImageView();
                building.setPreserveRatio(true);
                building.setFitHeight(SQUARE_SIZE);
                buildings.add(building, j, i, 1, 1);

                //domes layer
                dome = new ImageView();
                dome.setImage(new Image(String.valueOf(getClass().getResource("/assets/dome.png"))));
                dome.setPreserveRatio(true);
                dome.setFitHeight(SQUARE_SIZE/2);
                dome.setVisible(false);
                domes.add(dome, j, i, 1, 1);

                //builders layer
                builder = new ImageView();
                builder.setPreserveRatio(true);
                builder.setFitHeight(SQUARE_SIZE/2);
                builders.add(builder, j, i, 1, 1);

                //Setting board listeners
                int finalI = i;
                int finalJ = j;
                button.setOnMouseClicked((event) -> {
                    try {
                        selectSquare(new Coordinate(finalI, finalJ));
                        updateButtons();
                        updateBuilders();
                        highlight(getHighlightedCoordinates());
                    } catch (IllegalActionException | IllegalValueException | ActivityNotAllowedException ignored) {}
                });

            }
        }
        highlight(getHighlightedCoordinates());
    }


    /**
     * updates the label that indicates the active player of this turn
     */
    public void updateTurnLabel(){
        if(getThisPlayerName().equals(getActivePlayer())){
            turnLabel.setText("IT'S YOUR TURN!");
        } else {
            turnLabel.setText("IT'S " + getActivePlayer() +"'S TURN!");
        }
    }


    /**
     * @param allowedTiles the list of coordinates to highlight
     */
    public void highlight(List<Coordinate> allowedTiles){
        //Clear previous highlight
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                GUI.getNodeFromGridPane(highlight, j, i).setVisible(false);
            }
        }
        if(isActiveScreen()) {
            for (Coordinate c : allowedTiles) {
                GUI.getNodeFromGridPane(highlight, c.getY(), c.getX()).setVisible(true);
            }
        }
    }


    /**
     * brings the turn to the end phase
     */
    public void endPhaseListener() {
        try {
            super.endPhase();
            updateButtons();
            highlight(getHighlightedCoordinates());
        } catch (IllegalActionException ignored){}
    }

    /**
     * allows the player to rechoose the builder at the beginning of his turn
     */
    public void resetPhaseListener(){
        try {
            super.resetPhase();
            updateButtons();
            highlight(getHighlightedCoordinates());
        } catch (IllegalActionException ignored){}
    }

    /**
     * allows the player to use the special power of his god
     */
    public void toggleSpecialPowerListener(){
        try {
            super.toggleSpecialPower();
            updateButtons();
            highlight(getHighlightedCoordinates());
            if(underlineSP.getOpacity() == 0){
                underlineSP.setOpacity(1);
            } else {
                underlineSP.setOpacity(0);
            }
        } catch (IllegalActionException ignored){}
    }


    /**
     * updates the buttons (making them clickable only in certain moments)
     */
    public void updateButtons(){
        //Update special power button
        if(specialPowerAvailable()){
            toggleSpecialPower.setDisable(false);
            toggleSpecialPowerGraphic.setOpacity(1);
        } else {
            toggleSpecialPower.setDisable(true);
            toggleSpecialPowerGraphic.setOpacity(0.5);
            underlineSP.setOpacity(0);
        }

        //Update end phase button
        if(endPhaseAvailable()){
            endPhase.setDisable(false);
            endPhaseGraphic.setOpacity(1);
        } else {
            endPhase.setDisable(true);
            endPhaseGraphic.setOpacity(0.5);
        }

        //Update reset phase
        if(getSelectedBuilder() != null && resetPhaseAvailable()){
            resetPhase.setDisable(false);
            resetPhaseGraphic.setOpacity(1);
        } else {
            resetPhase.setDisable(true);
            resetPhaseGraphic.setOpacity(0.5);
        }

    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower){
        super.receiveAllowedSquares(builder, allowedTiles, specialPower);
    }

    @Override
    public void receiveBoard(Board board) {
        super.receiveBoard(board);

        Board b = getBoard();
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                int level = b.squareAt(i,j).getBuildLevel();
                boolean dome = b.squareAt(i,j).isDomed();

                if(level == 0){
                    ((ImageView) GUI.getNodeFromGridPane(buildings, j, i)).setImage(null);
                } else {
                    ((ImageView) GUI.getNodeFromGridPane(buildings, j, i)).setImage(new Image(String.valueOf(getClass().getResource("/assets/build_"+level+".png"))));
                }

                GUI.getNodeFromGridPane(domes, j, i).setVisible(dome);
            }
        }

    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders){
        super.receiveBuildersPositions(builders);
        Platform.runLater(this::updateBuilders);
        Platform.runLater(this::updateTurnLabel);
    }


    /**
     * updates the builders position on the board
     */
    public void updateBuilders(){
        //Clear board
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                ((ImageView) GUI.getNodeFromGridPane(this.builders, j, i)).setImage(null);
            }
        }

        for(Builder b : getCurrBuilders()){
            Coordinate newC = b.getSquare().getCoordinate();
            URL url = getClass().getResource("/assets/"+ color.get(view.getPlayersNames().indexOf(b.getOwner().getName())) + "_pawn.png");
            Image img = new Image(String.valueOf(url));
            ((ImageView) GUI.getNodeFromGridPane(this.builders, newC.getY(), newC.getX())).setImage(img);
        }
    }


    @Override
    public void receiveTurnState(Turn.State state, Player player) {
        super.receiveTurnState(state, player);
        //Platform.runLater(this::updateTurnLabel);
    }

    @Override
    public void receiveUpdateDone() {
        super.receiveUpdateDone();
        Platform.runLater(this::updateTurnLabel);

        highlight(getHighlightedCoordinates());
        Platform.runLater(this::updateBuilders);
        Platform.runLater(this::updateButtons);
    }







    @Override
    public void onScreenOpen() {
        Platform.runLater(() ->GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public String getSceneName() {
        return "board";
    }

}
