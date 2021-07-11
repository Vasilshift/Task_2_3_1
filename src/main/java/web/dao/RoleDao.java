package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);

    Role getRoleById(int id);

    List<Role> allRoles();

    Role getDefaultRole();
}
