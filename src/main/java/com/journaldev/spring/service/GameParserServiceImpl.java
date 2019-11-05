package com.journaldev.spring.service;

import com.journaldev.spring.model.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

@Service
public class GameParserServiceImpl implements GameParserService {
    private Game game;
    private List<ImageResource> imageResources;

    public GameParserServiceImpl() {
    }

    @Override
    public void parse(MultipartFile gameFile, MultipartFile[] images) throws IOException, SQLException, ParseException {
        InputStream inputStream = gameFile.getInputStream();
        inputStream.skip(3);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        this.parseJSON(jsonObject);
        this.parseImages(images);
    }

    @Override
    public void parseJSON(JSONObject jsonObject) {
        this.game = new Game();
        game.setName((String) jsonObject.get("Name"));
        game.setPlayersCount(Math.toIntExact((Long) jsonObject.get("PlayersCount")));
        int startSceneId1 = Math.toIntExact((Long) jsonObject.get("StartSceneId1"));
        int startSceneId2 = Math.toIntExact((Long) jsonObject.get("StartSceneId1"));

        this.imageResources = new LinkedList<>();
        List<Scene> scenes1 = new LinkedList<>();

        Set<Sprite> sprites1 = new HashSet<>();
        Set<Choice> choices1 = new HashSet<>();
        Set<MatrixVariant> choiceMatrix1 = new HashSet<>();

        JSONArray jsonResources = (JSONArray) jsonObject.get("ImageResources");
        for (JSONObject resource : (Iterable<JSONObject>) jsonResources) {
            ImageResource imageResource = new ImageResource();
            imageResource.setJsonId(Math.toIntExact((Long) resource.get("Id")));
            imageResource.setPath((String) resource.get("Path"));
            imageResources.add(imageResource);
        }

        JSONArray scenes = (JSONArray) jsonObject.get("Scenes");
        for (JSONObject next : (Iterable<JSONObject>) scenes) {
            Scene scene = new Scene();
            scene.setJsonId(Math.toIntExact((Long) next.get("Id")));
            if (scene.getJsonId() == startSceneId1) {
                game.setStartScene1(scene);
            }
            if (scene.getJsonId() == startSceneId2) {
                game.setStartScene2(scene);
            }
            scene.setText((String) next.get("Text"));
            scene.setType((String) next.get("Type"));

            JSONArray sprites = (JSONArray) next.get("Sprites");
            HashSet<Sprite> sceneSprites = new HashSet<>();
            for (JSONObject next1 : (Iterable<JSONObject>) sprites) {
                Sprite sprite = new Sprite();
                int resourceId = Math.toIntExact((Long) next1.get("ResourceId"));
                for (ImageResource im : imageResources) {
                    if (resourceId == im.getJsonId()) {
                        sprite.setImageResource(im);
                    }
                }
                sprite.setScene(scene);
                sprite.setPositionX(Math.toIntExact((Long) next1.get("PositionX")));
                sprite.setPositionY(Math.toIntExact((Long) next1.get("PositionY")));
                sprites1.add(sprite);
                sceneSprites.add(sprite);
            }
            scene.setSprites(sceneSprites);

            if (scene.getType().equals("Normal")) {
                JSONArray choices = (JSONArray) next.get("Choices");
                for (JSONObject next1 : (Iterable<JSONObject>) choices) {
                    Choice choice = new Choice();
                    choice.setScene(scene);
                    //choice.setJsonId(Math.toIntExact((Long) next1.get("Id")));
                    choice.setCaption((String) next1.get("Caption"));
                    choice.setMatrixNum(Math.toIntExact((Long) next1.get("MatrixNum")));
                    choices1.add(choice);
                }
                scene.setChoices(choices1);

                JSONArray choiceMatrix = (JSONArray) next.get("ChoiceMatrix");
                for (JSONObject next1 : (Iterable<JSONObject>) choiceMatrix) {
                    MatrixVariant matrixVariant = new MatrixVariant();
                    matrixVariant.setScene(scene);
                    matrixVariant.setMatrixPosition((String) next1.get("MatrixPosition"));
                    matrixVariant.setValue(Math.toIntExact((Long) next1.get("Value")));
                    matrixVariant.setNextSceneId1(Math.toIntExact((Long) next1.get("NextSceneId1")));
                    matrixVariant.setNextSceneId2(Math.toIntExact((Long) next1.get("NextSceneId2")));
                    choiceMatrix1.add(matrixVariant);
                }
                scene.setMatrixVariantList(choiceMatrix1);
            }
            scenes1.add(scene);
            int backgroundId = Math.toIntExact((Long) next.get("BackgroundId"));
            for (ImageResource im : imageResources) {
                if (backgroundId == im.getJsonId()) {
                    scene.setBackground(im);
                }
            }
        }
        for (MatrixVariant m: choiceMatrix1) {
            for (Scene s: scenes1) {
                if(s.getJsonId() == m.getNextSceneId1()){
                    m.setNextScene1(s);
                }
                if(s.getJsonId() == m.getNextSceneId2()){
                    m.setNextScene2(s);
                }
            }
        }
    }

    @Override
    public void parseImages(MultipartFile[] images) throws SQLException, IOException {

        Map<String, byte[]> imgs = new HashMap<>();
        for (MultipartFile image: images) {
            byte[] imageBytes = image.getBytes();
            String name = image.getOriginalFilename();
            imgs.put(name, imageBytes);
        }

        for (ImageResource resource:imageResources) {
            byte[] image = imgs.get(resource.getPath());
            Blob im = new SerialBlob(image);
            resource.setImage(im);
        }
    }

    public Game getGame() {
        return game;
    }
}
