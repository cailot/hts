package au.org.htsv.hips.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A simple POJO used to summary report statistics.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionSummaryData extends ExceptionBasicData {

	// discharge sent by health service
	private int dischargeCntAie2Hts;

	// lis sent by health service
	private int lisCntAie2Hts;

	// ris sent by health service
	private int risCntAie2Hts;
	
	// psml sent by health service
	private int psmlCntAie2Hts;

	// shs sent by health service
	private int shsCntAie2Hts;

	// es sent by health service
	private int esCntAie2Hts;

	// discharge error count at HTS
	private int dischargeErrorCntAtHts;

	// lis error count sent at HTS
	private int lisErrorCntAtHts;

	// ris error count sent at HTS
	private int risErrorCntAtHts;

	// psml error count sent at HTS
	private int psmlErrorCntAtHts;

	// shs error count sent at HTS
	private int shsErrorCntAtHts;

	// es error count sent at HTS
	private int esErrorCntAtHts;

	// discharge sent from HTS to MHR
	private int dischargeCntHts2Mhr;

	// lis sent from HTS to MHR
	private int lisCntHts2Mhr;

	// ris sent from HTS to MHR
	private int risCntHts2Mhr;

	// psml sent from HTS to MHR
	private int psmlCntHts2Mhr;

	// shs sent from HTS to MHR
	private int shsCntHts2Mhr;

	// es sent from HTS to MHR
	private int esCntHts2Mhr;

	// discharge error count at HIPS
	private int dischargeErrorCntAtHips;

	// lis error count at HIPS
	private int lisErrorCntAtHips;

	// ris error count at HIPS
	private int risErrorCntAtHips;
	
	// psml error count at HIPS
	private int psmlErrorCntAtHips;

	// shs error count at HIPS
	private int shsErrorCntAtHips;

	// es error count at HIPS
	private int esErrorCntAtHips;

	// success discharge upload count to MHR
	private int successDischargeCntUpload;

	// success lis upload count to MHR
	private int successLisCntUpload;

	// sucess ris upload count to MHR
	private int successRisCntUpload;
	
	// success pharmasist Shared Medicine List upload count to MHR
	private int successPsmlCntUpload;

	// success shared health summary upload count to MHR
	private int successShsCntUpload;

	// sucess event summary upload count to MHR
	private int successEsCntUpload;
	
	
	public ExceptionSummaryData(String fromDate, String toDate, String campus, String hospital, String facility) {
		super(fromDate, toDate, campus, hospital, facility);
	}
}
