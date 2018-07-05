package com.advance.mgr.web.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.advance.mgr.util.JsonResultFactory;
import com.advance.mgr.web.user.model.Resource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/resources")
public class ResourceController {






    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) throws Exception {
        List<Resource> lists =Lists.newArrayList();
        Resource resource = null;
        for (int i = 0; i < 4 ; i++) {
            if (i == 0) {
                resource = new Resource();
                resource.setId("417797760662958080");
                resource.setName("开思假一罚N管理");
                resource.setResourceValue("/systemMgr/compensation/compensationManage");
                resource.setParentId("107");
                resource.setSequence(8);
                resource.setCode("COMPENSATION_INFO");
                lists.add(resource);
            } else if (i == 1) {
                resource = new Resource();
                resource.setId("417797659527487488");
                resource.setName("开思假一罚N查询");
                resource.setResourceValue("/systemMgr/compensation/compensationsearch");
                resource.setParentId("107");
                resource.setSequence(9);
                resource.setCode("COMPENSATION_INFO");
                lists.add(resource);
            } else if (i == 2) {
                resource = new Resource();
                resource.setId("417797414051651584");
                resource.setName("开思质保查询");
                resource.setResourceValue("/systemMgr/compensation/compensationManage");
                resource.setParentId("107");
                resource.setSequence(10);
                resource.setCode("COMPENSATION_INFO");
                lists.add(resource);
            }  else if (i == 3) {
                resource = new Resource();
                resource.setId("417797509149106176");
                resource.setName("开思质保管理");
                resource.setResourceValue("/systemMgr/compensation/compensationManage");
                resource.setParentId("107");
                resource.setSequence(11);
                resource.setCode("WARRANTY_MGR_INFO");
                lists.add(resource);
            }
        }
        model.addAttribute("menus" , lists);
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
