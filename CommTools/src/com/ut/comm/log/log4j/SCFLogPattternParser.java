package com.ut.comm.log.log4j;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

import com.comm.pojo.ISessionEntity;
import com.ut.comm.tool.CommSessionUtil;
import com.ut.comm.tool.string.StringUtil;

/**
 * @see log 输出的时候，显示session id userid信息
 * @author PanHao
 *
 */
public class SCFLogPattternParser extends PatternParser{
	//对应配置文件中的 [%u] [%s] 
	private static final int CS_USER_ID = 'u';
	private static final int CS_SESSION_ID = 's';
	private static final int CS_THREAD_ID = 't';
	private static final int CS_BUSI_UNIT = 'b';
	
	public SCFLogPattternParser(String pattern)
	{
		super(pattern);
	}
	
	protected void finalizeConverter(char c)
	{
		PatternConverter pc = null;
		switch (c)
		{
			case CS_USER_ID:
			case CS_SESSION_ID:
			case CS_THREAD_ID:
			case CS_BUSI_UNIT:
			{
				pc = new SCFPatternConverter(formattingInfo, c);
				currentLiteral.setLength(0);
				addConverter(pc);
				break;
			}
			default:
				super.finalizeConverter(c);
		}			
	}

	private class SCFPatternConverter extends PatternConverter
	{
		char param = ' ';

		SCFPatternConverter(FormattingInfo formattingInfo, char type)
		{
			super(formattingInfo);
			this.param = type;
		}

		protected String convert(LoggingEvent event)
		{
			String str = null;
			ISessionEntity context = CommSessionUtil.getContext();
			switch (param)
			{
				case CS_USER_ID:
				{
					str = context.getUserId();
					if (StringUtil.isTrimEmpty(str)){
						str = "system";	
					}
					break;
				}
				case CS_SESSION_ID:{
					str = context.getSessId();
					if (StringUtil.isTrimEmpty(str)){
						str = "sys_session_id";	
					}
					break;
				}
				case CS_THREAD_ID:{
					str = Thread.currentThread().getId()+"";
					break;
				}
				case CS_BUSI_UNIT:{
					str = context.getSysBusiUnit();
					if(StringUtil.isTrimEmpty(str))
						str = "platform"; 
					break;
				}
			}
			if(str == null)
				str = "N/A";
			return str;
		}
	}

}
