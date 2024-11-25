package com.nexuscreator.journalApp.controller;

import com.nexuscreator.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
    @GetMapping("/abc")

    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }
    @GetMapping("id/{myId}")
    public  JournalEntry getJournalEntryById(@PathVariable Long myId){
    return journalEntries.get(myId);
    }
    @RequestMapping("/create")
    public  boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;

    }
    @DeleteMapping("id/{myId}")
    public  JournalEntry deleteJournalEntryById(@PathVariable Long myId){
        return journalEntries.remove(myId);
    }
    @PutMapping("id/{id}")
    public  JournalEntry updateJournalEntryById(@PathVariable Long id,  @RequestBody JournalEntry myEntry){
        // Ensure the provided ID matches the one in the path variable
        myEntry.setId(id);
        return  journalEntries.put(id, myEntry);
    }
}
