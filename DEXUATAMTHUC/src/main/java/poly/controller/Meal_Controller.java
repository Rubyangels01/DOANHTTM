package poly.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.portlet.ModelAndView;

import poly.DAO.*;
import poly.bean.*;
//@RestController




@Controller
public class Meal_Controller {
	 int TIMKIEM,LUU = 0;
	  int YEUTHICH,XEM = 1;
	  
	  @RequestMapping(value = "mainfood", method = RequestMethod.GET)
		public String CATOGORYPAGE(Model model) throws SQLException {
			MEAL_DAO meal_dao = new MEAL_DAO();
		    model.addAttribute("MainFood", meal_dao.getTypeFood(1));
		    model.addAttribute("DessertFood",meal_dao.getTypeFood(2));
		    model.addAttribute("BreakFast",meal_dao.getTypeFood(3));
		    return "Category";
		}
	  @RequestMapping(value = "famousfood", method = RequestMethod.GET)
		public String List_FamousPage(Model model) throws SQLException {
			MEAL_DAO meal_dao = new MEAL_DAO();
		    model.addAttribute("famousfood",meal_dao.GET_FAMOUSFOOD());
		    
		    return "Famous_List";
		}
	  // hàm ra trang find meal
	  @RequestMapping(value = "/findmeal", method = RequestMethod.GET)
		public String FIND_MEALPAGE(Model model) throws SQLException {		
			return "Find_Meal";
		}
	  
	  @RequestMapping(value = "/Infor_Meal{id}", method = RequestMethod.GET)
		public String DETAIL_MEAL_PAGE(@PathVariable("id") String melaId, ModelMap model, HttpServletRequest request,HttpSession session)
				throws SQLException {
			// xử lý mã sản phẩm và chuẩn bị dữ liệu
			MEAL_DAO mealDao = new MEAL_DAO();
//			Integer maUser = (Integer) session.getAttribute("maUser");
			for (MEAL meal : mealDao.getAllMeal()) {
				if (meal.getMaMon() == Integer.parseInt(melaId)) {
					
					request.setAttribute("maMon", meal.getMaMon());
						request.setAttribute("imgUrl", meal.getHinhAnh());
						request.setAttribute("tenMon", meal.getTenMon());
						
						 model.addAttribute("NguyenLieu",mealDao.GET_NGUYENLIEU(meal.getMaMon()));
						 model.addAttribute("BuocLam",mealDao.GET_STEP(meal.getMaMon()));
//						 mealDao.VIEWORFIND(maUser, meal.getMaMon(),XEM);
						return "DeTail_Meal";
					}
				} 
			return "DeTail_Meal";
			// trả về view hiển thị chi tiết sản phẩm
		}
	  
	  
	  @RequestMapping("/checksearch")
		public String CHECK_SEARCH_PAGE(HttpServletRequest request, Model model) throws SQLException {

		    MEAL_DAO meal_DAO = new MEAL_DAO();
		    ArrayList<MEAL> Meal_Find = new ArrayList<MEAL>();
		    String input_search = request.getParameter("search");
		    String[] ingredientArray = input_search.split(",\\s*");
		    List<String> ingredientList = Arrays.asList(ingredientArray);

		    for (MEAL meal : meal_DAO.getAllMeal()) {
		        boolean containsAll = true;

		        for(String nguyenlieu : ingredientList) {
		            boolean containsIngredient = false;
		            for (NGUYENLIEU_MONAN nglieumon : meal_DAO.GET_NGUYENLIEU(meal.getMaMon())) {
		                if (nglieumon.getTenNguyenLieu().equalsIgnoreCase(nguyenlieu.trim())) {
		                    containsIngredient = true;
		                    break;
		                }
		            }

		            if (!containsIngredient) {
		                containsAll = false;
		                break;
		            }
		        }

		        if (containsAll) {
		            Meal_Find.add(meal);
		        }
		    }
		    model.addAttribute("message", meal_DAO.taoMaCongThuc());
		    // Thực hiện các thao tác cần thiết với Meal_Find, ví dụ như gửi về frontend
		    model.addAttribute("foundMeals", Meal_Find);

		    return "List_Find";
		}
	  
	  
	  
	  
	  
