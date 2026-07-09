<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.borderless tr, .borderless td, .borderless th {
    border: none !important;
   }
</style>

<script type="text/javascript">

	$(function() {
		initReportDatePickers();
	});

</script>
<div id="validation-error" class="modal fade">
	<div class="modal-dialog">
		<div class="alert alert-block alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<i class="fa fa-frown-o fa-lg"></i>&nbsp;&nbsp;<span id="validation-error-message">Please make sure <b>Hospital</b>, <b>FromDate</b> & <b>ToDate</b> are filled in.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FromDate can go back up to 2 years, and the date range cannot exceed 6 weeks.</span>
		</div>
	</div>
</div>

<div class="width: 85%; margin:0 auto;">
	<div class="row m-3 justify-content-center">
		<div id="searchCondition">
			<form method="get" action="${pageContext.request.contextPath}/dashboard" class="form-inline form-control-row">
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
			<div class="form-group pr-2">
				<input type="text" class="form-control" id="fromDate" name="fromDate" value="${fromDate}" 
					placeholder="From" readonly />
			</div>
			<div class="form-group pr-3">
				<input type="text" class="form-control" id="toDate" name="toDate" value="${toDate}" 
					placeholder="To" readonly />
			</div>
			<button type="submit" class="btn btn-primary" id="searchDate"  onclick="return validateForm();">Search</button>
			<button type="button" class="btn btn-info ml-2" id="clearDate" onclick="return clearSearchForm();">Clear</button>
			<input type="hidden" name="searchCheck" value="true" />
			</form>
		</div>
	</div>
	<div class="row">
		<c:choose>
			<c:when test="${dashboardData != null}">
				<div class="stats">
				<table class="table table-hover">
					<thead>
						<tr>
							<th colspan="3" style="text-align: center;"> 
								<c:choose>
									<c:when test="${dashboardHospital != null}">
										<div class="h6 font-italic">
											Successful uploads to My Health Record at <span class="text-primary font-weight-bold"><c:out value="${dashboardHospital}" /></span> from <span class="text-primary font-weight-bold"><c:out value="${dashboardFromDate}"/></span> to <span class="text-primary font-weight-bold"><c:out value="${dashboardToDate}"/></span> 
										</div>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</th>
						</tr>
						<tr>
							<th class="left header" width="25%" style="padding-left: 2.5rem;">Document Type</th>
							<th class="centre header" width="30%" style="text-align:center !important; padding-right: 10%;">Count</th>
							<th class="right header"></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="totalSuccess" scope="session" value="${(dashboardData.successDischargeCntUpload + dashboardData.successLisCntUpload + dashboardData.successRisCntUpload + dashboardData.successPsmlCntUpload + dashboardData.successShsCntUpload + dashboardData.successEsCntUpload)}" />
						<c:set var="dischargeSuccess" scope="session" value="${dashboardData.successDischargeCntUpload}" />
						<c:set var="lisSuccess" scope="session" value="${dashboardData.successLisCntUpload}" />
						<c:set var="risSuccess" scope="session" value="${dashboardData.successRisCntUpload}" />
						<c:set var="psmlSuccess" scope="session" value="${dashboardData.successPsmlCntUpload}" />
						<c:set var="shsSuccess" scope="session" value="${dashboardData.successShsCntUpload}" />
						<c:set var="esSuccess" scope="session" value="${dashboardData.successEsCntUpload}" />
						
						<c:if test="${dischargeSuccess > 0}" >
							<tr>
								<td style="padding-left: 2.5rem;">Discharge Summary</td>
								<td style="text-align:center !important; padding-right: 10%;">
									<span class="font-weight-bold"><c:out value="${dischargeSuccess}"/><%--</span> of <c:out value="${totalSuccess}" />--%>
								</td>
								<td class="right">
									<c:choose>
										<c:when test="${totalSuccess!=0}">
											<div class="progress">
												<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60"
													aria-valuemin="0" aria-valuemax="100"
													style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(dischargeSuccess) / (totalSuccess) } " />;">
													<fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(dischargeSuccess) / (totalSuccess) } " />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${lisSuccess > 0}" >
							<tr>
								<td style="padding-left: 2.5rem;">Pathology Report</td>
								<td style="text-align:center !important; padding-right: 10%;">
									<span class="font-weight-bold"><c:out value="${lisSuccess}"/><%--</span> of <c:out value="${totalSuccess}" />--%>
								</td>
								<td class="right">
									<c:choose>
										<c:when test="${totalSuccess!=0}">
											<div class="progress">
												<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60"
													aria-valuemin="0" aria-valuemax="100"
													style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(lisSuccess) / (totalSuccess) } " />;">
													<fmt:formatNumber type="percent" maxIntegerDigits="3"
														value="${(lisSuccess) / (totalSuccess) } " />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${risSuccess > 0}" >
							<tr>
								<td style="padding-left: 2.5rem;">Radiology Report</td>
								<td style="text-align:center !important; padding-right: 10%;">
									<span class="font-weight-bold"><c:out value="${risSuccess}"/><%--</span> of <c:out value="${totalSuccess}" />--%>
								</td>
								<td class="right">
									<c:choose>
										<c:when test="${totalSuccess!=0}">
											<div class="progress">
												<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60"
													aria-valuemin="0" aria-valuemax="100"
													style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(risSuccess) / (totalSuccess) } " />;">
													<fmt:formatNumber type="percent" maxIntegerDigits="3"
														value="${(risSuccess) / (totalSuccess) } " />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${psmlSuccess > 0}" >
							<tr>
								<td style="padding-left: 2.5rem;">Pharmacist Shared Medicines List</td>
								<td style="text-align:center !important; padding-right: 10%;">
									<span class="font-weight-bold"><c:out value="${psmlSuccess}"/><%--</span> of <c:out value="${totalSuccess}" />--%>
								</td>
								<td class="right">
									<c:choose>
										<c:when test="${totalSuccess!=0}">
											<div class="progress">
												<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="60"
													aria-valuemin="0" aria-valuemax="100"
													style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(psmlSuccess) / (totalSuccess) } " />;">
													<fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(psmlSuccess) / (totalSuccess) } " />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${shsSuccess > 0}" >
							<tr>
								<td style="padding-left: 2.5rem;">Specialist Letter</td>
								<td style="text-align:center !important; padding-right: 10%;">
									<span class="font-weight-bold"><c:out value="${shsSuccess}"/><%--</span> of <c:out value="${totalSuccess}" />--%>
								</td>
								<td class="right">
									<c:choose>
										<c:when test="${totalSuccess!=0}">
											<div class="progress">
												<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60"
													aria-valuemin="0" aria-valuemax="100"
													style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(shsSuccess) / (totalSuccess) } " />;">
													<fmt:formatNumber type="percent" maxIntegerDigits="3"
														value="${(shsSuccess) / (totalSuccess) } " />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${esSuccess > 0}" >
							<tr>
								<td style="padding-left: 2.5rem;">Event Summary</td>
								<td style="text-align:center !important; padding-right: 10%;">
									<span class="font-weight-bold"><c:out value="${esSuccess}"/><%--</span> of <c:out value="${totalSuccess}" />--%>
								</td>
								<td class="right">
									<c:choose>
										<c:when test="${totalSuccess!=0}">
											<div class="progress">
												<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60"
													aria-valuemin="0" aria-valuemax="100"
													style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(esSuccess) / (totalSuccess) } " />;">
													<fmt:formatNumber type="percent" maxIntegerDigits="3"
														value="${(esSuccess) / (totalSuccess) } " />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						
						<tr>
							<td style="padding-left: 2.5rem;">Total</td>
							<td class="text-primary font-weight-bold" style="text-align:center !important; padding-right: 10%;">
								<c:out value="${totalSuccess}" />
							</td>
							<td class="right">
							</td>
						</tr>
					</tbody>
				</table>
				<table class="table table-hover borderless">
					<tbody>
							<tr>
								<td class="header text-center" width="50%">
									<canvas id="dashboardChart1" style="height: 250px;"></canvas>
									<script>
									var ctx = document.getElementById('dashboardChart1').getContext('2d');
									var data = {
											  labels: ["Discharge Summary", "Pathology Report", "Radiology Report", "Pharmacist Shared Medicines List", "Specialist Letter", "Event Summary"],
											  datasets: [
											  {
											    backgroundColor: ['#449d44', '#31b0d5', '#ec971f','#007bff', '#dc3545', '#5bc0de'],
											    borderWidth : 1,
											    data: [${dashboardData.successDischargeCntUpload}, ${dashboardData.successLisCntUpload}, ${dashboardData.successRisCntUpload}, ${dashboardData.successPsmlCntUpload}, ${dashboardData.successShsCntUpload}, ${dashboardData.successEsCntUpload}]
											  }]
											};
											
											var myBarChart = new Chart(ctx, {
											  type: 'doughnut',
											  data: data,
											  options: {
												responsive: true,
												maintainAspectRatio: false,
												title: {
													display: true,
													text: 'Successfully uploaded document types'
												},
												legend: {
													display: false
												},
											  }
											});
		
									</script>
								</td>
								<td class="header text-center" width="50%">
									<canvas id="dashboardChart2" style="height: 250px;"></canvas>
									<script>
		
									const labelValues =  ["Discharge Summary", "Pathology Report", "Radiology Report", "Pharmacist Shared Medicines List", "Specialist Letter", "Event Summary"];
									const dataValues = [${dashboardData.successDischargeCntUpload}, ${dashboardData.successLisCntUpload}, ${dashboardData.successRisCntUpload},${dashboardData.successPsmlCntUpload}, ${dashboardData.successShsCntUpload}, ${dashboardData.successEsCntUpload}];
									while(dataValues[0]==0){
										dataValues.shift();
										labelValues.shift();
									}
									while(dataValues[dataValues.length-1]==0){
										dataValues.pop();
										labelValues.pop();
									}
									  
									var ctx = document.getElementById('dashboardChart2').getContext('2d');
									var data = {
											  labels: labelValues,
											  datasets: [
											  {
											    backgroundColor: ['#449d44', '#31b0d5', '#ec971f','#007bff', '#dc3545', '#5bc0de'],
											    borderWidth : 1,
											    data: dataValues
											  }]
											};
											
											var myBarChart = new Chart(ctx, {
											  type: 'bar',
											  data: data,
											  options: {
												responsive: true,
												maintainAspectRatio: false,
												title: {
													display: true,
													text: 'Successfully uploaded document counts'
												},
												legend: {
													display: false
												},
												scales: {
													yAxes: [{
														ticks: {
															beginAtZero: true,
															//stepSize: 1
															}
														}]
													}
											  	}
											});
		
									</script>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<br><br><br>
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
</div>
