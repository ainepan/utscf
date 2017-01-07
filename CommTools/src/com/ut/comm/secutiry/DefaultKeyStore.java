package com.ut.comm.secutiry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ut.comm.tool.ErrorUtil;
import com.ut.comm.tool.string.StringUtil;

public class DefaultKeyStore implements IKeyStore{
	
	Logger logger = LoggerFactory.getLogger(DefaultKeyStore.class);
	
	 // The keystore type default keystore.type=jks
    private String type;

//    // The provider
//    private Provider provider;
    
    // The provider
    private String provider;

    // The provider implementation
    private KeyStoreSpi keyStoreSpi;

    // Has this keystore been initialized (loaded)?
    private boolean initialized = false;
    
    //keystore password
    private String password;
    
    //keystore file path
    private String keyFile;
    
    private KeyStore _keyStore;
    
//	protected AbsDefaultKeyStore(KeyStoreSpi keyStoreSpi, Provider provider, String type) {
//		super(keyStoreSpi, provider, type);
//	}
	
    public DefaultKeyStore(String type,String provider,String keyFile,String password){
    	this.type = type;
    	this.provider = provider;
    	this.keyFile = keyFile;
    	this.password = password;
    	
    	initilize();
    }
    
	public void initilize(){
		try{
			if (StringUtil.isTrimEmpty(keyFile)) {
				throw new Exception("证书文件无效.");
			}

			if (StringUtil.isTrimEmpty(type)) {
				type = "JKS";
			}

//			KeyStore keyStore = null;
			try {
				if (StringUtil.isTrimEmpty(provider)) {
					_keyStore = KeyStore.getInstance(type);
				} else {
					_keyStore = KeyStore.getInstance(type, provider);
				}
			} catch (KeyStoreException kse) {
				throw new Exception("证书类型无效:" + type);
			} catch (NoSuchProviderException e) {
				throw new Exception("证书类型提供者无效:" + provider);
			}

			InputStream bais = new FileInputStream(keyFile);
			try {
				_keyStore.load(bais, password.toCharArray());
			} catch (NoSuchAlgorithmException e1) {
				throw new Exception("证书算法无效:");
			} catch (CertificateException e1) {
				throw new Exception("加载证书失败:" + e1.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new Exception("证书地址无效:" + e1.toString());
			} finally {
				bais.close();
			}
		}catch(Exception e){
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}
	}

	@Override
	public Certificate getCertificate(String alias) throws KeyStoreException {
		return _keyStore.getCertificate(alias);
	}

	@Override
	public boolean containsAlias(String alias) throws KeyStoreException {
		return _keyStore.containsAlias(alias);
	}

	@Override
	public Key getKey(String alias, char[] cerPsdChar) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return _keyStore.getKey(alias, cerPsdChar);
	}

	public String getType() {
		return type;
	}

	public String getProvider() {
		return provider;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public String getPassword() {
		return password;
	}

	public String getKeyFile() {
		return keyFile;
	}

	@Override
	public void deleteEntry(String alias) throws KeyStoreException {
		_keyStore.deleteEntry(alias);
	}

	@Override
	public void store() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
		FileOutputStream output = new FileOutputStream(getKeyFile());
		try {
			_keyStore.store(output, getPassword().toCharArray());
		} finally {
			output.close();
		}
	}
	
	

}
