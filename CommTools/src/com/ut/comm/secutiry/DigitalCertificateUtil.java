package com.ut.comm.secutiry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ut.comm.tool.EncryptUtil;
import com.ut.comm.tool.string.StringUtil;

public class DigitalCertificateUtil {
	
	public static Logger logger = LoggerFactory.getLogger(DigitalCertificateUtil.class);
	/**
	 * 
	 * @param keyFilePath
	 *            : 本地证书路径+文件名
	 * @param type
	 *            : 证书类型，默认为 X.509
	 * @return base64 带回车换行
	 * @throws Exception
	 */
	public static String generatePublicKey(String keyFilePath, String type) throws Exception {
		if (StringUtil.isTrimEmpty(keyFilePath)) {
			throw new Exception("证书文件无效.");
		}

		if (StringUtil.isTrimEmpty(type)) {
			type = "X.509";
		}

		try {
			CertificateFactory certificatefactory = CertificateFactory.getInstance(type);
			FileInputStream bais = new FileInputStream(keyFilePath);
			try {
				X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
				PublicKey pk = cert.getPublicKey();
				
				return EncryptUtil.encryptString(pk.getEncoded(), true);
			} catch (CertificateException e) {
				throw new Exception("证书类型与证书文件不匹配，无法解析。 Type:" + type + " File:" + keyFilePath);
			}

		} catch (CertificateException e) {
			throw new Exception("证书类型无效:" + type);
		} catch (FileNotFoundException e) {
			throw new Exception("证书文件无效:" + keyFilePath);
		}
	}

