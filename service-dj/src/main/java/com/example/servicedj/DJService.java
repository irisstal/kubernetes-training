package com.example.servicedj;

import com.example.servicedj.entity.DJ;
import com.example.servicedj.repository.DJRepository;
import org.springframework.beans.BeanUtils;
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
public class DJService {

    private final DJRepository djRepository;

    @Autowired
    public DJService(DJRepository djRepository) {
        this.djRepository = djRepository;
    }

    //Create-services
    public ResponseEntity<DJ> addNewDJ(@RequestBody DJ dj) {
        try {
            DJ _dj = djRepository.save(new DJ(dj.getDjId(), dj.getName(), dj.getAge(), dj.getStyle())); //TODO waarom hier false?
            return new ResponseEntity<>(_dj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read-services
    public List<DJ> getAllDJs() {
        return djRepository.findAll();
    }

    public Optional<DJ> getOneDj(Long djId) {

        return djRepository.findById(djId);
    }

    //Update-service
    public DJ updateDJ(@PathVariable("djId") long djId, @RequestBody DJ dj) {
        Optional<DJ> djData = djRepository.findById(djId);
        if (djData.isPresent()) {
            DJ saveDj = djData.get();
            BeanUtils.copyProperties(dj, saveDj, "djId");
            return djRepository.saveAndFlush(saveDj);
        } else {
            throw new IllegalStateException("Dj doesn't exist.");
        }
    }

    //Delete-services
    @DeleteMapping("/{djId}")
    public void deleteDJ(@PathVariable("djId") long djId) {
        djRepository.deleteById(djId);
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllDJs() {
        try {
            djRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

