package com.ut.comm.tool;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.xml.XMLManager;

public class ErrorUtil {

	private static HashMap hmErrMsg = null;

	public static String msgSetErr(String errMsg)
	{
		try
		{
			if (errMsg == null || errMsg.trim().length() < 1)
				return null;
			if (hmErrMsg == null)
				initHmErrMsg();

			Iterator it = hmErrMsg.keySet().iterator();
			String errKey = null;
			String errMsgvalue = null;
			while (it.hasNext())
			{
				errKey = (String) it.next();
				errMsgvalue = (String) hmErrMsg.get(errKey);

				if (errMsg.indexOf(errKey) > -1)
					return errMsgvalue;
			}
			return errMsg;
		} catch (Exception e)
		{
			return errMsg;
		}

	}

	private static synchronized void initHmErrMsg()
	{
		if (hmErrMsg != null)
			return;
		String bin = ASPathConst.USER_DIR_PATH;
		String errFilePath = bin + "/EE_SYS/ErrorRoot.xml";
		hmErrMsg = new HashMap();

		Document errDoc = null;
		try {
			errDoc = XMLManager.xmlFileToDom(errFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Element eleRoot = errDoc.getDocumentElement();
		NodeList nlErr = eleRoot.getChildNodes();

		String errLevel = null;
		String errCode = null;
		String errKey = null;
		String errMsg = null;
		Element eErr = null;
		StringBuffer msgBuf = null;
		int in = 0;
		for (int i = 0; i < nlErr.getLength(); i++) {
			Node nErr = nlErr.item(i);
			if (nErr.getNodeType() != Node.ELEMENT_NODE)
				continue;

			eErr = (Element) nErr;

			errLevel = eErr.getAttribute("level").trim();
			errCode = eErr.getAttribute("errCode").trim();
			errKey = eErr.getAttribute("errKey").trim();
			errMsg = XMLManager.getNodeValue(eErr, true);
			if (errMsg != null)
				errMsg = errMsg.trim();
			if ((errKey != null && errKey.length() > 0) && (errMsg != null && errMsg.length() > 0))
			{
				msgBuf = new StringBuffer("[");
				msgBuf.append(errLevel);
				msgBuf.append(":");
				msgBuf.append(errCode);
				msgBuf.append("]    ");
				in = errMsg.indexOf("\n");
				while (in > 0)
				{
					if (errMsg.substring(in - 2, in).equals("\r"))
					{
						errMsg = errMsg.substring(0, in - 2) + " " + errMsg.substring(in + 2).trim();
					} else
					{
						errMsg = errMsg.substring(0, in) + " " + errMsg.substring(in + 2).trim();
					}
					in = errMsg.indexOf("\n");
				}
				msgBuf.append(errMsg);
				errMsg = msgBuf.toString();
				hmErrMsg.put(errKey, errMsg);
			}
		}

	}

	public static String getErrorStackTrace(Exception e) {
		String err = "";
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(os);
			e.printStackTrace(pw);
			os.close();
			pw.close();
			err = os.toString();
			os = null;
			pw = null;
		} catch (Exception ee) {
		}
		return err;
	}
}