	@RequestMapping(value = "users/Main", method = RequestMethod.GET)
	public String HOME(Model model) throws SQLException {
		MEAL_DAO meal_dao = new MEAL_DAO();
	    model.addAttribute("famousfood",meal_dao.GET_FAMOUSFOOD());
		return "users/Main";
	}	
	
	@RequestMapping(value = "users/mainfood", method = RequestMethod.GET)
	public String CATOGORY(Model model) throws SQLException {
		MEAL_DAO meal_dao = new MEAL_DAO();
	    model.addAttribute("MainFood", meal_dao.getTypeFood(1));
	    model.addAttribute("DessertFood",meal_dao.getTypeFood(2));
	    model.addAttribute("BreakFast",meal_dao.getTypeFood(3));
	    return "users/Category";
	}
	@RequestMapping(value = "users/famousfood", method = RequestMethod.GET)
	public String List_Famous(Model model) throws SQLException {
		MEAL_DAO meal_dao = new MEAL_DAO();
	    model.addAttribute("famousfood",meal_dao.GET_FAMOUSFOOD());
	    
	    return "users/Famous_List";
	}
	
	@RequestMapping(value = "users/suggest", method = RequestMethod.GET)
	public String LIST_SUGGEST(Model model, HttpSession session) throws SQLException {
	    MEAL_DAO mealDao = new MEAL_DAO();
	    
	    // Kiểm tra xem session có tồn tại giá trị "maUser" hay không
	    Integer maUser = (Integer) session.getAttribute("maUser");
//	    model.addAttribute("message", maUser);
	    
	    if (maUser == null) {
	        
	        return "users/Login";
	    }
	    model.addAttribute("Suggest_BreakFast", mealDao.generateMixedList(maUser, 3));
	    model.addAttribute("Suggest_Lunch", mealDao.generateMixedList(maUser, 1));
	    model.addAttribute("Suggest_Dinner", mealDao.generateMixedList(maUser, 4));
	    model.addAttribute("Suggest_Dessert", mealDao.generateMixedList(maUser, 2));
	    return "users/Suggest";
	}
	
	@RequestMapping(value = "users/Infor_Meal{id}", method = RequestMethod.GET)
	public String DETAIL_MEAL(@PathVariable("id") String melaId, ModelMap model, HttpServletRequest request,HttpSession session)
			throws SQLException {
		// xử lý mã sản phẩm và chuẩn bị dữ liệu
		MEAL_DAO mealDao = new MEAL_DAO();
		Integer maUser = (Integer) session.getAttribute("maUser");
		for (MEAL meal : mealDao.getAllMeal()) {
			if (meal.getMaMon() == Integer.parseInt(melaId)) {
				
				request.setAttribute("maMon", meal.getMaMon());
					request.setAttribute("imgUrl", meal.getHinhAnh());
					request.setAttribute("tenMon", meal.getTenMon());
					
					 model.addAttribute("NguyenLieu",mealDao.GET_NGUYENLIEU(meal.getMaMon()));
					 model.addAttribute("BuocLam",mealDao.GET_STEP(meal.getMaMon()));
					 mealDao.VIEWORFIND(maUser, meal.getMaMon(),XEM);
					return "users/DeTail_Meal";
				}
			} 
		return "users/DeTail_Meal";
		// trả về view hiển thị chi tiết sản phẩm
	}
	@RequestMapping("users/saveandlike")
	public ResponseEntity<String> saveAndLike(HttpSession session, @RequestParam("action") String action, @RequestParam("idmonan") int idmonan) throws SQLException {
	    MEAL_DAO meal_DAO = new MEAL_DAO();
	    Integer maUser = (Integer) session.getAttribute("maUser");
//	    request.setAttribute("message", idmonan);
	    boolean result_save = true;
	    @SuppressWarnings("unused")
		boolean result_like= true;
	    if (action.equals("luu")) {
	    	 if(meal_DAO.CHECK_LIKEORSAVE(maUser, idmonan, LUU) == 0)
	    	 {
	    		 meal_DAO.SAVEORLIKE(maUser, idmonan, LUU);
	    	 }
	    	 else {
	    		 result_save = false;
			}
	       
	    } else if (action.equals("yeuThich")) {
	    	 if(meal_DAO.CHECK_LIKEORSAVE(maUser, idmonan,1) == 0)
	    	 {
	    		 meal_DAO.SAVEORLIKE(maUser, idmonan, 1);
	    	 }
	    	 else {
	    		 result_like = false;
			}
	    }
	    if (result_save) {
	    	return new ResponseEntity<>("Món đã được lưu!", HttpStatus.OK);         
        } else {
        	return new ResponseEntity<>("Lưu thành công!", HttpStatus.OK);           
        }
	   

	    
	}
	
