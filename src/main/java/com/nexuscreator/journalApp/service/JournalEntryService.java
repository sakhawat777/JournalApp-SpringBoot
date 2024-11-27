package com.nexuscreator.journalApp.service;

import com.nexuscreator.journalApp.entity.JournalEntry;
import com.nexuscreator.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component // convert to Bean/Object
public class JournalEntryService {
@Autowired
    private JournalEntryRepository journalEntryRepository;
public  void saveEntry (JournalEntry journalEntry){
    journalEntryRepository.save(journalEntry);
}

public List<JournalEntry> getAll(){
    return  journalEntryRepository.findAll();

}
public Optional<JournalEntry> findById(ObjectId id){
    return journalEntryRepository.findById(id);
}
public void deleteById(ObjectId id){
 journalEntryRepository.deleteById(id);
}

}
