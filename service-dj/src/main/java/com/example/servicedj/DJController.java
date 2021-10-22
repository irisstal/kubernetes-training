package com.example.servicedj;

import com.example.servicedj.entity.DJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/djs")
public class DJController {

    private final DJService djService;

    @Autowired
    public DJController(DJService djService) {
        this.djService = djService;
    }

    //Create-controller
    @PostMapping("/add")
    public ResponseEntity<DJ> save(@RequestBody DJ dj) {
        return djService.addNewDJ(dj);
    }

    //Read-mapping
    @GetMapping("/all")
    public @ResponseBody
    List<DJ> getAllDJs(Model model) {
        List<DJ> djList = djService.getAllDJs();
        model.addAttribute("djList", djList);
        return djList;
    }

    @GetMapping
    @RequestMapping("/{djId}")
    public ResponseEntity<DJ> get(@PathVariable Long djId) {
        Optional<DJ> oneDj = djService.getOneDj(djId);
        return oneDj
                .map(dj -> ResponseEntity.ok().body(dj))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Update-mapping
    @PutMapping("/{djId}")
    public ResponseEntity<DJ> updateDJ(@PathVariable("djId") Long djId, @RequestBody DJ dj) {
        try {
            return ResponseEntity.ok().body(djService.updateDJ(djId, dj));
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete-mapping
    @DeleteMapping("delete/{djId}")
    public String deleteDJ(@PathVariable("djId") Long djId) {
        djService.deleteDJ(djId);
        return "Successfully removed DJ" + djId;
    }

    @DeleteMapping("delete/all")
    public String deleteAllDJs() {
        djService.deleteAllDJs();
        return "Successfully removed all DJs";
    }
}