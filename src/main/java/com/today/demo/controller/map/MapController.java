package com.today.demo.controller.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MapController {

    @GetMapping("/map/0")
    public String map0View(){
        return "map/0";
    }
    @GetMapping("/map/1")
    public String map1View(){
        return "map/1";
    }
    @GetMapping("/map/2")
    public String map2View(){
        return "map/2";
    }

}
