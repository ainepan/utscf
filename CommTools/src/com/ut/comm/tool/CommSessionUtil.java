package com.ut.comm.tool;

import com.comm.pojo.CommSessionContext;
import com.comm.pojo.ISessionEntity;

public class CommSessionUtil {
	static ThreadLocal<ISessionEntity> local = new ThreadLocal<ISessionEntity>();

	public static ISessionEntity initialize(Object contextDoc,ISessionEntity context) {
		context.initialize(contextDoc);
		local.set(context);
		return context;
	}
	
	public static ISessionEntity initialize(Object contextDoc) {
		ISessionEntity context = new CommSessionContext();
		context.initialize(contextDoc);
		local.set(context);
		return context;
	}

	public static ISessionEntity reflash(Object contextDoc,ISessionEntity context) {
		ISessionEntity lct = local.get();
		if (lct == null) {
			lct = context;
			local.set(lct);
		}
		lct.initialize(contextDoc);

		return lct;
	}

	public static ISessionEntity getContext() {
		ISessionEntity context = local.get();
		if(context==null){
			context = new CommSessionContext();
			context.initialize(null);
		}
			
		return context;
	}

	public static void setContext(ISessionEntity context) {
		ISessionEntity _context = local.get();
		if (_context != null) {
			_context.destroy();
			_context = null;
			local.set(null);
		}

		local.set(context);
	}

	public static void close() {
		ISessionEntity context = local.get();
		if (context != null) {
			context.destroy();
			context = null;
		}
		local.set(null);
	}
}
