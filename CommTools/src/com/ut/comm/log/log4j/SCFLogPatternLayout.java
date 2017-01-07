package com.ut.comm.log.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

public class SCFLogPatternLayout extends PatternLayout {
	private String pattern;
	private StringBuffer sbuf = new StringBuffer(BUF_SIZE);
	private PatternConverter head;
	
	public void setConversionPattern(String conversionPattern)
	{
		pattern = conversionPattern;
		head = createPatternParser(conversionPattern).parse();
	}

	public String getConversionPattern()
	{
		return pattern;
	}

	protected PatternParser createPatternParser(String pattern)
	{
		return new SCFLogPattternParser(pattern);
	}

	public String format(LoggingEvent event)
	{
		if (sbuf.capacity() > MAX_CAPACITY)
		{
			sbuf = new StringBuffer(BUF_SIZE);
		}
		else
		{
			sbuf.setLength(0);
		}

		PatternConverter c = head;

		while (c != null)
		{
			c.format(sbuf, event);
			c = c.next;
		}
		return sbuf.toString();
	}
}
