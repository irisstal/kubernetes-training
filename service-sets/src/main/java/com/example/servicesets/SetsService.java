package com.example.servicesets;

import com.example.servicesets.entity.Sets;
import com.example.servicesets.repository.SetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class SetsService {

    private final SetsRepository setsRepository;

    @Autowired
    public SetsService(SetsRepository setsRepository) {
        this.setsRepository = setsRepository;
    }

    public ResponseEntity<Sets> addNewSet(Sets sets) {
        try {
            Sets savedSets = setsRepository.save(new Sets(sets.getSetsId(), sets.getSetsName())); //Zorg voor constructor
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSets);

//            return new ResponseEntity<>(savedSets, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                    new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read-services
    public List<Sets> getAllSets() {
        return setsRepository.findAll();
    }

    public Sets getOneSet(Long setsId) {
        return setsRepository.findById(setsId).get();
    }

    //Update-service
    public Sets updateSets(@PathVariable("setsId") Long setsId, @RequestBody Sets sets) {
        Optional<Sets> setsData = setsRepository.findById(setsId);
        if (setsData.isPresent()) {
//            Sets saveSets = setsData.get();
//            BeanUtils.copyProperties(sets, saveSets, "setsId");
            return setsRepository.saveAndFlush(sets);
        } else {
            throw new IllegalStateException("Set doesn't exist.");
        }
    }

    //Delete-services
    @DeleteMapping("/{setsId}")
    public void deleteSet(@PathVariable("setsId") long setsId) {
        setsRepository.deleteById(setsId);
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllSets() {
        try {
            setsRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}