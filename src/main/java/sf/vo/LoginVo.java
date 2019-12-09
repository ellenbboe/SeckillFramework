package sf.vo;

import sf.validator.IsMobile;

import javax.validation.constraints.NotNull;

//登录认证
public class LoginVo {
    @NotNull
    @IsMobile
    private String phone;

    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
