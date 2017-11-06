package com.scsk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
  
@Controller  
@RequestMapping("/protected")  
public class CustomRoleController {  
  
    /** 
     *  
     * @return 
     */  
    @RequestMapping(value = "/user", method = RequestMethod.GET)  
    public String getCommonPage() {  
        return "/protected/user/commonpage.jsp";  
    }  
  
    /** 
     *  
     * @return 
     */  
    @RequestMapping(value = "/admin", method = RequestMethod.GET)  
    public String getAadminPage() {    
        return "/protected/admin/adminpage.jsp";  
  
    }  
    
    /** 
     *  
     * @return 
     */  
    @RequestMapping(value = "/denied", method = RequestMethod.GET)  
    public String getDeniedPage() {  
 
        return "/protected/user/deniedpage.jsp";  
  
    }  
  
}  