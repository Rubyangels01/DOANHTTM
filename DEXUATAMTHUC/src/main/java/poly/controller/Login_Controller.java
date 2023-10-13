package poly.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import poly.DAO.*;
import poly.bean.USER;
@Controller
public class Login_Controller {
	
	
	@RequestMapping(value = "users/Login", method = RequestMethod.GET)
	public String Home() {
		return "users/Login";
	}
	@RequestMapping("users/checkLogin")
	public String checkLogin(HttpServletRequest request,Model model) throws SQLException 
	{
		USER_DAO user_DAO = new USER_DAO();
		 HttpSession session = request.getSession();
		String message = "Sai thông tin đăng nhập!";
		String username= request.getParameter("email");
		String password = request.getParameter("password");
//		ListKH  list = new ListKH();
		boolean found = false;
		
		 if(username.equals("admin") && password.equals("123"))
			{	
			
			 session.setAttribute("maUser", "KitChen");
         	session.setAttribute("email", "admin11@gmail.com");         	
         	session.setAttribute("nameuser", "admin");
         	session.setAttribute("password", "123");
//				return "admin/MainHome";
			}
		 else {
			 
			 if(!user_DAO.LISTUSER().isEmpty())
				{
					 for (USER user : user_DAO.LISTUSER()) 
					 {
				            
				           
				            	if (user.getEmail().equals(username) && user.getPassword().equals(password)) 
					            {
					            	found = true;
					            	
//					            	if(kh.getAccountName().equals((gioHang.getKhachHang().getAccountName())))
//					            			{
//					            			listGioHang = (ListGioHang) session.getAttribute("dsGioHang");
//					            			
//					            			}
					            			session.setAttribute("User", user);
					            			
					            	session.setAttribute("maUser", user.getMaUser());
					            	session.setAttribute("email", user.getEmail());
					            	session.setAttribute("nameuser", user.getTenUser());				            	
					            	session.setAttribute("password", user.getPassword());
//					            	ListSP listSP = new ListSP();
//					    		    model.addAttribute("sanPhamList", listSP.getListSP());
					            	return "users/Main";
					                
					                
					            
							}
				        }
				}
//				else 
//				{
//					for(KhachHang kh:listKH)
//					{
//						
//						
//							 if (kh.getAccountName().equals(username) && kh.getPassword().equals(password)) 
//							 {
//					            	found = true;
//					            	session.setAttribute("khachhang", kh);
//					            	
//					            	session.setAttribute("fullName", kh.getFullName());
//					            	session.setAttribute("email", kh.getEmail());
//					            	session.setAttribute("phone", kh.getPhone());
//					            	session.setAttribute("accountName", kh.getAccountName());
//					            	session.setAttribute("password", kh.getPassword());
//					            	ListSP listSP = new ListSP();
//					    		    model.addAttribute("sanPhamList", listSP.getListSP());
//					            	return "users/Home";
//						
//						}
//						
//						 
//						 for (KhachHang kc : list.getListKH2()) 
//						 {
//					           
//					            	if (kc.getAccountName().equals(username) && kc.getPassword().equals(password)) 
//						            {
//						            	found = true;
//						            	session.setAttribute("khachhang", kc); //lưu khách hàng vào session
//						            	
//						            	session.setAttribute("fullName", kc.getFullName());
//						            	session.setAttribute("email", kc.getEmail());
//						            	session.setAttribute("phone", kc.getPhone());
//						            	session.setAttribute("accountName", kc.getAccountName());
//						            	session.setAttribute("password", kc.getPassword());
//						            	ListSP listSP = new ListSP();
//						    		    model.addAttribute("sanPhamList", listSP.getListSP());
//						            	return "users/Home";
//						                
//						                
//						            
//								}
//					        }
//				}
//					
//					
//
//			    }
//			
//		}
//		  
//       
		
	        	
	       
	    	
	    	
	    
	}
		 request.setAttribute("message", message);
	    	return "users/Login";
	}
}
