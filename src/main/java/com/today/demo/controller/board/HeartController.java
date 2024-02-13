package com.today.demo.controller.board;

import com.today.demo.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/like/{boardId}")
    public ResponseEntity<?> insert(Principal principal,@PathVariable int boardId){
        heartService.insert(principal.getName(),boardId);
        return null;
    }
    @DeleteMapping("/like/{boardId}")
    public ResponseEntity<?> delete(Principal principal,@PathVariable int boardId){
        heartService.delete(principal.getName(),boardId);
        return null;
    }

}
