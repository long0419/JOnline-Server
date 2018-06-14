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
public class JetbrainServerController {

    OkHttpClient client = new OkHttpClient();

    @RequestMapping("/")
    public String getServers(ModelMap map) {
        String content = "";

        Request request = new Request.Builder()
                .url("http://xidea.online/servers.html")
                .build();

        try {
            Response response = client.newCall(request).execute();
            content = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int begin = content.indexOf("assets/js/main-");
        int end = content.indexOf("\"></script><script>__.R.servers();");
        String mainJs = content.substring(begin, end) ;

        request = new Request.Builder()
                .url("http://xidea.online/" + mainJs)
                .build();

        try {
            Response response = client.newCall(request).execute();
            content = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        begin = content.indexOf("servers:[");
        end = content.indexOf(",donate:{");
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

