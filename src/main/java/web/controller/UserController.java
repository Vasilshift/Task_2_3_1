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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping()

    public String allUsers(Model model) {
        model.addAttribute("userList", userService.allUsers());
        return "users/allUsers";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute(userService.get(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(required = false) String roleAdmin,
                      @RequestParam(required = false) String roleGuest) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        if (roleAdmin != null  && roleGuest.equals("ROLE_GUEST")){
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleGuest != null && roleGuest.equals("ROLE_GUEST")) {
            roles.add(roleService.getRoleByName("ROLE_GUEST"));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        User user = userService.get(id);
        Set<Role> roles = user.getRoles();

        for (Role role: roles) {
            if (role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
            if (role.equals(roleService.getRoleByName("ROLE_GUEST"))) {
                model.addAttribute("roleGuest", true);
            }
        }
        model.addAttribute("user", userService.get(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(required = false) String roleAdmin,
                         @RequestParam(required = false) String roleGuest) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        if (roleAdmin != null && roleAdmin .equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleGuest != null && roleGuest.equals("ROLE_GUEST")) {
            roles.add(roleService.getRoleByName("ROLE_GUEST"));
        }
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
