package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void registerUser(String lastName, String firstName, String email, String password) {
		// SQLクエリの作成
		String sql = "INSERT INTO user(last_name, first_name, email, passwprd) VALUES (?, ?, ?, ?)";
		// JdbcTemplateを使ってデータベースに新しいユーザー情報を挿入
		jdbcTemplate.update(sql, lastName, firstName, email, password);
	}
}