	@RequestMapping(value = "users/createmeal", method = RequestMethod.GET)
	public String CREATE_MEAL(Model model) throws SQLException {
		MEAL_DAO meal_DAO = new MEAL_DAO();
		model.addAttribute("NguyenLieu",meal_DAO.GET_INGREDIENT());
		model.addAttribute("loaiMon",meal_DAO.GET_TYPEMEAL());
		return "users/Create_Meal";
	}
	
	
	@RequestMapping("users/checksearch")
	public String CHECK_SEARCH(HttpServletRequest request, Model model) throws SQLException {

	    MEAL_DAO meal_DAO = new MEAL_DAO();
	    ArrayList<MEAL> Meal_Find = new ArrayList<MEAL>();
	    String input_search = request.getParameter("search");
	    String[] ingredientArray = input_search.split(",\\s*");
	    List<String> ingredientList = Arrays.asList(ingredientArray);

	    for (MEAL meal : meal_DAO.getAllMeal()) {
	        boolean containsAll = true;

	        for(String nguyenlieu : ingredientList) {
	            boolean containsIngredient = false;
	            for (NGUYENLIEU_MONAN nglieumon : meal_DAO.GET_NGUYENLIEU(meal.getMaMon())) {
	                if (nglieumon.getTenNguyenLieu().equalsIgnoreCase(nguyenlieu.trim())) {
	                    containsIngredient = true;
	                    break;
	                }
	            }

	            if (!containsIngredient) {
	                containsAll = false;
	                break;
	            }
	        }

	        if (containsAll) {
	            Meal_Find.add(meal);
	        }
	    }
	    model.addAttribute("message", meal_DAO.taoMaCongThuc());
	    // Thực hiện các thao tác cần thiết với Meal_Find, ví dụ như gửi về frontend
	    model.addAttribute("foundMeals", Meal_Find);

	    return "users/List_Find";
	}
	@RequestMapping("users/checkmeal")
	public String CHECK_MEAL(HttpServletRequest request, Model model,HttpSession session,@ModelAttribute("ListNguyenLieu") ListNguyenLieu formModel) throws SQLException {

	    MEAL_DAO meal_DAO = new MEAL_DAO();
	    ArrayList<MEAL> Meal_Find = new ArrayList<MEAL>();
	    Integer maUser = (Integer) session.getAttribute("maUser");
	    // Lấy thông tin từ request
	    String name = request.getParameter("tenmon");
	    String descri = request.getParameter("mota");
	    String level = request.getParameter("dokho");
	    
	    // Chuyển đổi dữ liệu từ String sang int
	    int hour = Integer.parseInt(request.getParameter("hour"));
	    int minute = Integer.parseInt(request.getParameter("minute"));

	    int typeMeal = Integer.parseInt(request.getParameter("loaimon"));
	   

	    

	    // Gọi hàm CREATE_RECIPE với các tham số đã lấy được
	   meal_DAO.CREATE_RECIPE(hour, minute, level);
	   meal_DAO.CREATE_MEAL(name, descri, typeMeal, maUser, meal_DAO.maCongThuc);
	   for(NguyenLieu_SoLuong nglieumon : formModel.getCT_NguyenLieuList())
	   {
		   meal_DAO.CREATE_INGREDIENT(nglieumon.getIdNguyenLieu(),nglieumon.getSoLuong() );
	   }
	  for(int i = 0; i < formModel.getCT_BuocLam().size();i++)
	  {
		  meal_DAO.CREATE_STEP(i+1,formModel.getCT_BuocLam().get(i).getMoTa());
	  }	  
	  
//	  request.setAttribute("imgUrl", meal_DAO.getMealById(meal_DAO.maCongThuc));
		request.setAttribute("tenMon", name);
		
		 model.addAttribute("NguyenLieu",meal_DAO.GET_INGREDIENT_FROM_RECIPE(meal_DAO.maCongThuc));
		 model.addAttribute("BuocLam",meal_DAO.GET_STEP_FROM_IDRECIPE(meal_DAO.maCongThuc));
//		 int idmon = meal_DAO.GET_IDMEAL(meal_DAO.maCongThuc);
//		 meal_DAO.UPDATE_STATUSMEAL(idmon);
	    return "users/Meal_Created";
	}
	
	



