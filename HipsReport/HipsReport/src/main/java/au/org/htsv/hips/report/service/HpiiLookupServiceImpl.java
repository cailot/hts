package au.org.htsv.hips.report.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.xml.ws.soap.AddressingFeature;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.tempuri.HpiiService_Service;
import org.tempuri.IHpiiServiceV800;
import org.tempuri.IHpiiServiceV800HpiiBatchRetrieveHiServiceFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchRetrieveInvalidRequestFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchRetrieveInvalidUserFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchRetrieveItemNotFoundFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchRetrieveServiceOperationFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchSubmitHiServiceFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchSubmitInvalidRequestFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchSubmitInvalidUserFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchSubmitItemNotFoundFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiBatchSubmitServiceOperationFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiIdentifierSearchHiServiceFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiIdentifierSearchInvalidRequestFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiIdentifierSearchInvalidUserFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiIdentifierSearchItemNotFoundFaultFaultFaultMessage;
import org.tempuri.IHpiiServiceV800HpiiIdentifierSearchServiceOperationFaultFaultFaultMessage;

import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;

import au.org.htsv.hips.report.dao.HpiiLookupDao;
import au.org.htsv.hips.report.entity.HpiiDTO;
import au.org.htsv.hips.report.util.ExceptionReportConstants;
import hips.nehta._2014._03.HospitalIdentifier;
import hips.nehta._2014._03.ResponsibleUser;
import hips.nehta._2014._03.hpii.ArrayOfIdentifierQuery;
import hips.nehta._2014._03.hpii.BatchSearchResult;
import hips.nehta._2014._03.hpii.IdentifierQuery;
import hips.nehta._2020._05.hpii.HpiiBatchRetrieveRequestV8;
import hips.nehta._2020._05.hpii.HpiiBatchRetrieveResponseV8;
import hips.nehta._2020._05.hpii.HpiiBatchSubmitRequestV8;
import hips.nehta._2020._05.hpii.HpiiBatchSubmitResponseV8;
import hips.nehta._2020._05.hpii.HpiiIdentifierSearchRequestV8;
import hips.nehta._2020._05.hpii.HpiiIdentifierSearchResponseV8;

@Service
public class HpiiLookupServiceImpl implements HpiiLookupService {

		
	// Initialising necessary HIPS object factories
	public static final hips.nehta._2014._03.hpii.ObjectFactory hipsHpiiObjectFactory2014 = new hips.nehta._2014._03.hpii.ObjectFactory();
	public static final hips.nehta._2014._03.ObjectFactory hipsObjectFactory2014 = new hips.nehta._2014._03.ObjectFactory();
	public static final hips.nehta._2020._05.hpii.ObjectFactory hipsHpiiObjectFactory2020 = new hips.nehta._2020._05.hpii.ObjectFactory();
	public static final com.microsoft.schemas._2003._10.serialization.arrays.ObjectFactory msSerializationArraysOf2003 = new com.microsoft.schemas._2003._10.serialization.arrays.ObjectFactory();
	
	
	@Value("${hips.hpii.url}")
	public String url;
	
	// *.prod.services SSL certificate will need to be imported into your JRE trust store for this endpoint URL to work
	HpiiService_Service hpiiService;
	IHpiiServiceV800 hpii;
	
