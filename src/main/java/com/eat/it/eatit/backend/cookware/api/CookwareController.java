package com.eat.it.eatit.backend.cookware.api;

import com.eat.it.eatit.backend.cookware.data.CookwareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cookware")
public class CookwareController {

    CookwareHandler cookwareHandler;

    @Autowired
    public CookwareController(CookwareHandler cookwareHandler) {
        this.cookwareHandler = cookwareHandler;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public CookwareDTO getCookwareById(@PathVariable Long id) {
        return cookwareHandler.getCookwareById(id);
    }

    @GetMapping("/get_all")
    public List<CookwareDTO> getAllCookwares() {
        return cookwareHandler.getAllCookwares();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<CookwareDTO> addNewCookware(@RequestBody CookwareDTO cookwareDTO) {
        return cookwareHandler.addNewCookware(cookwareDTO);
    }
}
