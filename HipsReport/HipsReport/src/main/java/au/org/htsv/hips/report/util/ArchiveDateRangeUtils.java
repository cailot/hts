package au.org.htsv.hips.report.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ArchiveDateRangeUtils {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private ArchiveDateRangeUtils() {
	}

	public static ArchiveQueryPlan buildPlan(String from, String to, int cutoffMonths) {
		LocalDateTime fromDateTime = parseDateTime(from);
		LocalDateTime toDateTime = parseDateTime(to);
		LocalDateTime cutoff = LocalDate.now().minusMonths(cutoffMonths).atStartOfDay();

		if (toDateTime.isBefore(cutoff)) {
			return ArchiveQueryPlan.archiveOnly(from, to);
		}
		if (!fromDateTime.isBefore(cutoff)) {
			return ArchiveQueryPlan.primaryOnly(from, to);
		}

		String archiveTo = formatDateTime(cutoff.minusSeconds(1));
		String primaryFrom = formatDateTime(cutoff);
		return ArchiveQueryPlan.both(from, archiveTo, primaryFrom, to);
	}

	public static boolean isArchiveOnly(String from, String to, int cutoffMonths) {
		LocalDateTime toDateTime = parseDateTime(to);
		LocalDateTime cutoff = LocalDate.now().minusMonths(cutoffMonths).atStartOfDay();
		return toDateTime.isBefore(cutoff);
	}

	private static LocalDateTime parseDateTime(String value) {
		return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
	}

	private static String formatDateTime(LocalDateTime value) {
		return value.format(DATE_TIME_FORMATTER);
	}
}
