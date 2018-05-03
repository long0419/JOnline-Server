package com.mileworks.jetonline.controllers;

import com.google.gson.*;
import com.mileworks.jetonline.vo.ServerVo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Jebrain 获取 server 地址
 */

@Controller
@RequestMapping("online")
public class JetbrainServerController {

    OkHttpClient client = new OkHttpClient();

    @RequestMapping("/servers")
    public String getServers(ModelMap map) {
        String content = "";
        Request request = new Request.Builder()
                .url("http://xidea.online/assets/js/main-e025f78b7b.js")
                .build();

        try {
            Response response = client.newCall(request).execute();
            content = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int begin = content.indexOf("servers:[");
        int end = content.indexOf(",donate:{");
        String servers = "{" +  content.substring(begin, end) + "}" ;

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(servers).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("servers");

        List<ServerVo> list = new ArrayList<ServerVo>() ;
        for (JsonElement element : jsonArray){
            ServerVo vo = new Gson().fromJson(element , ServerVo.class) ;
            list.add(vo) ;
        }

        map.addAttribute("list", list);

        return "index";
    }

}