	@RequestMapping(value = "users/findmeal", method = RequestMethod.GET)
	public String FIND_MEAL(Model model) throws SQLException {
		
		
		return "users/Find_Meal";
	}
//	@RequestMapping(value = "/notacount_findmeal", method = RequestMethod.GET)
//	public String NOACOUNT_FIND_MEAL(Model model) throws SQLException {
//		
//		
//		return "Find_Meal";
//	}
	@RequestMapping("users/searchmeal")
	public String CHECK_FIND_MEAL(HttpServletRequest request, Model model,HttpSession session) throws SQLException {
		
	    MEAL_DAO meal_DAO = new MEAL_DAO();	
	    String input_search = request.getParameter("search");
	    Integer maUser = (Integer) session.getAttribute("maUser");
	    ArrayList<MEAL> SORTED_LIST = meal_DAO.filterFoodList(meal_DAO.getAllMeal(),input_search);
	    if(SORTED_LIST == null || SORTED_LIST.isEmpty())
   	 {
	    	if(maUser == null)
	    	{
	    		request.setAttribute("message", "Danh sách tìm kiếm rỗng");
	    		return "Find_Meal";
	    	}
	    	else {
	    		request.setAttribute("message", "Danh sách tìm kiếm rỗng");
	    		return "users/Find_Meal";
			}
   		 
   		
   	 }else {
   		 
   		meal_DAO.sortFoodList(SORTED_LIST,input_search);
 	    request.setAttribute("input_search", input_search);
 	    model.addAttribute("FindMeal", SORTED_LIST);
 	    if(maUser == null)
 	    {
 	    	return "Find_Meal";
 	    }
 	    else {
 	    	for(MEAL meal : SORTED_LIST)
 	 	   {
 	 		   meal_DAO.VIEWORFIND(maUser, meal.getMaMon(), TIMKIEM);
 	 	   }
 	    	return "users/Find_Meal";
		}	    			
	}	    	    	    	    		    	    	   	   	   	    
	}

	@RequestMapping(value = "users/mealuser", method = RequestMethod.GET)
	public String MEAL_USER(Model model) throws SQLException {	
		MEAL_DAO meal_DAO = new MEAL_DAO();
		
		model.addAttribute("List_Saved",meal_DAO.getAllMeal());
		return "users/MonAn_User";
	}
	@RequestMapping(value = "users/EditMeal{id}", method = RequestMethod.GET)
	public String EDIT(@PathVariable("id") String melaId, ModelMap model, HttpServletRequest request,HttpSession session)
			throws SQLException {
		
		
		return "users/";
	}
	@RequestMapping(value = "users/DeleteMeal{id}", method = RequestMethod.GET)
	public String DELETE(@PathVariable("id") int melaId, ModelMap model, HttpServletRequest request,HttpSession session)
			throws SQLException {
		MEAL_DAO meal_DAO = new MEAL_DAO();
		meal_DAO.DELETE_MEAL(melaId);	
		return "users/MonAn_User";
	}
	
	
	

