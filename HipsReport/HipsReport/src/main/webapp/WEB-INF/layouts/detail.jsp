<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hips.report.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttons.flash.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jszip.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pdfmake.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/vfs_fonts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttons.print.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datetime-moment.js"></script>


<style>
/**
table.display {
 margin: 0 auto;
  width: 100%;
  clear: both;
  border-collapse: collapse;
  table-layout: fixed;        
  word-wrap:break-word; 
 }
 */


.dt-buttons{
   float: right !important;
}
   
   .dataTables_filter{
   float: left !important;
}

.center-cell { 
	display: table-cell;
	text-align:center !important; 
	vertical-align:middle !important; 
	font-size: larger;
}

h4 {
	text-align: center !important;
	margin: 0 auto;
}

.ellipsis {
   	position: relative;
}
.ellipsis:before {
    content: '&nbsp;';
    visibility: hidden;
}
.ellipsis span {
    position: absolute;
    left: 0;
    right: 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}


</style>
<script type="text/javascript">

$(document).ready( function () {

	$('[data-toggle="tooltip"]').tooltip({
		container: 'body'	
	});

	$.fn.dataTable.moment('DD/MM/YYYY HH:mm:ss');
	
    $('#detailTable').DataTable({
      "autoWidth": false,  
	  dom: 'Bfrtip',
      buttons: [
            'copyHtml5', 'csvHtml5', 'excelHtml5', 
            {
	            extend: 'pdfHtml5',
	            download: 'open',
	            pageSize: 'A0'
	        }
      ],
      columnDefs : [
		{ width: '10%', targets: 0 },
		{ width: '9.5%', targets: 1 },
		{ width: '34%', targets: 2 },
		{ width: '8%', targets: 3 },
		{ width: '8%', targets: 4 },
		{ width: '10%', targets: 5 },
		{ width: '7.5%', targets: 6 },
		{ width: '7%', targets: 7 },
		{ width: '3%', targets: 8 },
		{ width: '3%', targets: 9 },
         {
            'targets': [8,9],
            'orderable': false,
         }
	  ],
         'order' : [[0, 'desc']]
	});

  
	
} );

var today = new Date();
	$(function() {
		initReportDatePickers();
	});

	function showMessage(auditId){
		$('#messageName').html("Original HL7 Message" + "&nbsp;&nbsp;&nbsp;" + "<i class='fa fa-commenting-o fa-lg'></i>");

		if(auditId.startsWith('urn:uuid:'))
		{
			$('#messageDetail').html('No message available...');
		}else{
			$.ajax({
			     type: "GET",
			     url: 'message',
			     data: "id=" + auditId,
			     success: function(data) {
			          $('#messageDetail').html(data);
			     }
			});
		}
	}

	
</script> 

