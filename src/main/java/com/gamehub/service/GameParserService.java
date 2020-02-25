package com.gamehub.service;

import com.gamehub.entity.*;
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
public class GameParserService {
    private GameEntity game;
    private List<ImageResourceEntity> imageResources;

    public GameParserService() {
    }

    public void parse(MultipartFile gameFile, MultipartFile[] images) throws IOException, SQLException, ParseException {
        InputStream inputStream = gameFile.getInputStream();
        //inputStream.skip(3);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        this.parseJSON(jsonObject);
        this.parseImages(images);
    }

    public void parseJSON(JSONObject jsonObject) {
        this.game = new GameEntity();
        game.setName((String) jsonObject.get("Name"));
        game.setPlayersCount(Math.toIntExact((Long) jsonObject.get("PlayersCount")));
        int startSceneId1 = Math.toIntExact((Long) jsonObject.get("StartSceneId1"));
        int startSceneId2 = Math.toIntExact((Long) jsonObject.get("StartSceneId2"));
        game.setColorTheme((String) jsonObject.get("ColorTheme"));

        this.imageResources = new LinkedList<>();
        List<SceneEntity> scenes1 = new LinkedList<>();

        Set<SpriteEntity> sprites1 = new HashSet<>();
        Set<ChoiceEntity> choices1 = new HashSet<>();
        HashSet<RoleEntity> roles = new HashSet<>();
        Set<MatrixVariantEntity> choiceMatrix1 = new HashSet<>();

        JSONArray jsonResources = (JSONArray) jsonObject.get("ImageResources");
        for (JSONObject resource : (Iterable<JSONObject>) jsonResources) {
            ImageResourceEntity imageResource = new ImageResourceEntity();
            imageResource.setJsonId(Math.toIntExact((Long) resource.get("Id")));
            imageResource.setPath((String) resource.get("Path"));
            imageResources.add(imageResource);
        }

        JSONArray jsonRoles = (JSONArray) jsonObject.get("Roles");
        for (JSONObject jsonRole : (Iterable<JSONObject>) jsonRoles) {
            RoleEntity role = new RoleEntity();
            role.setGame(game);
            role.setName((String) jsonRole.get("Name"));
            roles.add(role);
        }
        game.setRoles(roles);

        JSONArray scenes = (JSONArray) jsonObject.get("Scenes");
        for (JSONObject next : (Iterable<JSONObject>) scenes) {
            SceneEntity scene = new SceneEntity();
            scene.setJsonId(Math.toIntExact((Long) next.get("Id")));
            if (scene.getJsonId() == startSceneId1) {
                game.setStartScene1(scene);
            }
            if (scene.getJsonId() == startSceneId2) {
                game.setStartScene2(scene);
            }
            scene.setSpeaker((String) next.get("Speaker"));
            scene.setText((String) next.get("Text"));
            scene.setType((String) next.get("Type"));

            JSONArray sprites = (JSONArray) next.get("Sprites");
            HashSet<SpriteEntity> sceneSprites = new HashSet<>();
            for (JSONObject next1 : (Iterable<JSONObject>) sprites) {
                SpriteEntity sprite = new SpriteEntity();
                int resourceId = Math.toIntExact((Long) next1.get("ResourceId"));
                for (ImageResourceEntity im : imageResources) {
                    if (resourceId == im.getJsonId()) {
                        sprite.setImageResource(im);
                    }
                }
                sprite.setScene(scene);
                sprites1.add(sprite);
                sceneSprites.add(sprite);
            }
            scene.setSprites(sceneSprites);
            if(scene.getType().equals("Normal")){
                scene.setNextSceneJsonId(Math.toIntExact((Long) next.get("NextSceneId")));
            }

            if (scene.getType().equals("Quest")) {
                JSONArray choices = (JSONArray) next.get("Choices");
                for (JSONObject next1 : (Iterable<JSONObject>) choices) {
                    ChoiceEntity choice = new ChoiceEntity();
                    //choice.setScene(scene);
                    //choice.setJsonId(Math.toIntExact((Long) next1.get("Id")));
                    choice.setCaption((String) next1.get("Caption"));
                    choice.setMatrixNum(Math.toIntExact((Long) next1.get("MatrixNum")));
                    choices1.add(choice);
                }
                scene.setChoices(choices1);

                JSONArray choiceMatrix = (JSONArray) next.get("ChoiceMatrix");
                for (JSONObject next1 : (Iterable<JSONObject>) choiceMatrix) {
                    MatrixVariantEntity matrixVariant = new MatrixVariantEntity();
                    matrixVariant.setScene(scene);
                    matrixVariant.setMatrixPosition((String) next1.get("MatrixPosition"));
                    matrixVariant.setValue(Math.toIntExact((Long) next1.get("Value")));
                    matrixVariant.setNextSceneId1(Math.toIntExact((Long) next1.get("NextSceneId1")));
                    matrixVariant.setNextSceneId2(Math.toIntExact((Long) next1.get("NextSceneId2")));
                    choiceMatrix1.add(matrixVariant);
                }
                scene.setMatrixVariantList(choiceMatrix1);
            }
            int backgroundId = Math.toIntExact((Long) next.get("BackgroundId"));
            for (ImageResourceEntity im : imageResources) {
                if (backgroundId == im.getJsonId()) {
                    scene.setBackground(im);
                }
            }
            scenes1.add(scene);
        }
        for (MatrixVariantEntity m: choiceMatrix1) {
            for (SceneEntity s: scenes1) {
                if(s.getJsonId() == m.getNextSceneId1()){
                    m.setNextScene1(s);
                }
                if(s.getJsonId() == m.getNextSceneId2()){
                    m.setNextScene2(s);
                }
            }
        }
        for (SceneEntity s: scenes1) {
            if (s.getType().equals("Normal")) {
                for (SceneEntity s1 : scenes1) {
                    if (s1.getJsonId() == s.getNextSceneJsonId()) {
                        s.setNextScene(s1);
                    }
                }
            }
        }
    }

    public void parseImages(MultipartFile[] images) throws SQLException, IOException {

        Map<String, byte[]> imgs = new HashMap<>();
        for (MultipartFile image: images) {
            byte[] imageBytes = image.getBytes();
            String name = image.getOriginalFilename();
            imgs.put(name, imageBytes);
        }

        for (ImageResourceEntity resource:imageResources) {
            byte[] image = imgs.get(resource.getPath());
            Blob im = new SerialBlob(image);
            resource.setImage(im);
        }
    }

    public GameEntity getGame() {
        return game;
    }
}
