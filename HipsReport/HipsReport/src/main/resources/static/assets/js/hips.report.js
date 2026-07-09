
var today = new Date();
today.setHours(0, 0, 0, 0);
var twoYearsAgo = new Date(today.getTime());
twoYearsAgo.setFullYear(twoYearsAgo.getFullYear() - 2);

function addWeeks(date, weeks) {
  var result = new Date(date.getTime());
  result.setDate(result.getDate() + (weeks * 7));
  return result;
}

function parseReportDate(value) {
  return $.datepicker.parseDate('dd/mm/yy', value);
}

function updateToDateLimits(fromDate) {
  var maxTo = addWeeks(fromDate, 6);
  if (maxTo > today) {
    maxTo = today;
  }
  $("#toDate").datepicker("option", "minDate", fromDate);
  $("#toDate").datepicker("option", "maxDate", maxTo);
}

function updateFromDateLimits(toDate) {
  var minFrom = addWeeks(toDate, -6);
  if (minFrom < twoYearsAgo) {
    minFrom = twoYearsAgo;
  }
  $("#fromDate").datepicker("option", "minDate", minFrom);
  $("#fromDate").datepicker("option", "maxDate", toDate < today ? toDate : today);
}

function initReportDatePickers() {
  $("#fromDate").datepicker({
    dateFormat : 'dd/mm/yy',
    minDate : twoYearsAgo,
    maxDate : today,
    numberOfMonths : 1,
    onClose : function(selectedDate) {
      if (!selectedDate) {
        return;
      }
      updateToDateLimits(parseReportDate(selectedDate));
    }
  });
  $("#toDate").datepicker({
    dateFormat : 'dd/mm/yy',
    minDate : twoYearsAgo,
    maxDate : today,
    numberOfMonths : 1,
    onClose : function(selectedDate) {
      if (!selectedDate) {
        return;
      }
      updateFromDateLimits(parseReportDate(selectedDate));
    }
  });
}

function validateDateRange() {
  var fromValue = $('#fromDate').val();
  var toValue = $('#toDate').val();
  if (fromValue == '' || toValue == '') {
    return false;
  }

  var fromDate = parseReportDate(fromValue);
  var toDate = parseReportDate(toValue);
  if (fromDate < twoYearsAgo) {
    showValidationError('<b>FromDate</b> cannot be earlier than 2 years from today');
    return false;
  }
  if (toDate > today) {
    showValidationError('<b>ToDate</b> cannot be later than today');
    return false;
  }
  if (toDate < fromDate) {
    showValidationError('<b>ToDate</b> must be on or after <b>FromDate</b>');
    return false;
  }
  var maxTo = addWeeks(fromDate, 6);
  if (toDate > maxTo) {
    alert('Please select a date range within 6 weeks.');
    return false;
  }
  return true;
}

function showValidationError(message) {
  if ($("#validation-error-message").length) {
    $("#validation-error-message").html(message);
  }
  $("#validation-error").modal('toggle');
}

function clearSearchForm() {
  $("#siteName").val('');
  $("#dropdownMenuButton").text('Select Hospital');
  $("#fromDate").val('');
  $("#toDate").val('');
  if ($("#fromDate").hasClass('hasDatepicker')) {
    $("#fromDate").datepicker("option", "minDate", twoYearsAgo);
    $("#fromDate").datepicker("option", "maxDate", today);
  }
  if ($("#toDate").hasClass('hasDatepicker')) {
    $("#toDate").datepicker("option", "minDate", twoYearsAgo);
    $("#toDate").datepicker("option", "maxDate", today);
  }
  if ($("#document").length) {
    $("#document").val('');
    $("#doco input[type='checkbox']").prop('checked', false);
  }
  if ($("#patientSearch").length) {
    $("#patientSearch").val('');
  }
  return false;
}

function validateForm() {
  var isValid = true;
  // start & end date should be filled in.
  if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()==''))
  {
	  isValid = false;
	  showValidationError('Please make sure <b>Hospital</b>, <b>FromDate</b> & <b>ToDate</b> are filled in');
  } else if (!validateDateRange()) {
	  isValid = false;
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
		  showValidationError('Please make sure <b>Hospital</b>, <b>Document</b>, <b>FromDate</b> & <b>ToDate</b> are filled in');
	} else if (!validateDateRange()) {
		  isValid = false;
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
		  showValidationError('Please make sure <b>Hospital</b>, <b>Patient Information</b>, <b>FromDate</b> & <b>ToDate</b> are filled in');
	} else if (!validateDateRange()) {
		  isValid = false;
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
