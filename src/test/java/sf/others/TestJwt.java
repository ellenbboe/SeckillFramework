package sf.others;


import sf.util.JwtUtil;

import java.util.Date;

public class TestJwt {


    public static void main(String[] args) {
        Date a = new Date();
        Date b = new Date(a.getTime()+1);
        System.out.println(b);
    }
}