<div style="width: 85%; margin:0 auto;">
	<div class="row m-3 justify-content-center">
		<div id="searchCondition">
			<form method="get" action="${pageContext.request.contextPath}/detail" class="form-inline form-control-row">
		    <div class="dropdown pr-3" style="display: inline-block;">
		    <div class="btn-group btn-group-inline">
		        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown">
		            Select Hospital
		        </button>
		        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		        	<c:forEach items="${hospitals}" var="hospital">
						<a class="dropdown-item" href="#">${hospital.display}</a>
					</c:forEach>
		        </div>
		    </div>
		        <input type="hidden" name="siteName" id="siteName" value="" />
		    </div>
			<script>
				$(".dropdown-menu a ").click(function () {
					var selected = $(this).text();
					$(this).parents(".btn-group-inline").find('.btn').text(selected);
					document.querySelector("#siteName").value = selected;
				});
			</script>
			
			<!-- add document types -->	
			<div class="dropdown pr-3" style="display: inline-block;">
				<div class="btn-group btn-group-inline">
					<button class="btn btn-primary dropdown-toggle" type="button" id="documentType" name="documentType" data-toggle="dropdown"> <i class="fa fa-file-text-o" style="font-size: 1.5em;"></i></button>
					<div class="dropdown-menu" aria-labelledby="documentType" id="doco">
						<a class="dropdown-item" href="#" data-value="ds"><input id="ds" type="checkbox"/>&nbsp;Discharge Summary</a>
						<a class="dropdown-item" href="#" data-value="lis"><input id="lis" type="checkbox"/>&nbsp;Pathology Report</a>
			        	<a class="dropdown-item" href="#" data-value="ris"><input id="ris" type="checkbox"/>&nbsp;Diagnostic Imaging Report</a>
						<a class="dropdown-item" href="#" data-value="psml"><input id="psml" type="checkbox"/>&nbsp;Pharmacist Shared Medicines List</a>
			        	<a class="dropdown-item" href="#" data-value="shs"><input id="shs" type="checkbox"/>&nbsp;Specialist Letter</a>
						<a class="dropdown-item" href="#" data-value="es"><input id="es" type="checkbox"/>&nbsp;Event Summary</a>
			        </div>
				</div>
				<input type="hidden" name="document" id="document" value="" />
				<script>
					function passDocuments(){
						document.querySelectorAll('#doco a').forEach(function(el){
							var selected = el.dataset.value;
							if(document.querySelector('#'+ selected).checked==true){
								document.querySelector('#document').value += selected + ",";
							}
						});
					}
				 </script>
			</div>
			<!-- end of document types -->	
			
			<div class="form-group pr-2">
				<input type="text" class="form-control" id="fromDate" name="fromDate" value="${fromDate}" placeholder="From" readonly />
			</div>
			<div class="form-group pr-3">
				<input type="text" class="form-control" id="toDate" name="toDate" value="${toDate}" placeholder="To" readonly />
			</div>
			<button type="submit" class="btn btn-primary" id="searchDate"  onclick="passDocuments();return validateFormWithDetail();">Search</button>
			<button type="button" class="btn btn-info ml-2" id="clearDate" onclick="return clearSearchForm();">Clear</button>
			<input type="hidden" name="searchCheck" value="true" />
			</form>
		</div>
	</div>
	
	<!-- Detail Table -->
	<c:choose>
		<c:when test="${detailData != null}">
			<table id="detailTable" class="display">
				<thead>
					<tr>
						<th colspan="10" style="text-align: center;"> 
							<c:choose>
								<c:when test="${detailHospital != null}">
									<div class="h6 font-italic">
										 Detail exception context with patient information at <span class="text-primary font-weight-bold"><c:out value="${detailHospital}" /></span> from <span class="text-primary font-weight-bold"><c:out value="${detailFromDate}"/></span> to <span class="text-primary font-weight-bold"><c:out value="${detailToDate}"/></span>
									</div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</th>
					</tr>
					<tr>
						<th>Date</th>
						<th>Document</th>
						<th>Exception Details</th>
						<th>Admission</th>
						<th>Episode</th>
						<th>Patient Name</th>
						<th>MRN</th>
						<th>DOB</th>
						<th>Gender</th>
						<th>Msg</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detailData}" var="details">
						<tr>
							<td class="small ellipsis"><span><c:out value="${details.dateCreated}" /></span></td>
							<td class="small ellipsis"><span style="padding-left: 0.5rem;"><c:out value="${details.documentType}" /></span></td>
							<c:set var="description" scope="session" value="${details.exception}" />
							<td class="small ellipsis">
								<a href="#" class="text-dark" style="cursor:default;" data-toggle="tooltip" data-placement="auto" data-html="true"
									title="<div class='text-left'><c:out value='${description}' escapeXml='true'/></div>"
								> 
								<span><c:out value="${description}"/></span>
								</a>
							</td>
							<td class="small ellipsis"><span><c:out value="${details.admission}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${details.episode}" /></span></td>
							<td class="small ellipsis"><span style="padding-left: 0.5rem;"><c:out value="${details.firstName} ${details.lastName}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${details.urNumber}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${details.dob}" /></span></td>
							<td class="center-cell">
							<c:set var="gender" value="${details.gender}" />
							<c:choose>
								<c:when test="${gender=='1'}">
									<i class="fa fa-male text-primary"></i>
								</c:when>
								<c:when test="${gender=='2'}">
									<i class="fa fa-female text-danger"></i>
								</c:when>
								<c:otherwise>
									<i class="fa fa-question"></i>
								</c:otherwise>
							</c:choose>
							</td>
							<td class="center-cell"><i class="fa fa-envelope-o text-info" onclick="showMessage('${details.id}')" style="cursor:hand;" data-target="#layerpopMessage" data-toggle="modal"></i></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<div class="container">
				<div class="alert alert-warning" role="alert">
					<h5>
						<i class="fa fa-info-circle fa-lg"></i>&nbsp;&nbsp;Please select <strong>Hospital</strong> and <strong>Date Range</strong>.
					</h5>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

	
<div id="validation-error" class="modal fade">
	<div class="modal-dialog">
		<div class="alert alert-block alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<i class="fa fa-frown-o fa-lg"></i>&nbsp;&nbsp;<span id="validation-error-message">Please make sure <b>Hospital</b>, <b>Document</b>, <b>FromDate</b> & <b>ToDate</b> are filled in.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FromDate can go back up to 2 years, and the date range cannot exceed 6 weeks.</span>
		</div>
	</div>
</div>
<div class="modal fade" id="layerpopMessage" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog modal-xl" role="document">
    <div class="modal-content">
      <!-- header -->
      <div class="modal-header">
        <!-- header title -->
        <h4 class="modal-title text-info" id="messageName">Header</h4>
        <!-- x button -->
        <button type="button" class="close" data-dismiss="modal">?</button>
        
      </div>
      <!-- body -->
      <div class="modal-body" id="messageDetail" style="word-wrap: break-word;">
      	Loading...
      </div>
      <!-- Footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


