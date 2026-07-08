package au.org.htsv.hips.report.util;

public class ArchiveQueryPlan {

	private final boolean queryArchive;
	private final boolean queryPrimary;
	private final String archiveFrom;
	private final String archiveTo;
	private final String primaryFrom;
	private final String primaryTo;

	private ArchiveQueryPlan(boolean queryArchive, boolean queryPrimary,
			String archiveFrom, String archiveTo, String primaryFrom, String primaryTo) {
		this.queryArchive = queryArchive;
		this.queryPrimary = queryPrimary;
		this.archiveFrom = archiveFrom;
		this.archiveTo = archiveTo;
		this.primaryFrom = primaryFrom;
		this.primaryTo = primaryTo;
	}

	public static ArchiveQueryPlan archiveOnly(String from, String to) {
		return new ArchiveQueryPlan(true, false, from, to, null, null);
	}

	public static ArchiveQueryPlan primaryOnly(String from, String to) {
		return new ArchiveQueryPlan(false, true, null, null, from, to);
	}

	public static ArchiveQueryPlan both(String archiveFrom, String archiveTo, String primaryFrom, String primaryTo) {
		return new ArchiveQueryPlan(true, true, archiveFrom, archiveTo, primaryFrom, primaryTo);
	}

	public boolean isQueryArchive() {
		return queryArchive;
	}

	public boolean isQueryPrimary() {
		return queryPrimary;
	}

	public String getArchiveFrom() {
		return archiveFrom;
	}

	public String getArchiveTo() {
		return archiveTo;
	}

	public String getPrimaryFrom() {
		return primaryFrom;
	}

	public String getPrimaryTo() {
		return primaryTo;
	}
}
