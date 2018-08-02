package com.advance.mgr.web.user;

import com.advance.mgr.dto.sys.SysMenuQueryDto;
import com.advance.mgr.model.sys.SysMenuModel;
import com.advance.mgr.service.SysMenuService;
import com.advance.mgr.util.JsonResultFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resources")
public class ResourceController {



    @Autowired
    private SysMenuService sysMenuService;


    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) throws Exception {
        List<SysMenuModel> menuList = sysMenuService.queryMenus(new SysMenuQueryDto());


        model.addAttribute("menus" , menuList);
        return "/resource/menuList";
    }

    @RequestMapping(value = "/listMenus", method = RequestMethod.GET)
    @ResponseBody
    public String getMenus() throws Exception {

        List<Map<String, String>> retList = Lists.newArrayList();
        Map<String, String> maps = null;
        for (int i = 0; i < 7 ; i++) {
            if (i == 0) {
                maps = Maps.newHashMap();
                maps.put("id", "132");
                maps.put("name", "管理中心");
                maps.put("pId", "null");
                retList.add(maps);
            } else if (i == 1) {
                maps = Maps.newHashMap();
                maps.put("id", "107");
                maps.put("name", "售后管理");
                maps.put("pId", "132");
                retList.add(maps);
            } else if (i == 2) {
                maps = Maps.newHashMap();
                maps.put("id", "417797414051651584");
                maps.put("name", "开思质保查询");
                maps.put("pId", "107");
                retList.add(maps);
            } else if (i == 3) {
                maps = Maps.newHashMap();
                maps.put("id", "417797117852315648");
                maps.put("name", "售后服务平台特殊设置");
                maps.put("pId", "107");
                retList.add(maps);
            } else if (i == 4) {
                maps = Maps.newHashMap();
                maps.put("id", "417797287207510016");
                maps.put("name", "商家售后服务查询");
                maps.put("pId", "107");
                retList.add(maps);
            } else if (i == 5) {
                maps = Maps.newHashMap();
                maps.put("id", "417797659527487488");
                maps.put("name", "开思假一罚N查询");
                maps.put("pId", "107");
                retList.add(maps);
            } else if (i == 6) {
                maps = Maps.newHashMap();
                maps.put("id", "417797760662958080");
                maps.put("name", "开思假一罚N管理");
                maps.put("pId", "107");
                retList.add(maps);
            }
        }

        return JsonResultFactory.toJson(retList);
    }

}
