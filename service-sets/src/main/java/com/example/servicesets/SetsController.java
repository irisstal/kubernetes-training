package com.example.servicesets;

import com.example.servicesets.entity.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/sets")
public class SetsController {

    private final SetsService setsService;

    @Autowired
    public SetsController(SetsService setsService) {
        this.setsService = setsService;
    }

    //Create-controller
    @PostMapping("/add")
    public ResponseEntity<Sets> save(@RequestBody Sets sets) {
        return setsService.addNewSet(sets);
    }

    //Read-mapping
    @GetMapping("/all")
    public @ResponseBody
    List<Sets> getAllSets(Model model) {
        List<Sets> setsList = setsService.getAllSets();
        model.addAttribute("setsList", setsList);
        return setsList;
    }

    @GetMapping
    @RequestMapping("/{setsId}")
    public Sets get(@PathVariable Long setsId, Model model) {
        model.addAttribute("set", setsService.getOneSet(setsId));
        return setsService.getOneSet(setsId);
    }

    //Update-mapping
    @PutMapping("/{setsId}")
    public ResponseEntity<Sets> updateSets(@PathVariable("setsId") Long setsId, @RequestBody Sets sets) {
        try {
            return ResponseEntity.ok().body(setsService.updateSets(setsId, sets));
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete-mapping
    @DeleteMapping("/{setsId}")
    public String deleteSets(@PathVariable("setsId") Long setsId) {
        setsService.deleteSet(setsId);
        String s = "Successfully removed Set" + setsId;
        return s;
    }

    @DeleteMapping("/all")
    public String deleteAllSets() {
        setsService.deleteAllSets();
        String t = "Successfully removed all DJs";
        return t;
    }
}