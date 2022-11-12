package com.spring.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.*;
import com.spring.model.user;

@Service
public class service {

	@Autowired
	private dao dao;
	
	public String loginstudent(user user,HttpServletRequest req)
	{
		System.out.println("service login student");
		return dao.loginstudent(user,req);
	}
	
	public String insert(user user)
	{
		System.out.println("service insert");
		return dao.insert(user);
	}
	
	public String loginadmin(HttpServletRequest req)
	{
		System.out.println("service login admin");
		return dao.loginadmin(req);
	}
	
	public int addbooks(user user)
	{
		System.out.println("service addbooks");
		return dao.addbooks(user);
	}
	
	public String sendmail(String email,String name)
	{
		System.out.println("service sendmail");
		return dao.sendmail(email,name);
	}
	
	public void viewAllBooks(HttpSession s)
	{
		dao.viewAllBooks(s);
	}
	
	public String issueBook(HttpServletRequest req)
	{
		return dao.issueBook(req);
	}
	
	public String viewIssuedBooks(HttpSession s)
	{
		return dao.viewIssuedBooks(s);
	}
	
	public int changePassword(HttpServletRequest req)
	{
		return dao.changePassword(req);
	}
	
	public int changeMobile(HttpServletRequest req)
	{
		return dao.changeMobile(req);
	}
	
	public int changeUserName(HttpServletRequest req)
	{
		return dao.changeUserName(req);
	}
	
	public int changeEmail(HttpServletRequest req)
	{
		return dao.changeEmail(req);
	}
}
