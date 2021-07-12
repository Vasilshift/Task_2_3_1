package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")

    public String allUsers(Model model) {
        model.addAttribute("userList", userService.allUsers());
        return "admin/allUsers";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute(userService.get(id));
        return "admin/show";
    }

    @GetMapping("/users/new_user")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new_user";
    }

    @PostMapping("/users")
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(required = false) String roleAdmin,
                      @RequestParam(required = false) String roleUser) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleUser != null && roleUser.equals("ROLE_USER")) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        User user = userService.get(id);
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            if (role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
            if (role.equals(roleService.getRoleByName("ROLE_USER"))) {
                model.addAttribute("roleUser", true);
            }
        }
        model.addAttribute("user", userService.get(id));
        return "admin/edit";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(required = false) String roleAdmin,
                         @RequestParam(required = false) String roleUser) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleUser != null && roleUser.equals("ROLE_USER")) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