		@RequestMapping("users/getNewFoodItems")
	    public List<MEAL> getNewFoodItems() throws SQLException {
			MEAL_DAO meal_DAO = new MEAL_DAO();
	        List<MEAL> newFoodItems = meal_DAO.GET_FAMOUSFOOD();

	        return newFoodItems;
	    }
		
		
		@RequestMapping(value = "users/acountuser", method = RequestMethod.GET)
		public String LIST_SAVED(Model model, HttpSession session) throws SQLException {
			MEAL_DAO mealDao = new MEAL_DAO();
		    
		    // Kiểm tra xem session có tồn tại giá trị "maUser" hay không
		    Integer maUser = (Integer) session.getAttribute("maUser");
		    if (maUser == null) {
		        
		        return "users/Login";
		    }
		    model.addAttribute("list_saved", mealDao.GET_LIST_SAVED(maUser, LUU));
		    model.addAttribute("list_liked", mealDao.GET_LIST_SAVED(maUser, 1));
		    model.addAttribute("list_created", mealDao.GET_LIST_CREATED(maUser));
		    return "users/Acount_User";
		}
		
		
		@RequestMapping(value = "users/listsave", method = RequestMethod.GET)
		public String LIST_SAVED1(Model model, HttpSession session) throws SQLException {
			MEAL_DAO mealDao = new MEAL_DAO();
		    
		    // Kiểm tra xem session có tồn tại giá trị "maUser" hay không
		    Integer maUser = (Integer) session.getAttribute("maUser");
		    if (maUser == null) {
		        
		        return "users/Login";
		    }
		    model.addAttribute("listsaved", mealDao.GET_LIST_SAVED(maUser, 1));
		    
		    return "users/List_Save";
		}
		
		
		
		
		
		
		
		
		
		
//		@RequestMapping(value = "users/acountuser", method = RequestMethod.GET)
//		public String LIST_SAVED(Model model, HttpSession session) throws SQLException {
//		    MEAL_DAO mealDao = new MEAL_DAO();
//		    
//		    // Kiểm tra xem session có tồn tại giá trị "maUser" hay không
//		    Integer maUser = (Integer) session.getAttribute("maUser");
//		    if (maUser == null) {
//		        
//		        return "users/Login";
//		    }
//		   
//		    
//		    model.addAttribute("list_saved", mealDao.GET_LIST_SAVED(maUser, LUU));
//		    model.addAttribute("list_liked", mealDao.GET_LIST_SAVED(maUser, 1));
//		    model.addAttribute("list_created", mealDao.GET_LIST_CREATED(maUser));
//		    return "users/Acount_User";
//		}
//	@RequestMapping(value = "users/test1", method = RequestMethod.GET)
//	public String CREATE_MEAL1(Model model) throws SQLException {
//		MEAL_DAO meal_DAO = new MEAL_DAO();
//		model.addAttribute("NguyenLieu", meal_DAO.GET_INGREDIENT());
//		model.addAttribute("greeting", "Xin chào");
//		return "users/test";
//	}
//	@RequestMapping("users/test")
//	public String processForm(@ModelAttribute("ListNguyenLieu") ListNguyenLieu formModel, HttpServletRequest request) {
//	    // Xử lý dữ liệu
//	    if (formModel.getCT_NguyenLieuList() != null) {
//	        request.setAttribute("message", formModel.getCT_NguyenLieuList().get(1).getIdNguyenLieu());
//	        request.setAttribute("message1", formModel.getCT_BuocLam().get(1).getBuocLam());
//	    } else {
//	        request.setAttribute("message", "Danh sách rỗng hoặc null");
//	    }
//
//	    return "users/test2";
//	}
//	@RequestMapping(value = "users/suggest", method = RequestMethod.GET)
//	public String suggest() {
//		return "users/Suggest";
//	}
}
