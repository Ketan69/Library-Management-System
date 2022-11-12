package com.spring.controller;

import javax.mail.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.service.*;
import com.spring.model.*;

@Controller
public class controller {
	
	@Autowired
	private service service;
	
	@RequestMapping("/index")
	String index()
	{
		System.out.println("index");
		return "index";
	}
	
	@RequestMapping(path="/loginstudent" , method= {RequestMethod.GET , RequestMethod.POST})
	public String loginstudent(@ModelAttribute user user,HttpServletRequest req,HttpServletRequest sess)
	{
		System.out.println("login student");
		if(sess.getAttribute("email")==null)
	   {
		String s=service.loginstudent(user,req);
		System.out.println(s);
		
		if(s.equals("incorrect userid"))
		{
			req.setAttribute("userid", s);
		}
		else if(s.equals("incorrect password"))
		{
			req.setAttribute("password", s);
		}
		else
		{
			return "home";
		}
		return "index";
	   }
		return "home";
	}
	
	@RequestMapping(path="/signuppage" , method= {RequestMethod.GET,RequestMethod.POST})
	public String signuppage()
	{
		System.out.println("signuppage");
		return "signup";
	}
	
	@RequestMapping(path="/signup" , method= {RequestMethod.GET , RequestMethod.POST})
	public String insert(@ModelAttribute user user,HttpServletRequest req)
	{
		System.out.println("insert");
		String s=service.insert(user);
		if(s==null) req.setAttribute("data", "Already Registered");
		else req.setAttribute("data", "Registration Successfull");
		return "signup";
	}
	
	@RequestMapping(path="/loginadmin" , method= {RequestMethod.GET , RequestMethod.POST})
	public String loginadmin(HttpServletRequest req,HttpSession sess)
	{
		System.out.println("loginadmin");
			String s=service.loginadmin(req);
			System.out.println(s);
			
			if(s.equals("incorrect userid"))
			{
				req.setAttribute("userid1", s);
			}
			else if(s.equals("incorrct password"))
			{
				req.setAttribute("password1", s);
			}
			else
			{
				return "homeadmin";
			}
			return "index";
	}
	
	@RequestMapping(path="/add")
	public String add(HttpSession s)
	{
		if(s.getAttribute("email")==null) return "index"; 
		return "addbooks";		
	}
	
	
	@RequestMapping(path="/addbook" , method={RequestMethod.POST , RequestMethod.GET})
	public String addbooks(@ModelAttribute user user,HttpSession s,Model model)
	{
		System.out.println("addbooks");
		if(s.getAttribute("email")==null)
		{
			return "index";
		}
		
		int x=service.addbooks(user);
		if(x==0) model.addAttribute("result", "There is some error..");
		else model.addAttribute("result", "Book added Successfully..");
		return "addbooks";
	}
	
	@RequestMapping(path="/forgetpassword" , method= {RequestMethod.POST , RequestMethod.GET})
	public String forgetpassword()
	{
		return "forget";
	}
	
	@RequestMapping(path="/sendmail" , method= {RequestMethod.POST , RequestMethod.GET})
	public String sendmail(HttpServletRequest req) throws MessagingException,AddressException
	{
		System.out.println("email");
		
		Properties p=new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.EnableSSL.enable", "true");
		p.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getInstance(p, new Authenticator(){
			                                 protected PasswordAuthentication getPasswordAuthentication()
			                                 {
			                                    return new PasswordAuthentication("donreturns46@gmail.com","nevfanjburkxpmex");
			                                 }
		});
		
		session.setDebug(true);
		
		MimeMessage msg =new MimeMessage(session);
		try
		{
		  msg.setFrom(new InternetAddress("donreturns46@gmail.com"));
		  msg.setRecipient(Message.RecipientType.TO,new InternetAddress(req.getParameter("email")));
		  msg.setSubject("Library Account Password");
	    
		  String s=service.sendmail(req.getParameter("email"),req.getParameter("name"));
	      if(s==null)
	      {
	    	req.setAttribute("value", "Email is not Registered..");
	    	System.out.println("Email is not Registered..");
	      }
	      else
	      {
	    	System.out.println("sending mail..");
	    	msg.setContent("Your Password is"+s,"text/html");
	    	Transport.send(msg);
	        req.setAttribute("value", "password send on Email");
	        System.out.println("password send on Email");
	      }
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "forget";
	}
	
	
	@RequestMapping(path="/logout")
	public String logout(HttpServletRequest req)
	{
		req.getSession().invalidate();
		return "index";
		
	}
	
	@RequestMapping(path="/viewallbooks" , method= {RequestMethod.POST , RequestMethod.GET})
	public String viewAllBooks(HttpSession s)
	{
		if(s.getAttribute("email")==null) return "index";
		else service.viewAllBooks(s);
		return "viewallbooks";
	}
	
	@RequestMapping(path="/issue" , method= {RequestMethod.POST , RequestMethod.GET})
	public String issueBook(HttpSession sess,HttpServletRequest req)
	{
		if(sess.getAttribute("email")==null)
		{
			return "index";
		}
		else
		{
			String s=service.issueBook(req);
			if(s.equals("Not Available")) req.setAttribute("result", "This Book is Not Availabe in Libraray");
			else if(s.equals("Already Issued")) req.setAttribute("result", "Book is Already Issued");
			else req.setAttribute("result", "Book Succesfully Issued");
		}
		//service.viewAllBooks(sess);
		return "viewallbooks";
	}
	
	@RequestMapping(path="/viewissuedbooks" , method= {RequestMethod.POST , RequestMethod.GET})
	public String viewIssuedBooks(HttpServletRequest req,HttpSession sess)
	{
		if(sess.getAttribute("email")==null) return "index";
		else
		{
			String s=service.viewIssuedBooks(sess);
			if(s.equals("No Issued Books")) req.setAttribute("result", "No Books Are Issued");
		}
		return "viewissuebooks";
	}
	
	@RequestMapping(value="/password")
	public String password(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		return "changepass";
	}
	
	@RequestMapping(value="/name")
	public String username(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		return "changeusername";
	}
	
	@RequestMapping(value="/email")
	public String email(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		return "changeemail";
	}
	
	@RequestMapping(value="/number")
	public String mobilenumber(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		return "changemobilenumber";
	}
	
	
	@RequestMapping(value="changepass" , method= RequestMethod.POST)
	public String changePassword(HttpServletRequest req,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		if(service.changePassword(req)==0)
		{
			req.setAttribute("result","there is some error");
			return "changepass";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","password changes successfully");
		}
		return "index";
	}
	
	
	@RequestMapping(value="changeusername" , method= RequestMethod.POST)
	public String changeUserName(HttpServletRequest req,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		if(service.changeUserName(req)==0)
		{
			req.setAttribute("result","there is some error");
			return "changeusername";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","UserName changes successfully");
		}
		return "index";
	}
	
	@RequestMapping(value="changeemail" , method= RequestMethod.POST)
	public String changeEmail(HttpServletRequest req,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		int x=service.changeEmail(req);
		if(x==0)
		{
			req.setAttribute("result","there is some error");
			return "changeemail";
		}
		else if(x==10)
		{
			req.setAttribute("result","This user is already exsist");
			return "changeemail";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","Email changes successfully");
		}
		return "index";
	}
	
	@RequestMapping(value="changemobile" , method= RequestMethod.POST)
	public String changeMobile(HttpServletRequest req,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "index";
		}
		if(service.changeMobile(req)==0)
		{
			req.setAttribute("result","there is some error");
			return "changemobile";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","MobileNumber changes successfully");
		}
		return "index";
	}

}
