package com.sveil.other.web.users;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sveil.other.common.util.JsonFilesUtil;
import com.sveil.other.web.users.model.User;

@Controller
@RequestMapping("/users")
public class UsersController {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @RequestMapping("/index")
    public String index(Model model) {
        list(model);
        return "users/index";
    }

    public void list(Model model) {
        List<User> userList = new ArrayList<User>();
        String tableStr = "userstable";
        String basePath = this.getClass().getResource("/").getPath();
        String pathFile = basePath + "sql/usersdb.json";

        // 获得json文件的内容
        String jsonStr = JsonFilesUtil.load(pathFile);
        jsonStr = JsonFilesUtil.removeJsonField(tableStr, jsonStr);
        userList = JsonFilesUtil.jsonToList(jsonStr, User.class);
        model.addAttribute("userList", userList);
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "users/login";
    }
}
