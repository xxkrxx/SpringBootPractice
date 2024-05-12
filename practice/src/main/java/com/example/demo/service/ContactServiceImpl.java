package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    
    public void saveContact(ContactForm contactForm) {
        // ContactFormからContactエンティティへの変換
        Contact contact = new Contact();
        contact.setLastName(contactForm.getLastName());
        contact.setFirstName(contactForm.getFirstName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setZipCode(contactForm.getZipCode());
        contact.setAddress(contactForm.getAddress());
        contact.setBuildingName(contactForm.getBuildingName());
        contact.setContactType(contactForm.getContactType());
        contact.setBody(contactForm.getBody());
        
        // エンティティの保存
        contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        // データベースからすべての連絡先情報を取得する
        Iterable<Contact> contactsIterable = contactRepository.findAll();
        // 取得した連絡先情報をリストに変換する
        List<Contact> contacts = new ArrayList<>();
        contactsIterable.forEach(contacts::add);
        // 取得した連絡先情報のリストを返す
        return contacts;
    }
}
