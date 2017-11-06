package com.scsk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scsk.util.LogInfoUtil;
import com.scsk.util.SessionUser;

@Controller
@RequestMapping("/view")
public class ViewsController {

    @RequestMapping(value = "/master", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView master() {
        LogInfoUtil.LogInfoPage("master");
        String userName = SessionUser.userName();
        ModelAndView modelAndView = new ModelAndView("master");
        modelAndView.addObject("userName", userName);
        return modelAndView;
    }

    @RequestMapping(value = "/welcomePage", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView welcome() {
        LogInfoUtil.LogInfoPage("welcomePage");
        return new ModelAndView("welcomePage");
    }

    @RequestMapping(value = "/userList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView loginUserList() {
        LogInfoUtil.LogInfoPage("userList");
        return new ModelAndView("userList");
    }

    @RequestMapping(value = "/authorityList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView authorityList() {
        LogInfoUtil.LogInfoPage("authorityList");
        return new ModelAndView("authorityList");
    }

    @RequestMapping(value = "/storeATMList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView storeATMList() {
        LogInfoUtil.LogInfoPage("storeATMList");
        return new ModelAndView("storeATMList");
    }
    
    @RequestMapping(value = "/iYoStoreATMList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView iYoStoreATMList() {
        LogInfoUtil.LogInfoPage("iYoStoreATMList");
        return new ModelAndView("iYoStoreATMList");
    }
    
    @RequestMapping(value = "/114StoreATMList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView hyakujyushiStoreATMList() {
        LogInfoUtil.LogInfoPage("114StoreATMList");
        return new ModelAndView("114StoreATMList");
    }

    @RequestMapping(value = "/accountAppList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView accountAppList() {
        LogInfoUtil.LogInfoPage("accountAppList");
        return new ModelAndView("accountAppList");
    }

    @RequestMapping(value = "/account114AppList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView account114AppList() {
        LogInfoUtil.LogInfoPage("account114AppList");
        return new ModelAndView("account114AppList");
    }
    
    @RequestMapping(value = "/accountDocumentList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView account114ImageList() {
        LogInfoUtil.LogInfoPage("accountDocumentList");
        return new ModelAndView("accountDocumentList");
    }
    
    @RequestMapping(value = "/accountYamaGataAppList", method = { RequestMethod.GET, RequestMethod.POST,
            RequestMethod.DELETE, RequestMethod.PUT })
    public ModelAndView accountYamaGataAppList() {
        LogInfoUtil.LogInfoPage("accountYamaGataAppList");
        return new ModelAndView("accountYamaGataAppList");
    }

    @RequestMapping(value = "/pushNotifications", method = { RequestMethod.GET, RequestMethod.POST,
            RequestMethod.DELETE, RequestMethod.PUT })
    public ModelAndView pushNotifications() {
        LogInfoUtil.LogInfoPage("pushNotifications");
        return new ModelAndView("pushNotifications");
    }

    @RequestMapping(value = "/passwordUpd", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView passwordUpd() {
        LogInfoUtil.LogInfoPage("passwordUpd");
        return new ModelAndView("passwordUpd");
    }

    @RequestMapping(value = "/passwordUpdate", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView passwordUpdate() {
        LogInfoUtil.LogInfoPage("passwordUpdate");
        return new ModelAndView("passwordUpdate");
    }

    @RequestMapping(value = "/pushRecordAppList", method = { RequestMethod.GET, RequestMethod.POST,
            RequestMethod.DELETE, RequestMethod.PUT })
    public ModelAndView pushRecordAppList() {
        LogInfoUtil.LogInfoPage("pushRecordAppList");
        return new ModelAndView("pushRecordAppList");
    }

    @RequestMapping(value = "/pushRecordAppYamaGataList", method = { RequestMethod.GET, RequestMethod.POST,
            RequestMethod.DELETE, RequestMethod.PUT })
    public ModelAndView pushRecordAppYamaGataList() {
        LogInfoUtil.LogInfoPage("pushRecordAppYamaGataList");
        return new ModelAndView("pushRecordAppYamaGataList");
    }
    
    @RequestMapping(value = "/pushRecordApp114List", method = { RequestMethod.GET, RequestMethod.POST,
            RequestMethod.DELETE, RequestMethod.PUT })
    public ModelAndView pushRecordApp114List() {
        LogInfoUtil.LogInfoPage("pushRecordApp114List");
        return new ModelAndView("pushRecordApp114List");
    }
    
    @RequestMapping(value = "/documentRecordList", method = { RequestMethod.GET, RequestMethod.POST,
            RequestMethod.DELETE, RequestMethod.PUT })
    public ModelAndView pushRecordImage114List() {
        LogInfoUtil.LogInfoPage("documentRecordList");
        return new ModelAndView("documentRecordList");
    }
    // @RequestMapping(value = "/batch", method = { RequestMethod.GET,
    // RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
    // public ModelAndView batch() {
    // LogInfoUtil.LogInfoPage("batch");
    // return new ModelAndView("batch");
    // }

    @RequestMapping(value = "/file", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView file() {
        LogInfoUtil.LogInfoPage("file");
        return new ModelAndView("file");
    }

    @RequestMapping(value = "/fileList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView imagefile() {
        LogInfoUtil.LogInfoPage("fileList");
        return new ModelAndView("fileList");
    }

    @RequestMapping(value = "/masterData", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView masterData() {
        LogInfoUtil.LogInfoPage("masterData");
        return new ModelAndView("masterData");
    }

    @RequestMapping(value = "/application", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView application() {
        LogInfoUtil.LogInfoPage("application");
        return new ModelAndView("application");
    }

    @RequestMapping(value = "/type", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView type() {
        LogInfoUtil.LogInfoPage("type");
        return new ModelAndView("type");
    }

    @RequestMapping(value = "contents", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView contents() {
        LogInfoUtil.LogInfoPage("contents");
        return new ModelAndView("contents");
    }
    
    @RequestMapping(value = "useUserList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView useUserList() {
        LogInfoUtil.LogInfoPage("useUserList");
        return new ModelAndView("useUserList");
    }
    
    @RequestMapping(value = "useUser114List", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView useUser114List() {
        LogInfoUtil.LogInfoPage("useUser114List");
        return new ModelAndView("useUser114List");
    }
    
    @RequestMapping(value = "useUserDownLoad", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView useUserDownLoad() {
        LogInfoUtil.LogInfoPage("useUserDownLoad");
        return new ModelAndView("useUserDownLoad");
    }

    @RequestMapping(value = "pushMessage", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView pushMessage() {
        LogInfoUtil.LogInfoPage("pushMessage");
        return new ModelAndView("pushMessage");
    }

    @RequestMapping(value = "generalPurpose", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView generalPurposeCtrl() {
        LogInfoUtil.LogInfoPage("generalPurpose");
        return new ModelAndView("generalPurpose");
    }
    
    @RequestMapping(value = "iyoPushMessage", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView iyoPushMessage() {
        LogInfoUtil.LogInfoPage("iyoPushMessage");
        return new ModelAndView("iyoPushMessage");
    }
    
    @RequestMapping(value = "iyoPushUserList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView iyoPushUserList() {
        LogInfoUtil.LogInfoPage("iyoPushUserList");
        return new ModelAndView("iyoPushUserList");
    }
    
    @RequestMapping(value = "/accountLoanList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView accountLoanList() {
        LogInfoUtil.LogInfoPage("accountLoanList");
        return new ModelAndView("accountLoanList");
    }
                                                  
    @RequestMapping(value = "/pushRecordLoanList", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView pushRecordLoanList() {
        LogInfoUtil.LogInfoPage("pushRecordLoanList");
        return new ModelAndView("pushRecordLoanList");
                                                        
    }

}
