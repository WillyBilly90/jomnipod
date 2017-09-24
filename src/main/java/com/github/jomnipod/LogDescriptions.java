/*
 * Copyright (c) 2017 Mattias Eklöf <mattias.eklof@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.github.jomnipod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.jomnipod.IBFRecord.Iterator;

public class LogDescriptions {

	private Byte logsInfoRevision;
	private Byte insulinHistoryRevison;
	private Byte alarmHistoryRevision;
	private Byte bloodGlucoseRevision;
	private Byte insuletStatsRevision;
	private Byte day;
	private Byte month;
	private Integer year;
	private Byte seconds;
	private Byte minutes;
	private Byte hours;

	private Map<Integer, Map<String, Integer>> descriptions = new HashMap<>();

	public LogDescriptions(IBFRecord record) throws IOException {
		Iterator iterator = record.iterator();

		logsInfoRevision = iterator.nextByte();
		insulinHistoryRevison = iterator.nextByte();
		alarmHistoryRevision = iterator.nextByte();
		bloodGlucoseRevision = iterator.nextByte();
		insuletStatsRevision = iterator.nextByte();
		day = iterator.nextByte();
		month = iterator.nextByte();
		year = iterator.nextUnsignedBEShort();
		seconds = iterator.nextByte();
		minutes = iterator.nextByte();
		hours = iterator.nextByte();
		int numOfLogDescriptions = iterator.nextUnsignedBEShort();

		for (int i = 0; i < numOfLogDescriptions; i++) {
			int logIndex = iterator.nextUnsignedBEShort();
			int backup = iterator.nextUnsignedBEShort();
			int location = iterator.nextUnsignedBEShort();
			int hasVariable = iterator.nextUnsignedBEShort();
			int recordSize = iterator.nextUnsignedBEShort();
			int firstIndex = iterator.nextSignedBEInteger();
			int lastIndex = iterator.nextSignedBEInteger();

			HashMap<String, Integer> properties = new HashMap<>();
			properties.put("backup", backup);
			properties.put("location", location);
			properties.put("hasVariable", hasVariable);
			properties.put("recordSize", recordSize);
			properties.put("firstIndex", firstIndex);
			properties.put("lastIndex", lastIndex);

			descriptions.put(logIndex, properties);
		}
	}

}
