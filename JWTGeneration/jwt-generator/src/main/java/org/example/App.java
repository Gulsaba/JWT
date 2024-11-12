package org.example;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Hello world!
 */
public class App {
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Generated JWT Token:"+generateJWT());
	}
		
	 public static String generateJWT() throws Exception {
	        var privateKey = readPrivateKey(new File("C:\\Users\\this\\Downloads\\rsa_key.p8"));
	        RSAPublicKeySpec publicKeySpec =new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
	        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

	        var qualifiedUserName =
	            "FU40968"
	                + "."
	                + "JITENDRA";

	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	       
	        var publicKeyFp =
	                "SHA256:" + Base64.getEncoder().encodeToString(digest.digest(publicKey.getEncoded()));
	        
	        var issuedTs = new Date();
	        var expiresTs = new Date(issuedTs.getTime() + TimeUnit.HOURS.toMillis(1));
	        return JWT.create()
	            .withIssuer(qualifiedUserName + "." + publicKeyFp)
	            .withSubject(qualifiedUserName)
	            .withIssuedAt(issuedTs)
	            .withExpiresAt(expiresTs)
	            .sign(algorithm);
	      }
	    
	    private static RSAPrivateCrtKey readPrivateKey(File file) throws Exception {
	        String key = Files.readString(file.toPath(), Charset.defaultCharset());
	        
	        //key="MIIEpQIBAAKCAQEAsB7yuXJ0RfiXxjRMJvS/nI2dnsd5hekQGlDbKjFFoWcYyxr9R1g/jqC9xD0+1lY32zi+SopHExw55+BsM727eBUlkZAuURzOmc7gwj4wr+0vJyUWbY2SOPya7HrZ+7LE8pwnhBAP2/fV0hMhk0vcbqqjbFrtu4qOaIF7neYPNV4muHIRo5zooEEmUfdiFaMS51VbSUh8VrOrP3l8lNs1vdyoa9M1hwPeqXOnwCn1EbmOV+robCqLxejB6bXZXe/KouLK4MP8MWPyS3THep0wnQ6naIDjl/P1/I9+yHyVuyBnwW+/5oCR17aaPyAxlTOr3lR1SPtAa8Vs5gKGzByxIwIDAQABAoIBAHVJZuNQ8VXXo1t/YGdg/mnHdXxT4v3mo66iwhFrFq/R9JTtuFZW0pytNzMEhmA+spvvQepqA8MaPQXKUsRySfJCQu6ZUf76bCvJtawltk6yaYBsFm+GpCYHMzTg62HPP5FBpJ8yAx2+AksQKn/pOTcJtCNouoD2WLgH4sbYerBSk4I2Oyz78LmUpq5B90LVbY1R9bDD20G7w+GY/ShhUFzxcTpNppSHffB3Gzo69p4IpyFvmQSvBm1b4legIO6fHSXAAxoC5Jjm3s6WKkmoq0wCFn695LZ0S/p+QD1IKbe6wUVaDqHloB1Y+Qxx/uXWv0ZdT4U9tZCN10w40117POECgYEA437+9Tzi6aBzbpcY/I1DY1gTNdyfgcMB+APh7uoq/HT2mMM76/+cshslMVyFLEjmrKs9vAOQfQz4Ppgpqo5UMZplmfWcCP4bUAViTobyLowrMI/qsSu8uM5ZlDWkfp+1sfQ23ti5TwFSZeEliBaov0IA+2KmyS8LfUGCAh/e6Z0CgYEAxjAT8EvX+KHH8cJfeozr5VHWFVwuJvVx5V1Bc/2dMPc9Bi5hjYK4ySsxywLHAt82GQ6v4XewHhaQpY2k6LxSQVM14iSUqQNOUEN2TO/liqM3Zgq/VH0CUQ92KgrmUH9FThKy4nA5yyMzk3nbuATDKFD7/ybwSaLcwWLCW0t3ab8CgYEAwwlLmABdIaJ3lCZiO5L4ZSFLRvAgp+UBuRnLB0MnG01DZPBsXQXF10RCfLEvUyok6X/d62S66RrHcRKPFjPzppsR5A6XeXYxAEe9Ykl7M8Dj/jxEHyS6JU5zqgbbQdu2sBwJNsBjDoK5m7KMtMGIZoA1y09mYjC9DuOsjBm6yFkCgYEAxIx7ya0+A/tWEuJk1GIbtLKDcW2Y69QxX3or5n+XvC8GWzH2V2RUJFXtLNuVSLjbquAQ97c+d6iDTVx7NsFFhjmiz6Ldt/xqV1mAYm7sI6EY2N9BvuW3PXLrCmkC3sRJlFMhC0anWlmP1etdR1OZxEsEj5grZ6vX6QYroGToiXsCgYEAkHj8EANSULVAmeUjkCcldCkjq7imnFK+BR1jJLDfftojxPefgkYkfC6NCRU1MWi+prdWRN2USMe3mRUsHRplrbO3dbrfajHMx4QCI6ptsGiL8+fcjAlcEx61Ut22Pfepk4xVhyjwFu9z/KN1L7jfKnN+f0JXmCLXsvzYQZtSfPg=";

	        String privateKeyPEM =
	        		key.replace("-----BEGIN PRIVATE KEY-----", "") 
	                .replace("\n", "")
	                .replace("\r", "")
	                .replace("\r\n", "")
	                .replace("-----END PRIVATE KEY-----", "");
	        
	        System.out.println("privateKeyPEM is :: \n"+privateKeyPEM);

	        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
	        return (RSAPrivateCrtKey) keyFactory.generatePrivate(keySpec);
	      }
}
