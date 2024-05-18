

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.form.SigninForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private AdminService adminService;

    // お問い合わせの一覧画面を表示するメソッド
    @GetMapping("/contacts")
    public String showContact(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "admin/contacts/admin_contacts_list";
    }

    // 特定のIDのコンタクト詳細を表示するメソッド
    @GetMapping("/contacts/{id}")
    public String showContactDetails(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        model.addAttribute("contact", contact);
        return "admin/contacts/admin_contact_details";
    }

  //お問い合わせ編集画面を表示するメソッド
    @GetMapping("/contacts/{id}/edit")
    public String showeditContactForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        if (contact == null) {
            return "redirect:/admin/contacts";
        }
        ContactForm contactForm = new ContactForm();
        contactForm.setId(contact.getId());
        contactForm.setLastName(contact.getLastName());
        contactForm.setFirstName(contact.getFirstName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhone(contact.getPhone());
        contactForm.setZipCode(contact.getZipCode());
        contactForm.setAddress(contact.getAddress());
        contactForm.setBuildingName(contact.getBuildingName());
        contactForm.setContactType(contact.getContactType());
        contactForm.setBody(contact.getBody());
        
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("id", id);
        return "admin/contacts/admin_contact_edit";
    }

    // お問い合わせの編集処理を行うメソッド
    @PostMapping("/contacts/{id}/edit")
    public String editContact(@PathVariable Long id, @ModelAttribute ContactForm contactForm) {
        contactService.updateContact(id, contactForm);
        return "redirect:/admin/contacts";
    }

    // お問い合わせの削除処理を行うメソッド
    @PostMapping("/contacts/{id}/delete")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "redirect:/admin/contacts";
    }

    // ログアウト処理を行うメソッド
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/admin/signin";
    }

    // 新規登録フォームを表示するメソッド
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        SignupForm signupForm = new SignupForm();
        model.addAttribute("signupForm", signupForm);
        return "signup/admin_signup";
    }

    // 新規登録を処理するメソッド
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute SignupForm signupForm, Model model) {
        adminService.registerUser(signupForm.getLastName(), signupForm.getFirstName(), signupForm.getEmail(), signupForm.getPassword());
        return "redirect:/admin/signin";
    }

    // ログインフォームを表示するメソッド
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model) {
        SigninForm signinForm = new SigninForm();
        model.addAttribute("signinForm", signinForm);
        return "signin/admin_signin";
    }

    // ログイン処理を行うメソッド
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String login(@ModelAttribute SigninForm signinForm, Model model) {
        boolean loginSuccess = adminService.signin(signinForm.getEmail(), signinForm.getPassword());
        if (loginSuccess) {
            return "redirect:/admin/contacts";
        } else {
            model.addAttribute("message", "ログインに失敗しました。管理者情報を確認してください。");
            return "signin/admin_signin";
        }
    }
}
