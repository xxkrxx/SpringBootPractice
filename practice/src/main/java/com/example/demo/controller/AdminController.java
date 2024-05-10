
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.form.LoginForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.AdminService;

@Controller
public class AdminController {
	
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
		
		return "signup"; // "signup"というビューの名前を返す
	}
	
	// 新規登録を処理するメソッド
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute SignupForm signupForm, Model model) {
	    // フォームから受け取ったユーザー情報を使って新規登録処理を行う    
	    adminService.registerUser(signupForm.getLastName(), signupForm.getFirstName(), signupForm.getEmail(), signupForm.getPassword());
	    return "redirect:/login";
	}

	
	 // ログインフォームを表示するメソッド
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
	    LoginForm loginForm = new LoginForm();
	    loginForm.setId("");
	    loginForm.setName("");
	    model.addAttribute("loginForm", loginForm);
	    model.addAttribute("message", "管理者情報を入力してください");
	    
	    return "login";
	}
}