	/**
	 * 
	 * @param keyFilePath
	 *            : 本地证书路径+文件名
	 * @param type
	 *            : 证书类型，默认为 X.509
	 * @return base64 带回车换行
	 * @throws Exception
	 */
	public static String getPublicKey(IKeyStore keyStore, String alias) throws Exception {
		Certificate cert = keyStore.getCertificate(alias);
		PublicKey publicKey = cert.getPublicKey();
		return EncryptUtil.encryptString(publicKey.getEncoded(), true);
	}
	/**
	 * 
	 * @param keyFilePath
	 * @param type
	 *            :JKS, JCEKS, PKCS12, BKS，UBER
	 * @param provider
	 * @param ksdPsd
	 * @param alias
	 * @param cerPsd
	 * @return base64 带回车换行
	 * @throws Exception
	 */
	public static String getPrivateKey(String keyFilePath, String type, String provider, String ksdPsd, String alias,
			String cerPsd) throws Exception {
		if (StringUtil.isTrimEmpty(keyFilePath)) {
			throw new Exception("证书文件无效.");
		}

		if (StringUtil.isTrimEmpty(type)) {
			throw new Exception("证书类型缺失.");
		}

		KeyStore keyStore = null;
		try {
			if (StringUtil.isTrimEmpty(provider)) {
				keyStore = KeyStore.getInstance(type);
			} else {
				keyStore = KeyStore.getInstance(type, provider);
			}
		} catch (KeyStoreException kse) {
			throw new Exception("证书类型无效:" + type);
		} catch (NoSuchProviderException e) {
			throw new Exception("证书类型提供者无效:" + provider);
		}

		InputStream bais = new FileInputStream(keyFilePath);
		try {
			keyStore.load(bais, ksdPsd.toCharArray());
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

		try {
			// 取个人公钥用
			// Certificate cert = keyStore.getCertificate(alias);
			// PublicKey publicKey = cert.getPublicKey();
			// System.out.println("pubKey:"+publicKey);
			char[] cerPsdChar = StringUtil.isTrimEmpty(cerPsd) ? null : cerPsd.toCharArray();

			if (keyStore.containsAlias(alias)) {
				PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, cerPsdChar);
				return EncryptUtil.encryptString(privateKey.getEncoded(), true);
			} else {
				logger.error("cannot found alias" + alias);
			}
			return null;

		} catch (KeyStoreException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (UnrecoverableKeyException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		}
	}
	
	public static String getPrivateKey(IKeyStore keyStore, String alias, String cerPsd) throws Exception {
		try {
			char[] cerPsdChar = StringUtil.isTrimEmpty(cerPsd) ? null : cerPsd.toCharArray();

			if (keyStore.containsAlias(alias)&&	(keyStore.getKey(alias, cerPsdChar) instanceof PrivateKey) ) {
				PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, cerPsdChar);
				return EncryptUtil.encryptString(privateKey.getEncoded(), true);
			} else {
				logger.error("cannot found alias" + alias);
			}
			return null;
		} catch (KeyStoreException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (UnrecoverableKeyException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		}
	}

	public static String deletePrivateKey(String keyFilePath, String type, String provider, String ksdPsd, String alias)
			throws Exception {
		if (StringUtil.isTrimEmpty(keyFilePath)) {
			throw new Exception("证书文件无效.");
		}

		if (StringUtil.isTrimEmpty(type)) {
			throw new Exception("证书类型缺失.");
		}

		KeyStore keyStore = null;
		try {
			if (StringUtil.isTrimEmpty(provider)) {
				keyStore = KeyStore.getInstance(type);
			} else {
				keyStore = KeyStore.getInstance(type, provider);
			}
		} catch (KeyStoreException kse) {
			throw new Exception("证书类型无效:" + type);
		} catch (NoSuchProviderException e) {
			throw new Exception("证书类型提供者无效:" + provider);
		}

		InputStream bais = new FileInputStream(keyFilePath);
		try {
			keyStore.load(bais, ksdPsd.toCharArray());
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

		try {
			if (keyStore.containsAlias(alias)) {
				keyStore.deleteEntry(alias);

				FileOutputStream output = new FileOutputStream(keyFilePath);
				try {
					keyStore.store(output, ksdPsd.toCharArray());
				} catch (NoSuchAlgorithmException e1) {
					throw new Exception("证书算法无效:");
				} catch (CertificateException e1) {
					throw new Exception("加载证书失败:" + e1.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
					throw new Exception("证书地址无效:" + e1.toString());
				} finally {
					output.close();
				}
			} else {
				logger.error("cannot found alias" + alias);
			}
			return null;

		} catch (KeyStoreException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (UnrecoverableKeyException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		}
	}
	
	public static String deletePrivateKey(IKeyStore keyStore, String alias)throws Exception {
		try {
			if (keyStore.containsAlias(alias)) {
				
				keyStore.deleteEntry(alias);

				try {
					keyStore.store();
				} catch (NoSuchAlgorithmException e1) {
					throw new Exception("证书算法无效:");
				} catch (CertificateException e1) {
					throw new Exception("加载证书失败:" + e1.toString());
				} catch (IOException e1) {
					throw new Exception("证书地址无效:" + e1.toString());
				} finally {
				}
			} else {
				logger.error("cannot found alias" + alias);
			}
			return null;
		} catch (KeyStoreException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (UnrecoverableKeyException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("获取私钥失败:" + e.toString());
		}
	}

	public static void export(String keyStoreType, String keystoreFile, String password, String alias,
			String exportedFile) throws Exception {
		KeyStore keystore = KeyStore.getInstance(keyStoreType);
//		BASE64Encoder encoder = new BASE64Encoder();
		keystore.load(new FileInputStream(keystoreFile), password.toCharArray());
		String privateKey = getPrivateKey(keyStoreType,keystoreFile,"",password, alias, password);
//		PrivateKey privateKey = keyPair.getPrivate();
//		String encoded = encoder.encode(privateKey);
		FileWriter fw = new FileWriter(exportedFile);
		fw.write(privateKey);
		fw.write("\n");
		fw.close();
	}

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
//	public static final String SIGN_ALGORITHMS = "SHA256withRSA";

	public static void main(String[] args) {
		String publicKeyFile = "D:\\ca\\bocsh_win演练.cer";
		// String privateKeyFile = "D:\\ca\\CFCAP50401boczhejiang-演练.cer";
		String privateKeyFile = "D:\\ca\\e-chain-sh.jks";
		
		String alias = "cn=22.196.66.43,ou=bocic,o=bank of china,c=cn";
		
		String password = "BOC8PKI";

		String input = "client_id=000_bf7d1afcbf4e40dca0fe6330fde7a999&client_secret=f0bca57316ed465b9b7ac71840e5bf2f&code=b8a157f8-37e0-3458-9770-fd6e37b5d1ec&grant_type=authorization_code&redirect_uri=http://22.196.66.8/SCFWebUI/doLogin";
//		String singMsg ="v/ZwuycXEmemaEEYm5I3dBxy5ZMvZuzNa58mxyQgI9iW+/yalUIhvX4did76XNyF79qTPBO8mG6CFG3rwRqo8Lj/Hk6Zm1klceAFlYhd/ykPF9V6oGkR2ihBrA3qQZpSDXw4ENd/1Ph5mDiTC9ooIfVA3H0GMRlwZqguUDWBVFvTpB5rNiAQgg8VbKil9H4O31H2jY1XYW0EaHUSLTIepbOm/6FxWXFftl/aDlAv2tlSnqA/weqsl1iakoV58mHN9z41RbWcNDA7UPuz0httYfBJo/R7/OwRUhV4rroc4hqHB8PgD21LIi/7SQA6Iw3X3V9qXx3d9AJGEmaSb5HKhQ==";
//		IKeyStore keyStore = new DefaultKeyStore("JKS", "SUN", publicKeyFile,  "BOC8PKI");
		 
		try {

			String pubKey = DigitalCertificateUtil.generatePublicKey(publicKeyFile, "");
			System.out.println("pubKey:" + pubKey);

//			IKeyStore keyStore = new DefaultKeyStore("JKS", "", privateKeyFile, "Zyyl1112");
//			String pubKey = DigitalCertificateUtil.getPublicKey(keyStore, alias);
//			System.out.println("pubKey:" + pubKey);

			// String priKey =
			// DigitalCertificateUtil.getPrivateKey(privateKeyFile, "JKS", "",
			// "Zyyl1112", "{ccd66cb1-bfd9-4968-969f-b845dcdae7ff}",
			// "Zyyl1112");
			// System.out.println("priKey:"+priKey);

			// String priKey =
			// DigitalCertificateUtil.getPrivateKey(keyStore,alias, "BOC8PKI");
			// System.out.println("priKey:"+priKey);
			//
			String singMsg = DigitalCertificateUtil.sign("JKS", "SUN", privateKeyFile, password, alias,password,input,SIGN_ALGORITHMS);
			System.out.println("singMsg:" + singMsg);

			boolean verify = DigitalCertificateUtil.doCheck(input, singMsg, publicKeyFile, "",SIGN_ALGORITHMS);
			System.out.println("verify check:" + verify);

			// IKeyStore keyStore = new DefaultKeyStore("", "", privateKeyFile,
			// "Zyyl1112");
			//
			// String singMsg =
			// DigitalCertificateUtil.sign(keyStore,"{ccd66cb1-bfd9-4968-969f-b845dcdae7ff}",
			// "Zyyl1112", input);
			// System.out.println("singMsg:" + singMsg);
			// input+="1";
			// boolean verify = DigitalCertificateUtil.doCheck(input, singMsg,
			// publicKeyFile, "");
			// System.out.println("verify:" + verify);

			// export();
//			String response = "{\"biz_content\":\"\",\"header\":{\"code\":\"fail\",\"msg\":\"System error, please contact us to solve the error\"},\"sign\":\"\"}";
//			JSONObject jsonBody = JsonUtil.getJSON(response);
//			String bizContent = jsonBody.containsKey("biz_content") ? jsonBody.get("biz_content").toString() : "";
//			System.out.println(bizContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String sign(String keyStoreType, String provider, String keyFilePath,String keyStrorePsd,
			String keyAliase, String keyPsd, String input,String signAlgorithms) throws Exception {

		if (StringUtil.isTrimEmpty(keyFilePath)) {
			throw new Exception("证书文件无效.");
		}

		if (StringUtil.isTrimEmpty(keyStoreType)) {
			throw new Exception("证书类型缺失.");
		}

		KeyStore keyStore = null;
		try {
			if (StringUtil.isTrimEmpty(provider)) {
				keyStore = KeyStore.getInstance(keyStoreType);
			} else {
				keyStore = KeyStore.getInstance(keyStoreType, provider);
			}
		} catch (KeyStoreException kse) {
			throw new Exception("证书类型无效:" + keyStoreType);
		} catch (NoSuchProviderException e) {
			throw new Exception("证书类型提供者无效:" + provider);
		}

		InputStream bais = new FileInputStream(keyFilePath);
		try {
			keyStore.load(bais, keyStrorePsd.toCharArray());
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

		try {
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAliase, keyPsd.toCharArray());// 获取私钥
			X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyAliase);// 获取证书
			
			if(StringUtil.isTrimEmpty(signAlgorithms)){
				signAlgorithms = certificate.getSigAlgName();
				if(StringUtil.isTrimEmpty(signAlgorithms)){
					signAlgorithms = SIGN_ALGORITHMS;
				}
			}
			// 构建签名
			Signature signature = Signature.getInstance(signAlgorithms);
			signature.initSign(privateKey);
			signature.update(input.getBytes());
			return EncryptUtil.encryptString(signature.sign());
		} catch (KeyStoreException e) {
			throw new Exception("别名无效:");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("算法无效:");
		} catch (UnrecoverableKeyException e) {
			throw new Exception("别名无效:");
		} catch (InvalidKeyException e) {
			throw new Exception("初始化签名器失败:" + e.toString());
		} catch (SignatureException e) {
			throw new Exception("签名失败:" + e.toString());
		}
	}
	
	public static String sign(IKeyStore keyStore, String keyAliase, String keyPsd, String input,String signAlgorithms) throws Exception {
		try {
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAliase, keyPsd.toCharArray());// 获取私钥
			X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyAliase);// 获取证书
			// 构建签名
//			Signature signature = Signature.getInstance(certificate.getSigAlgName());

			if(StringUtil.isTrimEmpty(signAlgorithms)){
				signAlgorithms = certificate.getSigAlgName();
				if(StringUtil.isTrimEmpty(signAlgorithms)){
					signAlgorithms = SIGN_ALGORITHMS;
				}
			}
			
			Signature signature = Signature.getInstance(signAlgorithms);
			signature.initSign(privateKey);
			signature.update(input.getBytes());
			return EncryptUtil.encryptString(signature.sign(),true);
		} catch (KeyStoreException e) {
			throw new Exception("别名无效:");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("算法无效:");
		} catch (UnrecoverableKeyException e) {
			throw new Exception("别名无效:");
		} catch (InvalidKeyException e) {
			throw new Exception("初始化签名器失败:" + e.toString());
		} catch (SignatureException e) {
			throw new Exception("签名失败:" + e.toString());
		}
	}

	public static boolean doCheck(String content, String sign, String publicKeyFile, String type,String signAlgorithms) throws Exception {

		if (StringUtil.isTrimEmpty(publicKeyFile)) {
			throw new Exception("证书文件无效.");
		}

		if (StringUtil.isTrimEmpty(type)) {
			type = "X.509";
		}

		try {
			CertificateFactory certificatefactory = CertificateFactory.getInstance(type);
			FileInputStream bais = new FileInputStream(publicKeyFile);
			try {
				X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
				PublicKey pk = cert.getPublicKey();

				if(StringUtil.isTrimEmpty(signAlgorithms)){
					signAlgorithms = cert.getSigAlgName();
					if(StringUtil.isTrimEmpty(signAlgorithms)){
						signAlgorithms = SIGN_ALGORITHMS;
					}
				}
				Signature signature = Signature.getInstance(signAlgorithms);
				signature.initVerify(pk);
				signature.update(content.getBytes());

				boolean bverify = signature.verify(EncryptUtil.decryptByte(sign));
				return bverify;
			} catch (CertificateException e) {
				throw new Exception("证书类型与证书文件不匹配，无法解析。 Type:" + type + " File:" + publicKeyFile);
			} finally {
				bais.close();
			}

		} catch (CertificateException e) {
			throw new Exception("证书类型无效:" + type);
		} catch (FileNotFoundException e) {
			throw new Exception("证书文件无效:" + publicKeyFile);
		}
	}
	
	 public static void export() {  
		  
	        String[] arstringCommand = new String[] {  
	  
	        "cmd ", "/k",  
	                "start", // cmd Shell命令  
	  
	                "keytool",  
	                "-export", // - export指定为导出操作   
	                "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore  
	                "D:\\ca\\e-chain-sh.keystore",  
	                "-alias", // -alias指定别名，这里是ss  
	                "cn=22.196.66.43,ou=bocic,o=bank of china,c=cn",  
	                "-file",//-file指向导出路径  
	                "D:\\ca\\chain-sh-pub.cer",  
	                "-storepass",// 指定密钥库的密码  
	                "BOC8PKI"  
	                  
	        };  
	        execCommand(arstringCommand);  
	    } 
	 
	 public static void execCommand(String[] arstringCommand) {  
	        try {  
	            Runtime.getRuntime().exec(arstringCommand);  
	              
	        } catch (Exception e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  
}
