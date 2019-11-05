package com.journaldev.spring.service;

import com.journaldev.spring.model.Game;
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface GameParserService {

    void parse(MultipartFile gameFile, MultipartFile[] images) throws IOException, SQLException, ParseException;
    void parseJSON(JSONObject jsonObject);
    void parseImages(MultipartFile[] images) throws SQLException, IOException;

    Game getGame();
}
