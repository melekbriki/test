package com.alibou.jwtsecuritys.auth;

import com.alibou.jwtsecuritys.Entities.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role = Role.USER; // Set the role to USER by default
    private boolean mfaEnabled;




    public Integer getid() {

        return id;
    }
    public void setid(Integer id) {

        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}