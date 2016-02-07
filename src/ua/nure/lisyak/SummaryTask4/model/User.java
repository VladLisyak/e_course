package ua.nure.lisyak.SummaryTask4.model;

import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;

import java.util.Set;

/**
 * User class.
 *
 * @author Dmitry Bekuzarov
 */
public class User extends Entity {

    private String name;

    private String email;

    private String login;

    private String image;

    private String password;

    private Enabled enabled;

    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public Enabled getEnabled() {
        return enabled;
    }

    public void setEnabled(Enabled enabled) {
        this.enabled = enabled;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
