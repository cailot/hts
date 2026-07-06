
function validateForm() {
  var isValid = true;
  // start & end date should be filled in.
  if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()==''))
  {
	  isValid = false;
	  $("#validation-error").modal('toggle');
  }
  return isValid;
}

// for detail page
function validateFormWithDetail() {
	var isValid = true;
	  
	// plus document
	if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()=='')||($('#document').val()==null)||($('#document').val()==''))
	{
		  isValid = false;
		  $("#validation-error").modal('toggle');
	}
	return isValid;
	
}

//for audit page
function validateFormWithPatientInfo() {
	var isValid = true;
	  
	// plus patientSearch
	if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()=='')||($('#patientSearch').val()==null)||($('#patientSearch').val()==''))
	{
		  isValid = false;
		  $("#validation-error").modal('toggle');
	}
	return isValid;
	
}

// for hpii individual search
function validateIndividualForm() {
	  var isValid = true;
	  // lastName & ahpra should be filled in.
	  if(($('#lastName').val()=='')||($('#lastName').val()=='')||($('#ahpra').val()==null)||($('#ahpra').val()==''))
	  {
		  isValid = false;
		  $("#validation-error").modal('toggle');
	  }
	  return isValid;
	}
