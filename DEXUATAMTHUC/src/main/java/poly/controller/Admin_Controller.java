package poly.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import poly.DAO.MEAL_DAO;
import poly.DAO.USER_DAO;
import poly.bean.MEAL;

@Controller
public class Admin_Controller {
	
	
	@RequestMapping(value = "admin/MainHome", method = RequestMethod.GET)
    public String MainHome() {
        return "admin/MainHome";
    }
	
	@RequestMapping(value = "admin/mainfood", method = RequestMethod.GET)
	public String CATOGORY(Model model) throws SQLException {
		MEAL_DAO meal_dao = new MEAL_DAO();
	    model.addAttribute("MainFood", meal_dao.getTypeFood(1));
	    model.addAttribute("DessertFood",meal_dao.getTypeFood(2));
	    model.addAttribute("BreakFast",meal_dao.getTypeFood(3));
	    return "admin/DanhMucMonAn";
	}
	
	@RequestMapping(value = "admin/famousfood", method = RequestMethod.GET)
	public String List_Famous(Model model) throws SQLException {
		MEAL_DAO meal_dao = new MEAL_DAO();
	    model.addAttribute("famousfood",meal_dao.GET_FAMOUSFOOD());
	    
	    return "admin/MonAnPhoBien";
	}
	
	
	
	
	
	
	@RequestMapping(value = "admin/qlndung", method = RequestMethod.GET)
    public String QuanLyND(Model model) throws SQLException {
		USER_DAO user_DAO = new USER_DAO();
		model.addAttribute("ListUser", user_DAO.LISTUSER());
        return "admin/QuanLyNguoiDung";
    }
	@RequestMapping(value = "admin/qlmonan", method = RequestMethod.GET)
    public String QuanLyMonAn(Model model) throws SQLException {
		MEAL_DAO meal_DAO = new MEAL_DAO();
		model.addAttribute("ListType", meal_DAO.GET_TYPEMEAL());
        return "admin/QuanLyMonAn";
    }
	@RequestMapping(value = "admin/checktype")
    public String CHECKTYPE(Model model, HttpServletRequest request) throws SQLException {
		int idloai = Integer.parseInt(request.getParameter("typemeal"));
		MEAL_DAO meal_DAO = new MEAL_DAO();
		model.addAttribute("MealToType", meal_DAO.getTypeFood(idloai));
		model.addAttribute("ListType", meal_DAO.GET_TYPEMEAL());
        return "admin/QuanLyMonAn";
    }
	@RequestMapping(value = "admin/DeleteMeal{id}", method = RequestMethod.GET)
	public String DELETE(@PathVariable("id") int melaId, ModelMap model, HttpServletRequest request,HttpSession session)
			throws SQLException {
		MEAL_DAO meal_DAO = new MEAL_DAO();
		meal_DAO.DELETE_MEAL(melaId);	
		return "admin/QuanLyMonAN";
	}
	@RequestMapping(value = "admin/qlDM", method = RequestMethod.GET)
    public String QuanLyDanhMuc(Model model) throws SQLException {
		MEAL_DAO meal_DAO = new MEAL_DAO();
		model.addAttribute("ListNumberofType", meal_DAO.GET_NUBERMEALOFTYPE());
        return "admin/QuanLyDanhMuc";
    }
	@RequestMapping(value = "admin/checkcate")
    public String CHECK_CATEGORY(Model model, HttpServletRequest request) throws SQLException {
		String name =  request.getParameter("categoryName");
		MEAL_DAO meal_DAO = new MEAL_DAO();
		
		if(name.equals(null) || name.isEmpty())
		{
			request.setAttribute("message", "Lỗi khi thêm danh mục trống");
			model.addAttribute("ListNumberofType", meal_DAO.GET_NUBERMEALOFTYPE());
			
	        return "admin/QuanLyDanhMuc";
		}
		meal_DAO.ADD_TYPE(name);
		model.addAttribute("ListNumberofType", meal_DAO.GET_NUBERMEALOFTYPE());
		
        return "admin/QuanLyDanhMuc";
    }
	@RequestMapping(value = "admin/checknametype")
    public String CHECK_NAMETYPE(Model model, HttpServletRequest request) throws SQLException {
		String name =  request.getParameter("nametype");
		int id = Integer.parseInt(request.getParameter("idtype"));
		MEAL_DAO meal_DAO = new MEAL_DAO();
		if(name.equals(null) || name.isEmpty())
		{
			request.setAttribute("message", "Lỗi khi chỉnh sửa loại món");
			model.addAttribute("ListNumberofType", meal_DAO.GET_NUBERMEALOFTYPE());
			
	        return "admin/QuanLyDanhMuc";
		}
		meal_DAO.UPDATE_TYPE(name, id);
		model.addAttribute("ListNumberofType", meal_DAO.GET_NUBERMEALOFTYPE());
        return "admin/QuanLyDanhMuc";
    }
	
//	@RequestMapping(value = "admin/qlBD", method = RequestMethod.GET)
//    public String QuanLyBaiDang(Model model) throws SQLException {
//		MEAL_DAO meal_DAO = new MEAL_DAO();
//		model.addAttribute("ListNotAccept", meal_DAO.GET_STATUS_MEAL());
//        return "admin/QuanLyBaiDang";
//    }
	@RequestMapping(value = "admin/checkbox")
    public String CHECKBOX_CATEGORY(Model model, HttpServletRequest request,@RequestParam List<Integer> selectedIds) throws SQLException {
		
		
        return "admin/QuanLyDanhMuc";
    }

}
