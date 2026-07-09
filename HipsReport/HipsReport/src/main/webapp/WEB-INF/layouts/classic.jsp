<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon-32x32.png"/>
<link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

<link href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" rel="stylesheet" />
<!--[if IE 8]><link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-ie8buttonfix.css"><![endif]-->
<link href="${pageContext.request.contextPath}/css/hips.report.css" rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Chart.min.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Chart.min.js"></script>

<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
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
  if (!$("#fromDate").length || !$("#toDate").length) {
    return;
  }
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
  if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()==''))
  {
	  isValid = false;
	  showValidationError('Please make sure <b>Hospital</b>, <b>FromDate</b> & <b>ToDate</b> are filled in');
  } else if (!validateDateRange()) {
	  isValid = false;
  }
  return isValid;
}

function validateFormWithDetail() {
	var isValid = true;
	if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()=='')||($('#document').val()==null)||($('#document').val()==''))
	{
		  isValid = false;
		  showValidationError('Please make sure <b>Hospital</b>, <b>Document</b>, <b>FromDate</b> & <b>ToDate</b> are filled in');
	} else if (!validateDateRange()) {
		  isValid = false;
	}
	return isValid;
}

function validateFormWithPatientInfo() {
	var isValid = true;
	if(($('#fromDate').val()=='')||($('#toDate').val()=='')||($('#siteName').val()==null)||($('#siteName').val()=='')||($('#patientSearch').val()==null)||($('#patientSearch').val()==''))
	{
		  isValid = false;
		  showValidationError('Please make sure <b>Hospital</b>, <b>Patient Information</b>, <b>FromDate</b> & <b>ToDate</b> are filled in');
	} else if (!validateDateRange()) {
		  isValid = false;
	}
	return isValid;
}

function validateIndividualForm() {
	  var isValid = true;
	  if(($('#lastName').val()=='')||($('#lastName').val()=='')||($('#ahpra').val()==null)||($('#ahpra').val()==''))
	  {
		  isValid = false;
		  $("#validation-error").modal('toggle');
	  }
	  return isValid;
}
</script>


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
	
	<style>
	html,body{
		height:100%
	}
	.flex-fill{
		flex:1;
	}
	
	</style>
	
</head>
<body>
	<div class="container-fluid d-flex h-100 flex-column">
		<div class="row">
			<tiles:insertAttribute name="header" />
		</div>
		<div class="row dhhs-color" style="display: flex; justify-content: space-between;">
			<tiles:insertAttribute name="menu" />
		</div>
		<div class="row justify-content-center align-items-center">		
			<tiles:insertAttribute name="body" />
		</div>
		<footer class="mt-auto">
			<div class="row dhhs-color" style="padding: 15px 20px;">
				This web site is managed and authorised by the Department of Health & Human Services, State Government of Victoria, Australia&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &copy;&nbsp;Copyright State of Victoria
				2017 - <%=new java.util.Date().getYear() + 1900%>
			</div>
		</footer>
	</div>
</body>
</html>