
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;

    // お問い合わせフォームを表示するメソッド
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactForm", new ContactForm());

        return "contact";// contact.htmlを返す
    }
    
    // お問い合わせを受け取るメソッド
    @PostMapping("/contact")
    public String contact(@Validated @ModelAttribute("contactForm") ContactForm contactForm, BindingResult errorResult, HttpServletRequest request) {
        if (errorResult.hasErrors()) {
          return "contact";
        }
        
        // セッションにお問い合わせフォームオブジェクトを追加
        HttpSession session = request.getSession();
        session.setAttribute("contactForm", contactForm);

        return "redirect:/contact/confirm";// confirmページにリダイレクト
    }
    
    // お問い合わせ内容を確認するメソッド
    @GetMapping("/contact/confirm")
    public String confirm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        // セッションからお問い合わせフォームオブジェクトを取得し、モデルに追加
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
        model.addAttribute("contactForm", contactForm);
        return "confirmation"; // confirmation.htmlを返す
    }
    
    // お問い合わせを登録するメソッド
    @PostMapping("/contact/register")
    public String register(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
        
        // お問い合わせ内容を登録
        contactService.saveContact(contactForm);

        return "redirect:/contact/complete";// completeページにリダイレクト
    }
    
    // お問い合わせ完了画面を表示するメソッド
    @GetMapping("/contact/complete")
    public String complete(Model model, HttpServletRequest request) {
    	
    	// セッションがない場合は、お問い合わせ画面にリダイレクト
        if (request.getSession(false) == null) {
          return "redirect:/contact";
        }

        HttpSession session = request.getSession();
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
        model.addAttribute("contactForm", contactForm);

        session.invalidate();

        return "completion";// completion.htmlを返す
      }
}