	 @PostConstruct
	 public void initializeService() {
		URL serviceUrl = null;;
		try {
			serviceUrl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hpiiService = new HpiiService_Service(serviceUrl);
		AddressingFeature addressingFeature = new AddressingFeature();
		hpii = hpiiService.getWSHttpBindingIHpiiServiceV800(addressingFeature);
	 }
	 
	@Autowired
	private HpiiLookupDao hpiiLookupDao;

	@Override
	public HpiiDTO getHpiiFromDB(HpiiDTO dto) {
		HpiiDTO retreived = null;
		try{
			retreived = hpiiLookupDao.getHpii(dto.getLastName(), dto.getFirstName(), dto.getAhpra());
		}catch(Exception e) {
			retreived = new HpiiDTO(); // to avoid NPE
		}
		return retreived;
	}

	@Override
	@Transactional
	public int addHpiiToDB(HpiiDTO dto) {
		int result = 0;
		try {
			result = hpiiLookupDao.addHpii(dto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	@Transactional
	public int updateHpiiToDB(HpiiDTO dto) {
		int result = 0;
		try {
			result = hpiiLookupDao.updateHpii(dto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	@Override
	public HpiiDTO getHPII(HpiiDTO dto) {
		// 1. populate parameters
		String hospital = dto.getHospitalCode();//ExceptionReportConstants.FACILITY_SAMPLE;
		String familyName = dto.getLastName();
		String firstName = dto.getFirstName();
		String ahpra = dto.getAhpra();
		// 2. build Request - check if single name or not
		HpiiIdentifierSearchRequestV8 request = StringUtils.isBlank(firstName) ? buildSearchRequest(hospital, familyName, ahpra) : buildSearchRequest(hospital, familyName, firstName, ahpra);
		try {
			// 3. get Response
			HpiiIdentifierSearchResponseV8 response = hpii.hpiiIdentifierSearch(request);
			String hpiiResult = response.getData().getValue().getHpiiNumber().getValue();
			String hpiiStatus = response.getData().getValue().getHpiiStatus().value();
			// 4. update dto
			dto.setHpii(hpiiResult);
			dto.setStatus(hpiiStatus);
		} catch (IHpiiServiceV800HpiiIdentifierSearchHiServiceFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IHpiiServiceV800HpiiIdentifierSearchInvalidRequestFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IHpiiServiceV800HpiiIdentifierSearchInvalidUserFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IHpiiServiceV800HpiiIdentifierSearchItemNotFoundFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IHpiiServiceV800HpiiIdentifierSearchServiceOperationFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 5. return dto
		return dto;
	}

	@Override
	public List<HpiiDTO> getHPII(List<HpiiDTO> providers) {
		if(providers == null || providers.size()==0) return new ArrayList<>();		
		try {
//			String hospitalCode = providers.get(0).getHospitalCode();
			// 1. create HospitalIdentifier parameter object
			HospitalIdentifier hospitalIdentifier = new HospitalIdentifier();
			hospitalIdentifier.setHospitalCode(ExceptionReportConstants.FACILITY_SAMPLE);
			
			// 2. create batch Id request
			HpiiBatchSubmitRequestV8 batchSubmitRequest = hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8();
			batchSubmitRequest.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
			batchSubmitRequest.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
			hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8IdentifierQueries(null);
			
			
			// 3. create each row as IdentifierQuery
			ArrayOfIdentifierQuery identifierQueries = hipsHpiiObjectFactory2014.createArrayOfIdentifierQuery();
			for(HpiiDTO provider : providers) {
				String familyName = provider.getLastName();
				String firstName = provider.getFirstName();
				String ahpra = provider.getAhpra();
				IdentifierQuery query = StringUtils.isBlank(firstName) ? getQuery(familyName, ahpra) : getQuery(familyName, firstName, ahpra);
				identifierQueries.getIdentifierQuery().add(query);
			}
			
			// 4. set IdentifierQueries to batch Id request 
			batchSubmitRequest.setIdentifierQueries(hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8IdentifierQueries(identifierQueries));
					
			// 5. get batch Id
			HpiiBatchSubmitResponseV8 batchSubmitResponse = hpii.hpiiBatchSubmit(batchSubmitRequest);
			// Extract the response status and batch identifier
			String batchId = batchSubmitResponse.getData().getValue().getBatchIdentifier().getValue();
			
			// 6. batch request for hpiis
			HpiiBatchRetrieveRequestV8 batchRetrieveRequest = hipsHpiiObjectFactory2020.createHpiiBatchRetrieveRequestV8();
			batchRetrieveRequest.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
			batchRetrieveRequest.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
			batchRetrieveRequest.setBatchIdentifier(hipsHpiiObjectFactory2020.createHpiiBatchRetrieveRequestV8BatchIdentifier(batchId));
			
			// 7. handle response
			HpiiBatchRetrieveResponseV8 batchRetrieveResponse = hpii.hpiiBatchRetrieve(batchRetrieveRequest);
			List<BatchSearchResult> responses = batchRetrieveResponse.getData().getValue().getResults().getBatchSearchResult();
			for(BatchSearchResult response : responses) {
				String hpii = response.getHpiiNumber().getValue();
				System.out.println("Batch Response : " + hpii);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return providers;
	}


	@Override
	public String getHPII(String hospital, String familyName, String registerId) {// throws Exception {	
		// EXAMPLE 1: Individual provider search
		HpiiIdentifierSearchRequestV8 request = buildSearchRequest(hospital, familyName, registerId);
		
		HpiiIdentifierSearchResponseV8 response = null;
		try {
			response = hpii.hpiiIdentifierSearch(request);
		} catch (IHpiiServiceV800HpiiIdentifierSearchHiServiceFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchInvalidRequestFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchInvalidUserFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchItemNotFoundFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchServiceOperationFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extract the response status and HPII number
		String hpiiResult = (response!=null) ?  response.getData().getValue().getHpiiNumber().getValue() : "";
		return hpiiResult;
	}
	 	
	@Override
	public String getHPII(String hospital, String familyName, String firstName, String registerId) {// throws Exception {	
		// EXAMPLE 1: Individual provider search
		HpiiIdentifierSearchRequestV8 request = buildSearchRequest(hospital, familyName, firstName, registerId);
		
		HpiiIdentifierSearchResponseV8 response = null;
		try {
			response = hpii.hpiiIdentifierSearch(request);
		} catch (IHpiiServiceV800HpiiIdentifierSearchHiServiceFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchInvalidRequestFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchInvalidUserFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchItemNotFoundFaultFaultFaultMessage
				| IHpiiServiceV800HpiiIdentifierSearchServiceOperationFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extract the response status and HPII number
		String hpiiResult = (response != null) ? response.getData().getValue().getHpiiNumber().getValue() : "";
		return hpiiResult;
	}
	
	
	// set up Request for individual HPI-I webservice call to HIPS
	private HpiiIdentifierSearchRequestV8 buildSearchRequest(String hospitalCode, String familyName, String firstName, String registrationId) {
		HpiiIdentifierSearchRequestV8 request = hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8();
		request.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
		HospitalIdentifier hospitalIdentifier = new HospitalIdentifier();
		hospitalIdentifier.setHospitalCode(hospitalCode);
		hospitalIdentifier.setHospitalCodeSystem(ExceptionReportConstants.PAS_FACILITY_CODE);
		request.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
		IdentifierQuery identifierQuery = new IdentifierQuery();
		identifierQuery.setFamilyName(familyName);
		
		// doesn't need to pass first name as setOnlyNameIndicator sets to 'false'
		//ArrayOfstring givenNames = msSerializationArraysOf2003.createArrayOfstring();
		//givenNames.getString().add(firstName);
		//identifierQuery.setGivenNames(hipsHpiiObjectFactory2014.createQueryGivenNames(givenNames));
		identifierQuery.setOnlyNameIndicator(false);
		
		identifierQuery.setRegistrationId(hipsHpiiObjectFactory2014.createIdentifierQueryRegistrationId(registrationId));
		request.setQuery(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Query(identifierQuery));
		return request;
	}
	
	
	// set up Request for individual HPI-I webservice call to HIPS without given name 
	private HpiiIdentifierSearchRequestV8 buildSearchRequest(String hospitalCode, String familyName, String registrationId) {
		HpiiIdentifierSearchRequestV8 request = hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8();
		request.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
		HospitalIdentifier hospitalIdentifier = new HospitalIdentifier();
		hospitalIdentifier.setHospitalCode(hospitalCode);
		hospitalIdentifier.setHospitalCodeSystem(ExceptionReportConstants.PAS_FACILITY_CODE);
		request.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
		IdentifierQuery identifierQuery = new IdentifierQuery();
		identifierQuery.setFamilyName(familyName);
		identifierQuery.setRegistrationId(hipsHpiiObjectFactory2014.createIdentifierQueryRegistrationId(registrationId));
		// set single name indicator as true !!
		identifierQuery.setOnlyNameIndicator(true);
		request.setQuery(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Query(identifierQuery));
		return request;
	}

	
	public String getBatchIdentifier() {//throws Exception {
		
		// EXAMPLE 2: Submit batch provider search request
		HpiiBatchSubmitRequestV8 batchSubmitRequest = buildBatchIdRequest(ExceptionReportConstants.FACILITY_SAMPLE, ExceptionReportConstants.PAS_FACILITY_CODE);
		HpiiBatchSubmitResponseV8 batchSubmitResponse = null;
		try {
			batchSubmitResponse = hpii.hpiiBatchSubmit(batchSubmitRequest);
		} catch (IHpiiServiceV800HpiiBatchSubmitHiServiceFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitInvalidRequestFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitInvalidUserFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitItemNotFoundFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitServiceOperationFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extract the response status and batch identifier
		String identifier = (batchSubmitResponse != null) ? batchSubmitResponse.getData().getValue().getBatchIdentifier().getValue() : "";
		return identifier;
	}
	
	public String getBatchIdentifier(List<HpiiDTO> data) {//throws Exception {
		
		// EXAMPLE 2: Submit batch provider search request
		HpiiBatchSubmitRequestV8 batchSubmitRequest = buildBatchIdRequest(data);
		HpiiBatchSubmitResponseV8 batchSubmitResponse = null;
		try {
			batchSubmitResponse = hpii.hpiiBatchSubmit(batchSubmitRequest);
		} catch (IHpiiServiceV800HpiiBatchSubmitHiServiceFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitInvalidRequestFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitInvalidUserFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitItemNotFoundFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchSubmitServiceOperationFaultFaultFaultMessage e) {
			e.printStackTrace();
		}
		// Extract the response status and batch identifier
		String identifier = (batchSubmitResponse != null) ? batchSubmitResponse.getData().getValue().getBatchIdentifier().getValue() : "";
		return identifier;
	}
	
	// set up Request for batch identifier prior to actual batch request to HIPS
	private HpiiBatchSubmitRequestV8 buildBatchIdRequest(String hospitalCode, String codeSystem) {
		HpiiBatchSubmitRequestV8 request = hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8();
		request.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
		HospitalIdentifier hospitalIdentifier = new HospitalIdentifier();
		hospitalIdentifier.setHospitalCode(hospitalCode);
		hospitalIdentifier.setHospitalCodeSystem(codeSystem);		
		request.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
		
		hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8IdentifierQueries(null);
		ArrayOfIdentifierQuery identifierQueries = hipsHpiiObjectFactory2014.createArrayOfIdentifierQuery();
		
		IdentifierQuery query1 = getQuery("Jones", "Trent", "111111GT");
		identifierQueries.getIdentifierQuery().add(query1);
		
		IdentifierQuery query2 = getQuery("Ho", "NMW0001011701");
		identifierQueries.getIdentifierQuery().add(query2);
		
		IdentifierQuery query3 = getQuery("BALLAD", "HHS0000000003");
		identifierQueries.getIdentifierQuery().add(query3);
		
		IdentifierQuery query4 = getQuery("Smith", "XXX0000000001");
		identifierQueries.getIdentifierQuery().add(query4);
		
		request.setIdentifierQueries(hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8IdentifierQueries(identifierQueries));
		
		return request;
	}
	
	private HpiiBatchSubmitRequestV8 buildBatchIdRequest(List<HpiiDTO> providers){
		HpiiBatchSubmitRequestV8 request = hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8();
		request.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
		HospitalIdentifier hospitalIdentifier = new HospitalIdentifier();
		hospitalIdentifier.setHospitalCode(ExceptionReportConstants.FACILITY_SAMPLE);
		hospitalIdentifier.setHospitalCodeSystem(ExceptionReportConstants.PAS_FACILITY_CODE);
		request.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
		hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8IdentifierQueries(null);
		ArrayOfIdentifierQuery identifierQueries = hipsHpiiObjectFactory2014.createArrayOfIdentifierQuery();
		for(HpiiDTO provider : providers) {
			String familyName = provider.getLastName();
			String firstName = provider.getFirstName();
			String ahpra = provider.getAhpra();
			IdentifierQuery query = StringUtils.isBlank(firstName) ? getQuery(familyName, ahpra) : getQuery(familyName, firstName, ahpra);
			identifierQueries.getIdentifierQuery().add(query);
		}
		request.setIdentifierQueries(hipsHpiiObjectFactory2020.createHpiiBatchSubmitRequestV8IdentifierQueries(identifierQueries));
		return request;	
	}
	

	public String processBatch(String batchId) {//throws Exception {		
		HpiiBatchRetrieveRequestV8 batchRetrieveRequest = buildBatchRequest(batchId);
		HpiiBatchRetrieveResponseV8 batchRetrieveResponse = null;
		try {
			batchRetrieveResponse = hpii.hpiiBatchRetrieve(batchRetrieveRequest);
		} catch (IHpiiServiceV800HpiiBatchRetrieveHiServiceFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchRetrieveInvalidRequestFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchRetrieveInvalidUserFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchRetrieveItemNotFoundFaultFaultFaultMessage
				| IHpiiServiceV800HpiiBatchRetrieveServiceOperationFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extract the response status
		String response = "";
		if(batchRetrieveResponse !=null) {
			response = batchRetrieveResponse.getStatus().toString();
			if(ExceptionReportConstants.OK.equalsIgnoreCase(StringUtils.defaultString(response))) {
				List<BatchSearchResult> results = batchRetrieveResponse.getData().getValue().getResults().getBatchSearchResult();
				for(BatchSearchResult result : results) {
					String familyName = result.getFamilyName();
					String firstName = result.getGivenNames().getValue().getString().toString();
					String ahpra = result.getRegistrationId().getValue();
					String hpii = result.getHpiiNumber().getValue();
					String status = result.getHpiiStatus().name();
					System.out.println("Family Name :  " + familyName + " , First Name : " + firstName + " , Ahpra : " + ahpra + " , HPII : " + hpii + " , Status : " + status);
				}
			}
		}
		return response;
	}
	
	// execute batch with batchId
	private HpiiBatchRetrieveRequestV8 buildBatchRequest(String batchId) {
		if(batchId == null) {
			return null;
		}
		HpiiBatchRetrieveRequestV8 request = hipsHpiiObjectFactory2020.createHpiiBatchRetrieveRequestV8();
		request.setUser(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8User(new ResponsibleUser()));
		HospitalIdentifier hospitalIdentifier = new HospitalIdentifier();
		hospitalIdentifier.setHospitalCode(ExceptionReportConstants.FACILITY_SAMPLE);
		hospitalIdentifier.setHospitalCodeSystem(ExceptionReportConstants.PAS_FACILITY_CODE);
		request.setFacility(hipsHpiiObjectFactory2020.createHpiiIdentifierSearchRequestV8Facility(hospitalIdentifier));
		request.setBatchIdentifier(hipsHpiiObjectFactory2020.createHpiiBatchRetrieveRequestV8BatchIdentifier(batchId));
		return request;
	}
	
	// Identifier with given name
	private IdentifierQuery getQuery(String familyName, String givenName, String registrationId) {
		IdentifierQuery identifierQuery = new IdentifierQuery();
		identifierQuery.setFamilyName(familyName);
		ArrayOfstring givenNames = msSerializationArraysOf2003.createArrayOfstring();
		givenNames.getString().add(givenName);
		identifierQuery.setGivenNames(hipsHpiiObjectFactory2014.createQueryGivenNames(givenNames));
		identifierQuery.setRegistrationId(hipsHpiiObjectFactory2014.createIdentifierQueryRegistrationId(registrationId));
		identifierQuery.setOnlyNameIndicator(false);
		return identifierQuery;
	}
	
	// IdentifierQuery without given name
	private IdentifierQuery getQuery(String familyName, String registrationId) {
		IdentifierQuery identifierQuery = new IdentifierQuery();
		identifierQuery.setFamilyName(familyName);
		identifierQuery.setRegistrationId(hipsHpiiObjectFactory2014.createIdentifierQueryRegistrationId(registrationId));
		identifierQuery.setOnlyNameIndicator(true);
		return identifierQuery;
	}

}


