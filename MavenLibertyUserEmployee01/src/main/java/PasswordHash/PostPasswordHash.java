package PasswordHash;

import static org.junit.Assert.assertFalse;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class PostPasswordHash {
	
	private static final Log Log = LogFactory.getLog(PostPasswordHash.class.getName());
	
	private static final String ALG = "SHA-256";
	private static final String FIXEDSALT = "aafsafrevgadsfergvadsefg44wefKYTJRAaawf356";
	
	@Test
	public void testGetPasswordHash() throws Exception{
		String userId1 = "0001";
		String userId2 = "0002";
		String pass1 = "password";
		String pass2 = "password";
		
		assertFalse( getPasswordHash(userId1, pass1).equals( getPasswordHash(userId2, pass2) ));
	}
	
	public String getPasswordHash(String userId, String password){
		return getHash(password, getSalt(userId) );
	}
	
	private String getHash(String target, String salt){
		return getHash(target + salt);
	}
	
	private String getHash(String target){
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance(ALG);
			md.update(target.getBytes());
			byte[] digest = md.digest();
			//byte[] -> String
			hash = new String(digest, "UTF-8");

		} catch (NoSuchAlgorithmException e) {
			Log.error(e.getLocalizedMessage(), e);
		} catch (UnsupportedEncodingException e) {
			Log.error(e.getLocalizedMessage(), e);
		}
		return hash;
	}

	private String getSalt(String userId){
		return userId + FIXEDSALT;
	}
	
}
