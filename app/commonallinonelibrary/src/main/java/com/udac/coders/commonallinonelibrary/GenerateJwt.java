package com.udac.coders.commonallinonelibrary;
import android.os.Build;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

public class GenerateJwt {
    public String Generate_Jwt() {
        String secret = "kr!sh@";
        Map<String, Object> header = new HashMap<>();
        header.put("typ", Header.JWT_TYPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try{
                return Jwts.builder().setIssuer("SutharSamajApp").setSubject("SutharSamajApp")
                        .setHeader(header)
                        .setExpiration(Date.from(Instant.now().plus(2l, ChronoUnit.MINUTES)))
                        .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode(secret))
                        .compact();
            }catch (Exception ex){
                return "";
            }
        }
        return "";
    }
}
