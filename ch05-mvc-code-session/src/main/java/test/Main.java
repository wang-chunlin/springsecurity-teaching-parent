package test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import util.JwtUtil;

import java.security.Key;

/**
 * @author cj
 * @date 2020/1/3
 */
public class Main {
    public static void main(String[] args) {
        //simpleJwtDemo();



        try {
            JwtUtil util = new JwtUtil();
            String jwt = util.createJWT( "test spring security", 5*1000);
            System.out.println("JWT：" + jwt);

            System.out.println("\n解密\n");

            Claims c = util.parseJWT(jwt);
           // System.out.println(c.getId());
           // System.out.println(c.getIssuedAt());
            System.out.println(c.getSubject());
          //  System.out.println(c.getIssuer());
            //System.out.println(c.get("uid", String.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void simpleJwtDemo() {
        Key key = MacProvider.generateKey();
        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        //System.out.println(compactJws);

        String result =  Jwts.parser().setSigningKey(key)
                 .parseClaimsJws(compactJws)

                 .getBody().getSubject();
        System.out.println("-----debug: result = " + result);
    }
}





