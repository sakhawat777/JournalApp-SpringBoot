package com.nexuscreator.journalApp.controller;

import com.nexuscreator.journalApp.entity.JournalEntry;
import com.nexuscreator.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping("/abc")
    // ? Wild card pattern
    public ResponseEntity<?> getAll() {
        List<JournalEntry> all = journalEntryService.getAll();
        if(all != null && !all.isEmpty() ){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{myId}")
    public  ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
//        if(journalEntry.isPresent()){
//            return  new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @GetMapping("id/{myId}")
//    public  ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
//        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
//        if(journalEntry.isPresent()){
//            return  new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//            return  new ResponseEntity<>(user.get(), HttpStatus.OK); // Multiple return for ? Wild card pattern
//        }
//        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


    @PostMapping("/create")
    public  ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry((myEntry));
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("id/{myId}")
    // ? Wild card pattern
    public  ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("id/{id}")
    // ? Wild card pattern
    public  ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id,  @RequestBody JournalEntry newEntry){
        JournalEntry journalEntry = journalEntryService.findById(id).orElse(null);
        if(journalEntry !=null){
            journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : journalEntry.getContent());
            journalEntryService.saveEntry((journalEntry));
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
