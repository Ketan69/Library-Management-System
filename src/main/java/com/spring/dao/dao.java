package com.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.model.user;


@Repository
public class dao {

	@Autowired
	private JdbcTemplate jdbctemplate; 
	
	public String loginstudent(user user,HttpServletRequest req)
	{
		try
		{
			System.out.println("dao login student..");
			System.out.println(user);
			
			HttpSession s=req.getSession();
			System.out.println(s);
			
			Connection c=jdbctemplate.getDataSource().getConnection();
			 
			PreparedStatement p=c.prepareStatement("select * from user where email=?");
			
			if(s.getAttribute("email")==null) p.setString(1, user.getEmail());
			else p.setString(1, (String)s.getAttribute("email"));
			ResultSet r=p.executeQuery();
			
			if(r.next())
			{
				
				if(r.getString("password").equals(user.getPassword()) || r.getString("password").equals((String)s.getAttribute("password")))
				{
					if(s.getAttribute("email")==null)
					{
					  s.setAttribute("name", r.getString("name"));
					  s.setAttribute("password", user.getPassword());
					  s.setAttribute("email", user.getEmail());
					  s.setAttribute("number", r.getString("number"));
					}
					return "correct";
				}
				else
				{
					return "incorrect password";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "incorrect userid";
	}
	
	public String insert(user user)
	{
		System.out.println("dao insert");
		try
		{
			Connection c=jdbctemplate.getDataSource().getConnection();
			PreparedStatement p=null;
			
			p=c.prepareStatement("select * from user where email=?");
			p.setString(1,user.getEmail());
			ResultSet rs=p.executeQuery();
			System.out.println(rs+" "+user);
			if(rs.next())
			{
				System.out.println("Already there..");
				return "null";
			}
			
			String q="insert into user(name,password,email,number) values(?,?,?,?)";
			p=c.prepareStatement(q);
			
			System.out.println("inserting..");
			p.setString(1, user.getName());
			p.setString(2, user.getPassword());
			p.setString(3, user.getEmail());
			p.setString(4, user.getNumber());
			p.executeUpdate();
			System.out.println("inserted..");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "signup";
	}
		
		public String loginadmin(HttpServletRequest req)
		{
			System.out.println("dao login admin");
			try
			{
				HttpSession s=req.getSession();
				if(s.getAttribute("email")==null)
				{
				  s.setAttribute("email", req.getParameter("email"));
				  s.setAttribute("password", req.getParameter("password"));
				}
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=null;
				p=c.prepareStatement("select * from admin where email=?");
				p.setString(1,(String)s.getAttribute("email"));
				ResultSet rs=p.executeQuery();
				
				if(rs.next())
				{	
					if(rs.getString("password").equals((String)s.getAttribute("password")))
					{
						ArrayList<ArrayList<String>> list=new ArrayList<>();
						p=c.prepareStatement("select * from books");
						ResultSet r=p.executeQuery();
						
						while(r.next())
						{
							ArrayList<String> l=new ArrayList<>();
							l.add(r.getString(1));
							l.add(r.getString(2));
							l.add(r.getString(3));
							l.add(r.getString(4));
							l.add(r.getString(5));
							l.add(r.getString(6));
							list.add(l);
							System.out.println(l);				
						}
						
						s.setAttribute("books", list);					
						return "correct";
					}
					else
					{
						s.removeAttribute("email");
						s.removeAttribute("password");
						return "incorrect password";
					}
				}
				
				s.removeAttribute("email");
				s.removeAttribute("password");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return "incorrect userid";
		}
		
		public int addbooks(user user)
		{
			int x=0;
			try
			{
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=c.prepareStatement("insert into books(subject,edition,price,author,status) values(?,?,?,?,?)");
				p.setString(1, user.getSubject());
				p.setString(2, user.getEdition());
				p.setString(3, user.getPrice());
				p.setString(4, user.getAuthor());
				p.setString(5, "available");
				
				x=p.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return x;
		}
		
		public String sendmail(String email,String name)
		{
			System.out.println("dao sendmail..");
			try
			{
				Connection c=jdbctemplate.getDataSource().getConnection();
				name=name.toLowerCase();
				System.out.println(name);
				String q=null;
				if(name.equals("admin")) q="select * from admin where email=?";
				if(name.equals("user")) q="select * from user where email=?";
				
				PreparedStatement p=c.prepareStatement(q);
				p.setString(1,email);
				ResultSet rs=p.executeQuery();
				
				if(rs.next()) return rs.getString("password");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		public void viewAllBooks(HttpSession s)
		{
			try
			{
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=c.prepareStatement("select * from books");
				ResultSet rs=p.executeQuery();
				ArrayList<ArrayList<String>> list=new ArrayList<>();
				
				while(rs.next())
				{
					ArrayList<String> l=new ArrayList<>();
					l.add(rs.getString(2));
					l.add(rs.getString(3));
					l.add(rs.getString(4));
					l.add(rs.getString(5));
					l.add(rs.getString(6));
					list.add(l);
				}
				
				s.setAttribute("books", list);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public String issueBook(HttpServletRequest req)
		{
			try
			{
				HttpSession s=req.getSession();
				
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=null;
				p=c.prepareStatement("select * from books where subject=?");
				p.setString(1,req.getParameter("subject"));
				ResultSet rs=p.executeQuery();
				
				int x=0,y=0;
				while(rs.next())
				{
					y=1;
					if(rs.getString("status").equals("available"))
					{
						x=rs.getInt(1);
						break;
					}
				}
				
				if(x==0 && y==0) return "Not Available";
				if(x==0 && y==1) return "Already Issued";
				
				p=c.prepareStatement("update books set status=? where id=?");
				p.setString(1, "unavailable");
				p.setInt(2, x);
				p.executeUpdate();
				
				p=c.prepareStatement("insert into issuedbooks values(?,?)");
				p.setString(1, (String)s.getAttribute("email"));
				p.setString(2, req.getParameter("subject"));
				p.executeUpdate();			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return "successfully";
		}
		
		public String viewIssuedBooks(HttpSession s)
		{
			try
			{
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=c.prepareStatement("select * from books b INNER JOIN issuedbooks ib where(b.subject=ib.subject && email=?)");
				p.setString(1, (String)s.getAttribute("email"));
				ResultSet rs=p.executeQuery();
				
				ArrayList<ArrayList<String>> list=new ArrayList<>();
				int x=0;
				while(rs.next())
				{
					x=1;
					ArrayList<String> l=new ArrayList<>();
					l.add(rs.getString(2));
					l.add(rs.getString(3));
					l.add(rs.getString(4));
					l.add(rs.getString(5));
					l.add(rs.getString(6));
					list.add(l);
				}
				if(x==0) return "No Issued Books";
				else s.setAttribute("issuebooks", list);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return "DONE";
		}
		
		public int changePassword(HttpServletRequest req)
		{
			try
			{
				HttpSession s=req.getSession();
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=c.prepareStatement("update user set password=? where email=?");
				p.setString(1, req.getParameter("password"));
				p.setString(2, (String)s.getAttribute("email"));
				return p.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return 0;
		}
		
		public int changeUserName(HttpServletRequest req)
		{
			try
			{
				HttpSession s=req.getSession();
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=c.prepareStatement("update user set name=? where email=?");
				p.setString(1, req.getParameter("userName"));
				p.setString(2, (String)s.getAttribute("email"));
				return p.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return 0;
		}
		
		public int changeMobile(HttpServletRequest req)
		{
			try
			{
				HttpSession s=req.getSession();
				Connection c=jdbctemplate.getDataSource().getConnection();
				PreparedStatement p=c.prepareStatement("update user set number=? where email=?");
				p.setString(1, req.getParameter("mobileNumber"));
				p.setString(2, (String)s.getAttribute("email"));
				return p.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return 0;
		}
		
		public int changeEmail(HttpServletRequest req)
		{
			try
			{
				HttpSession s=req.getSession();
				Connection con=jdbctemplate.getDataSource().getConnection();
				PreparedStatement pst1=con.prepareStatement("select * from user where email=?");
				pst1.setString(1, req.getParameter("email"));
				ResultSet rs=pst1.executeQuery();
				if(rs.next())
				{
					return 10;
				}
				PreparedStatement pst=con.prepareStatement("update user set email=? where email=?");
				pst.setString(1,req.getParameter("email"));
				pst.setString(2,(String) s.getAttribute("email"));
				int x=pst.executeUpdate();
				return x;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return 0;
		}
	}
