package tokenexample;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

public class TokenGenerator {

	public static void main(String[] args) throws Exception {
		TokenGenerator tokenGenerator = new TokenGenerator();
		PrivateKey privateKey = tokenGenerator.getPemPrivateKey("C:\\pemfile\\private.pem", "RSA");
		
		/*
		 * Sign the key..
		 */
		
		JWTSigner jwtSigner = new JWTSigner(privateKey);
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", "12345");
        claims.put("name", "John Doe");
        claims.put("admin", "true");
        JWTSigner.Options options = new JWTSigner.Options();
        options.setAlgorithm(Algorithm.RS256);
        String jwtSigned = jwtSigner.sign(claims, options);

        System.out.println("Signed: " + jwtSigned);
        
        PublicKey publicKey = tokenGenerator.getPemPublicKey("C:\\pemfile\\pubkey.pem","RSA");
        JWTVerifier verifier = new JWTVerifier(publicKey);
        try {
            verifier.verify(jwtSigned);
            System.out.println("Verify ok!");
        } catch (Exception e) {
            System.out.println("Verify fail!");
            e.printStackTrace();
        }

	}
	
	/*
	 * Commad to create RSA private key in OpenSSL (pkcs#8)
	 * openssl genrsa 2048 | openssl pkcs8 -topk8 -nocrypt -out private.pem
	 * 
	 */
	
	public PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();

		String temp = new String(keyBytes);
		String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
		privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
		System.out.println("Private key\n" + privKeyPEM);
		Base64 base64 = new Base64();
		byte[] decoded = base64.decode(privKeyPEM.getBytes());

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePrivate(spec);
	}
	/*
	 * OpenSSL commanad 
	 * openssl rsa -in private.pem -pubout -out pubkey.pem
	 */
	public  PublicKey getPemPublicKey(String filename, String algorithm) throws Exception {
	      File f = new File(filename);
	      FileInputStream fis = new FileInputStream(f);
	      DataInputStream dis = new DataInputStream(fis);
	      byte[] keyBytes = new byte[(int) f.length()];
	      dis.readFully(keyBytes);
	      dis.close();

	      String temp = new String(keyBytes);
	      String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
	      publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");


	      Base64 b64 = new Base64();
	      byte [] decoded = b64.decode(publicKeyPEM);

	      X509EncodedKeySpec spec =
	            new X509EncodedKeySpec(decoded);
	      KeyFactory kf = KeyFactory.getInstance(algorithm);
	      return kf.generatePublic(spec);
	 }

}
