package au.org.hts.dashboard.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class HumeDashboardUtils {

	
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public static String elapsedTime(long milliseconds) {
		String elapsed = "N/A";
		if (milliseconds > 1000 * 60 * 60 * 24) {// days
			elapsed = (int) (Math.floor(milliseconds / (1000 * 60 * 60 * 24))) + " day";
		} else if (milliseconds > 1000 * 60 * 60) {// hours
			elapsed = Math.abs(milliseconds / (1000 * 60 * 60)) + " hr " + ((milliseconds % (1000 * 60))) / 10000
					+ " min";
		} else if (milliseconds > 1000 * 60) {// minutes
			elapsed = Math.abs(milliseconds / (1000 * 60)) + " min " + ((milliseconds % (1000 * 60))) / 1000 + " sec";
		} else { // seconds
			elapsed = Math.round(milliseconds / 1000) + " sec";
		}
		// System.out.println(elapsed);
		return elapsed;
	}

	// compare timestamp and check if new time is later one
	public static boolean getLaterTimestamp(String temp, String last) {
		boolean result = false;
		LocalDateTime tempTime = LocalDateTime.parse(temp, dtf);
		LocalDateTime lastTime = LocalDateTime.parse(last, dtf);
		result = tempTime.isBefore(lastTime);
		return result;
	}
	
	// format elapsed time
	public static String elapsedTimeExpression(String time) {
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d = null;
		try {
			d = formatter.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "N/A";
		}
		long elap = d.getTime();
		long current = System.currentTimeMillis();
		return elapsedTime(current-elap);
		
	}
		
	public static String getThemeColor(int type) {
		String color = HumeDashboardConstants.DANGER;
		if(type%5 == 0) {
			color = HumeDashboardConstants.PRIMARY;
		}else if(type%5 == 1) {
			color = HumeDashboardConstants.INFO;
		}else if(type%5 == 2) {
			color = HumeDashboardConstants.WARNING;
		}else if(type%5 == 3) {
			color = HumeDashboardConstants.SUCCESS;
		}else if(type%5 == 4) {
			color = HumeDashboardConstants.DANGER;
		}
		return color;
	}

	// combine two arrays
	public static Object[] arrayCombine(Object[] array1, Object[] array2) {
		Object[] both = null;
		both = Arrays.copyOf(array1, array1.length + array2.length);
		System.arraycopy(array2, 0, both, array1.length, array2.length);
		return both;
	}

	public static String convertDuraton(String time) {
		// String time = "PT122H16M26.808S";
		String duration = StringUtils.defaultString(time);
		if(duration.startsWith("PT")) {
			Duration d = Duration.parse(time);
			long sec = d.get(ChronoUnit.SECONDS);
			return displayTimeFormat(sec);
		}else {
			return "N/A";
		}
	}

	public static String convertISO8601(String time) {
		// String time = "P3Y2M19DT17H17M50.379S";
		String calTime = "";
		String period = StringUtils.substringBefore(time, "M");
		period = StringUtils.stripStart(period, "P");
		int year = Integer.parseInt(StringUtils.substringBefore(period, "Y"));
		int month = Integer.parseInt(StringUtils.substringAfter(period, "Y"));
		long yearSec = year * 60 * 60 * 24 * 365;
		long monthSec = month * 60 * 60 * 24 * 30;
		long periodSec = yearSec + monthSec;

//		if (periodSec > 0) {
//			calTime = displayTimeFormat(periodSec);
//			return calTime;
//		}

		String duration = StringUtils.substringAfter(time, "M");
		duration = "P" + duration;
		Duration dur = Duration.parse(duration);
		long durationSec = dur.getSeconds() + periodSec;
		calTime = displayTimeFormat(durationSec);
		return calTime;
	}

	public static String displayTimeFormat(long seconds) {
		long modulus = 0;
		String calcTime = "";
		if (seconds > 60) {
			long min = (long) (seconds / 60); // minute
			if (min > 60) {
				long hour = (long) (min / 60); // hour
				if (hour > 24) {
					long day = (long) (hour / 24); // day
					modulus = (long) (hour % 24);
					calcTime = Math.abs(day) + " d " + Math.abs(modulus) + " h";
				} else {
					modulus = (long) (min % 60);
					calcTime = Math.abs(hour) + " h " + Math.abs(modulus) + " m";
				}
			} else {
				modulus = (long) (seconds % 60);
				calcTime = Math.abs(min) + " m " + Math.abs(modulus) + " s";
			}
		} else {
			calcTime = Math.abs(seconds) + " s";
		}
		return calcTime;
	}

	// remove prefix on the name for pretty display
	public static String cleansingPrefix(String[] prefix, String name) {
		for (String pre : prefix) {
			name = StringUtils.removeStart(name, pre);
		}
		return name;
	}

	// check if CP name starts with defined value - CP.tcp_|CP.http_|CP.dir_FBI_Pick
	public static boolean checkCPName(String[] prefix, String name) {
		boolean result = false;
		for (String pre : prefix) {
			if (StringUtils.startsWithIgnoreCase(name, pre)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	// get portfolio name
	public static String getPortfolioName(String id) {
		String name = "";
		switch(id) {
			case HumeDashboardConstants.PORTFOLIO_PCMS :
				name = "PCMS";
				break;
			case HumeDashboardConstants.PORTFOLIO_CS :
				name = "CS";
				break;
			case HumeDashboardConstants.PORTFOLIO_CMS :
				name = "CMS";
				break;
			case HumeDashboardConstants.PORTFOLIO_RHEMS :
				name = "RHEMS";
				break;
		}
		return name;
	}
	
	
	// convert input to Oracle datetime format
	public static String convertDateTimeFormat(String input) {
		String converted = "01/01/1970 00:00 am";
		String oracleFormat = "dd/MMM/yy hh:mm:00.0000000 a";
		SimpleDateFormat uiFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
		Date in = null;
		try {
			in = uiFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		uiFormat.applyPattern(oracleFormat);
		converted = uiFormat.format(in);
		return converted;
	}
	
	// remove whitespace after digits
	public static String removeWhitespaceAfterDigits(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        boolean lastCharWasDigit = false;

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c);
                lastCharWasDigit = true;
            } else if (Character.isWhitespace(c) && lastCharWasDigit) {
                // Skip the whitespace
                lastCharWasDigit = false; // Reset the flag
            } else {
                result.append(c);
                lastCharWasDigit = false;
            }
        }
        return result.toString();
    }
	

}
