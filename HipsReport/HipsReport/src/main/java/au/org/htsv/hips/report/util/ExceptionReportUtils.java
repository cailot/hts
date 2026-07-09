package au.org.htsv.hips.report.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;

/**
 * @author js278
 *
 */
public class ExceptionReportUtils {

	private static final DateTimeFormatter ARCHIVE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static List acronyms;
	
	public static List hospitals;
	
	public static List<ExceptionSimpleData> getDropdownList(String contents) {
		List<ExceptionSimpleData> list = new ArrayList<ExceptionSimpleData>();
		StringTokenizer st = new StringTokenizer(contents, ",");
		while (st.hasMoreTokens()) {
			String hospital = st.nextToken();
			String[] values = StringUtils.split(hospital, "_");
			ExceptionSimpleData data = new ExceptionSimpleData(values[0], values[1]);
			list.add(data);
		}
		return list;
	}



	
	/**
	 * Return contained context based on passed value
	 * @param data
	 * @param value
	 * @param defaultMsg
	 * @return
	 */
	public static String getExceptionValue(List<ExceptionSimpleData> data, String value, String defaultMsg) {
		String map = StringUtils.defaultString(defaultMsg);
		for (ExceptionSimpleData item : data) {
			if (value.contains(item.getDisplay())) {
				map = StringUtils.defaultString(item.getValue());
				break;
			}
		}
		return map;
	}
	

	/**
	 * Return contained context based on passed value
	 * @param data
	 * @param value
	 * @param defaultMsg
	 * @return
	 */
	public static String getCampusValue(List<ExceptionSimpleData> data, String value) {
		String map = "";
		outter:for (ExceptionSimpleData item : data) {
			
			if(item.getDisplay().contains("|")) { // multiple acronyms with "|"
				String[] names = StringUtils.split(item.getDisplay(), "|");
//				String[] names = item.getDisplay().split("|");
				
				for(String acroName : names) {
					if(acroName.contains(value)) {
						map = StringUtils.defaultString(item.getValue());
						break outter;
					}
				}
				
			}else if (value.contains(item.getDisplay())) {
				map = StringUtils.defaultString(item.getValue());
				break outter;
			}
		}
		return map;
	}
	
	
	/**
	 * Return mapped site id based on passsed value
	 * @param data
	 * @param value
	 * @return
	 */
	public static String getMappedDisplay(List<ExceptionSimpleData> data, String passed) {
		String value = "";
		for (ExceptionSimpleData item : data) {
			if (StringUtils.defaultString(item.getDisplay()).equals(passed)) {
				value = StringUtils.defaultString(item.getValue());
				break;
			}
		}
		return value;
	}
	
	/**
	 * Return mapped context based on passsed value
	 * @param data
	 * @param passed
	 * @return
	 */
	public static String getMappedValue(List<ExceptionSimpleData> data, String passed) {
		String display = "";
		for (ExceptionSimpleData item : data) {
			if (StringUtils.defaultString(item.getValue()).equals(passed)) {
				display = StringUtils.defaultString(item.getDisplay());
				break;
			}
		}
		return display;
	}
	
	
	public static String displayFromDate(String date) {
		String pattern = "yyyy-MM-dd 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String converted = sdf.format(null);
		return converted;
	}
	
	public static String ddMMyyyy2yyyyMMdd(String oldDate) {
		final String OLD_FORMAT = "dd/MM/yyyy";
		final String NEW_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(OLD_FORMAT);
		Date date = null;
		try {
			date = dateFormat.parse(oldDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateFormat.applyPattern(NEW_FORMAT);
		return date!=null ? dateFormat.format(date) : "";
	}
	
	
	/**
	 * Return camelcase string by putting space in front of upper case for better human readable format
	 * @param contents
	 * @return
	 */
	public static String splitCamelCase(String contents) {
		return contents.replaceAll(
			String.format("%s|%s|%s",
					"(?<=[A-Z])(?=[A-Z][a-z])",
					"(?<=[^A-Z])(?=[A-Z])",
					"(?<=[A-Za-z])(?=[^A-Za-z])"),	
					" "
		);
	}

	public static ArchiveQueryPlan buildArchiveQueryPlan(String from, String to, int cutoffMonths) {
		LocalDateTime fromDateTime = parseArchiveDateTime(from);
		LocalDateTime toDateTime = parseArchiveDateTime(to);
		LocalDateTime cutoff = LocalDate.now().minusMonths(cutoffMonths).atStartOfDay();

		if (toDateTime.isBefore(cutoff)) {
			return ArchiveQueryPlan.archiveOnly(from, to);
		}
		if (!fromDateTime.isBefore(cutoff)) {
			return ArchiveQueryPlan.primaryOnly(from, to);
		}

		String archiveTo = formatArchiveDateTime(cutoff.minusSeconds(1));
		String primaryFrom = formatArchiveDateTime(cutoff);
		return ArchiveQueryPlan.both(from, archiveTo, primaryFrom, to);
	}

	public static boolean isArchiveOnly(String from, String to, int cutoffMonths) {
		LocalDateTime toDateTime = parseArchiveDateTime(to);
		LocalDateTime cutoff = LocalDate.now().minusMonths(cutoffMonths).atStartOfDay();
		return toDateTime.isBefore(cutoff);
	}

	public static boolean isPrimaryOnly(String from, String to, int cutoffMonths) {
		ArchiveQueryPlan plan = buildArchiveQueryPlan(from, to, cutoffMonths);
		return plan.isQueryPrimary() && !plan.isQueryArchive();
	}

	public static String describeArchiveRouting(String from, String to, int cutoffMonths) {
		ArchiveQueryPlan plan = buildArchiveQueryPlan(from, to, cutoffMonths);
		if (plan.isQueryPrimary() && !plan.isQueryArchive()) {
			return "within " + cutoffMonths + " months, primary datasource only";
		}
		if (plan.isQueryArchive() && !plan.isQueryPrimary()) {
			return "older than " + cutoffMonths + " months, archive datasource only";
		}
		return "spans " + cutoffMonths + " month cutoff, archive and primary datasources";
	}

	private static LocalDateTime parseArchiveDateTime(String value) {
		return LocalDateTime.parse(value, ARCHIVE_DATE_TIME_FORMATTER);
	}

	private static String formatArchiveDateTime(LocalDateTime value) {
		return value.format(ARCHIVE_DATE_TIME_FORMATTER);
	}
}
