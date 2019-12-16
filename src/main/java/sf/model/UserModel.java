package sf.model;

import sf.entity.User;

public class UserModel {
    private String nickname;
    private String phone;
    private String address;

    public UserModel(User user) {
        this.phone = user.getPhone();
        this.nickname = user.getNickname();
        this.address = user.getAddress();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
