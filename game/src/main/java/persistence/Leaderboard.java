package persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class Leaderboard {
    private static Map<String, Integer> board;
    private static final String LEADERBOARD_FILE_PATH = "leaderboard.json";


    public static void saveBoard() throws Exception{

        JsonObject boardObject = new JsonObject();
        JsonArray scores = new JsonArray();
        boardObject.add("scores", scores);

        for(Map.Entry<String, Integer> score : board.entrySet()){
            JsonObject scoreObject = new JsonObject();
            scoreObject.addProperty("name", score.getKey());
            scoreObject.addProperty("score", score.getValue());
            scores.add(scoreObject);
        }


        BufferedWriter bf = new BufferedWriter(new FileWriter(LEADERBOARD_FILE_PATH));
        bf.write(boardObject.toString());
        bf.close();
    }

    public static void addScore(String name, Integer score){
        board.put(name, score);
        sortBoard();

        if(board.size() > 10){
            Map.Entry<String, Integer> last = board.entrySet().iterator().next();
            while(board.entrySet().iterator().hasNext())
                last = board.entrySet().iterator().next();

            board.remove(last.getKey());
        }
    }


    public static void loadBoard(){
        try{
            File f = new File(LEADERBOARD_FILE_PATH);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String savefile = "";
            String line;
            while ((line = br.readLine()) != null)
                savefile += line;

            JsonObject boardJson = new JsonParser().parse(savefile).getAsJsonObject();


            board = new LinkedHashMap<String, Integer>();
            for(JsonElement score : boardJson.getAsJsonArray("scores")){
                JsonObject scoreObject = score.getAsJsonObject();
                board.put(scoreObject.get("name").getAsString(), scoreObject.get("score").getAsInt());
            }
            br.close();

        } catch (Exception e){
            //nincs fájl, vagy rossz
            board = new LinkedHashMap<String, Integer>();
        }

    }

    public static void sortBoard(){
        board = board.entrySet().stream().
                sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
    }

    public static void printScores(){
        board.entrySet().stream()
                .sorted(comparingByValue())
                .forEach(score -> System.out.println("Name: " + score.getKey() + ", " + "Score: " + score.getValue()));
    }
}
