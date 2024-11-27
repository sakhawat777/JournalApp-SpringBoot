package com.nexuscreator.journalApp.controller;

import com.nexuscreator.journalApp.entity.JournalEntry;
import com.nexuscreator.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping("/abc")

    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }
    @GetMapping("id/{myId}")
    public  JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return  journalEntryService.findById(myId).orElse(null);
    }
    @PostMapping("/create")
    public  JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry((myEntry));
        return myEntry;

    }
    @DeleteMapping("id/{myId}")
    public  boolean deleteJournalEntryById(@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId);
        return true;
    }
    @PutMapping("id/{id}")
    public  JournalEntry updateJournalEntryById(@PathVariable ObjectId id,  @RequestBody JournalEntry newEntry){
        JournalEntry journalEntry = journalEntryService.findById(id).orElse(null);
        if(journalEntry !=null){
            journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : journalEntry.getContent());
        }
        journalEntryService.saveEntry((journalEntry));
        return journalEntry;
    }
}
