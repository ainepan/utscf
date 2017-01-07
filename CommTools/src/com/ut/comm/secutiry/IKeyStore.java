package com.ut.comm.secutiry;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public interface IKeyStore {

	public Certificate getCertificate(String alias) throws KeyStoreException;

	public boolean containsAlias(String alias) throws KeyStoreException;

	public Key getKey(String alias, char[] cerPsdChar) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException;

	public void deleteEntry(String alias) throws KeyStoreException;
	
	public String getType();

	public String getProvider() ;

	public boolean isInitialized();
	
	public String getPassword();

	public String getKeyFile();

	public void store() throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException;

}
