
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Contact;
import com.example.demo.form.SigninForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ContactService contactService;
	
	// お問い合わせの一覧画面を表示するメソッド
	@GetMapping("/contacts")
	public String showContact(Model model) {
		// お問い合わせの一覧データを取得し、モデルに追加する
		List<Contact> contacts = contactService.getAllContacts();//お問い合わせデータを取得する例
		model.addAttribute("contacts", contacts);
		return "admin/contacts/admin_contacts_list";
	}
	
	@Autowired
	private AdminService adminService;
	
	// 新規登録フォームを表示するメソッド
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		SignupForm signupForm = new SignupForm();
		signupForm.setLastName("");
		signupForm.setFirstName("");
		signupForm.setEmail("");
		signupForm.setPassword("");
		model.addAttribute("signupForm", signupForm);
		model.addAttribute("message", "管理者情報を入力してください");
		
		return "signup/admin_signup"; // "signup/admin_signup"というビューの名前を返す
	}
	
	// 新規登録を処理するメソッド
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute SignupForm signupForm, Model model) {
	    // フォームから受け取ったユーザー情報を使って新規登録処理を行う    
	    adminService.registerUser(signupForm.getLastName(), signupForm.getFirstName(), signupForm.getEmail(), signupForm.getPassword());
	    return "redirect:/signin";
	}

	
	 // ログインフォームを表示するメソッド
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(Model model) {
	    SigninForm signinForm = new SigninForm();
	    signinForm.setId("");
	    signinForm.setName("");
	    model.addAttribute("signinForm", signinForm);
	    model.addAttribute("message", "管理者情報を入力してください");
	    
	    return "signin/admin_signin";// "signup/admin_signin"というビューの名前を返す
	}
}

