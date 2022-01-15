package server.gamelogic;

import java.util.ArrayList;

public class GameField {
    private int width;
    private int height;
    private GameCell[][] gameField;
    ArrayList<Player> players;

    public void initGameField(int width, int height){
        this.width = width;
        this.height = height;

        gameField = new GameCell[width][height];
    }

    public void addPlayer(int id){
        players.add(new Player(id, 1));
    }

    public void removePlayer(Player player){
        players.remove(player);
    }



}
