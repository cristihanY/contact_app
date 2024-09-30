package com.example.dbapp.service;

import java.util.List;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.model.entity.Contact;
import com.example.dbapp.repository.dao.ContactDao;

public class ContactService {

    private final ContactDao contactDao;

    public ContactService(AppDatabase database) {
        this.contactDao = database.contactDao();;
    }

    public Contact addContact(Contact contact) {
        return contactDao.persist(contact);
    }

    public void updateContact(Integer id, String data) {
        Contact contact = getContactById(id);
        contact.setName(data);
        contactDao.edit(contact);
    }

    public void removeContact(Integer id) {
        contactDao.deleteById(id);
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAll();
    }

    public Contact getContactById(int id) {
        return contactDao.getById(id);
    }

}
