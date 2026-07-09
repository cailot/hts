<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hips.report.css"/>
<script type="text/javascript">
	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
		initReportDatePickers();
	});


    Chart.defaults.doughnutLabels = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutLabels = Chart.controllers.doughnut.extend({
    	updateElement: function(arc, index, reset) {
        var _this = this;
        var chart = _this.chart,
            chartArea = chart.chartArea,
            opts = chart.options,
            animationOpts = opts.animation,
            arcOpts = opts.elements.arc,
            centerX = (chartArea.left + chartArea.right) / 2,
            centerY = (chartArea.top + chartArea.bottom) / 2,
            startAngle = opts.rotation, // non reset case handled later
            endAngle = opts.rotation, // non reset case handled later
            dataset = _this.getDataset(),
            circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
            innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
            outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
            custom = arc.custom || {},
            valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

        helpers.extend(arc, {
          // Utility
          _datasetIndex: _this.index,
          _index: index,

          // Desired view properties
          _model: {
            x: centerX + chart.offsetX,
            y: centerY + chart.offsetY,
            startAngle: startAngle,
            endAngle: endAngle,
            circumference: circumference,
            outerRadius: outerRadius,
            innerRadius: innerRadius,
            label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
          },

          draw: function () {
          	var ctx = this._chart.ctx,
    						vm = this._view,
    						sA = vm.startAngle,
    						eA = vm.endAngle,
    						opts = this._chart.config.options;
    				
    					var labelPos = this.tooltipPosition();
    					var segmentLabel = vm.circumference / opts.circumference * 100;
    					
    					ctx.beginPath();
    					
    					ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
    					ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);
    					
    					ctx.closePath();
    					ctx.strokeStyle = vm.borderColor;
    					ctx.lineWidth = vm.borderWidth;
    					
    					ctx.fillStyle = vm.backgroundColor;
    					
    					ctx.fill();
    					ctx.lineJoin = 'bevel';
    					
    					if (vm.borderWidth) {
    						ctx.stroke();
    					}
    					
    					if (vm.circumference > 0.15) { // Trying to hide label when it doesn't fit in segment
    						ctx.beginPath();
    						ctx.font = helpers.fontString(opts.defaultFontSize, opts.defaultFontStyle, opts.defaultFontFamily);
    						ctx.fillStyle = "#fff";
    						ctx.textBaseline = "top";
    						ctx.textAlign = "center";
                
                // Round percentage in a way that it always adds up to 100%
    						ctx.fillText(segmentLabel.toFixed(0) + "%", labelPos.x, labelPos.y);
    					}
          }
        });

        var model = arc._model;
        model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
        model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
        model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
        model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

        // Set correct angles if not resetting
        if (!reset || !animationOpts.animateRotate) {
          if (index === 0) {
            model.startAngle = opts.rotation;
          } else {
            model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
          }

          model.endAngle = model.startAngle + model.circumference;
        }

        arc.pivot();
      }
    });
</script>
<div style="width: 85%; margin:0 auto;">
	<div class="row m-3 justify-content-center">
		<div id="searchCondition">
			<form method="get" action="${pageContext.request.contextPath}/summary" class="form-inline form-control-row">
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
			<c:choose>
			<c:when test="${summaryData != null}">
				<div class="stats">
					<table class="table table-hover">
						<thead>
							<tr>
								<th colspan="10" style="text-align: center;"> 
									<c:choose>
										<c:when test="${summaryHospital != null}">
											<div class="h6 font-italic">
												 Total & Failure counts per document types at <span class="text-primary font-weight-bold"><c:out value="${summaryHospital}" /></span> from <span class="text-primary font-weight-bold"><c:out value="${summaryFromDate}"/></span> to <span class="text-primary font-weight-bold"><c:out value="${summaryToDate}"/></span>
											</div>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</th>
							</tr>
							<tr>
								<th width="20%" class="text-center font-italic"><c:out value="${summaryHospital}" /></th>
								<th width="9%" class="text-center"><img src="${pageContext.request.contextPath}/images/blue-arrow.png" class="img-fluid" style="max-width: 30%;"/></th>
								<th width="10%" class="text-center font-italic">HTS</th>
								<th width="9%" class="text-center"><img src="${pageContext.request.contextPath}/images/blue-arrow.png" class="img-fluid" style="max-width: 30%;"/></th>
								<th width="10%" class="text-center font-italic">MHR</th>
								<th width="9%" class="text-center"></th>
								<th width="19%"></th>
								<th width="14%"></th>
							</tr>
						</thead>
						<tbody>
						
						
							<!-- Discharge Summary -->
							<c:if test="${summaryData.dischargeCntAie2Hts > 0}" >
								<tr>
									<td class="center-cell">
										Discharge Summary
									</td>
									<td class="center-cell font-weight-bold">
										${summaryData.dischargeCntAie2Hts}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold">
										${summaryData.dischargeCntHts2Mhr}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold text-primary" rowspan="2">${summaryData.successDischargeCntUpload}</td>
									<td rowspan="2">
										<canvas id="dischargeStatsChart" style="height: 130px;"></canvas>
										<script>
										var ctx = document.getElementById('dischargeStatsChart').getContext('2d');
										var data = {
												  labels: ["Discharge Summary"],
												  datasets: [
												  {
												    label: "Total count from ${summaryHospital} to HTS",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
												    data: [${summaryData.dischargeCntAie2Hts}]
												  },
												  {
													    label: "Error count from ${summaryHospital} to HTS",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.dischargeErrorCntAtHts}]
												  },
												  {
												    label: "Total count from HTS to MHR",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
										           	data: [${summaryData.dischargeCntHts2Mhr}]
												  }, 
												  {
													    label: "Error count from HTS to MHR",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.dischargeErrorCntAtHips}]
												  },
												  {
												    label: "Success count from HTS to MyHR",
												    backgroundColor: 'rgba(54, 162, 235, 0.2)',
										            borderColor : 'rgba(54, 162, 235, 1)',
										           	borderWidth : 1,
												    data: [${summaryData.successDischargeCntUpload}]
												  }
												  ]
												};
										
												var myBarChart = new Chart(ctx, {
												  type: 'bar',
												  data: data,
												  options: {
													responsive: true,
													maintainAspectRatio: false,
													legend: {
														display: false
													},
												    barValueSpacing: 20,
												    scales: {
												      yAxes: [{
												        ticks: {
												          min: 0,
												        }
												      }]
												    }
												  }
												});
			
										</script>
									</td>
									<td rowspan="2">
										<c:if test="${summaryData.dischargeCntAie2Hts != 0}">
											<canvas id="dischargePieChart" style="width:50%;height:50%;"></canvas>
							        		<script>
										        var config = {
										          type: 'doughnutLabels',
										          data: {
										            datasets: [{
										              data: [
										            	  ${summaryData.successDischargeCntUpload},
										            	  ${summaryData.dischargeErrorCntAtHts + summaryData.dischargeErrorCntAtHips}
										              ],
										              backgroundColor: [
										                "#36A2EB",
										                "#FF6384"
										              ],
										              label: 'Dataset 1'
										            }],
										            labels: [
										              "Success",
										              "Failure"
										            ]
										          },
										          options: {
										            responsive: true,
										            maintainAspectRatio: false,
										            legend: {
														display: false
										                },
										            animation: {
										              animateScale: true,
										              animateRotate: true
										            }
										          }
										        };
										        var ctx = document.getElementById("dischargePieChart").getContext("2d");
										        new Chart(ctx, config);
									    	</script>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="center-cell">
										<i class="fa fa-exclamation-circle text-danger fa-lg"></i>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>No matching MSH</li>
													  <li>PCEHR not exist</li>
													  <li>Bad CDA included</li>
													  <li>Invalid document</li>
													  <li>Invalid episode</li>
													  <li>Patient under age</li>
													  <li>Base64 encoding error</li>
													  <li>Invalid Patient</li>
													  <li>Invalid IHI</li>
													  <li>Datetime format</li>
													</ul>
												</div>"
										>
										${summaryData.dischargeErrorCntAtHts}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.dischargeCntAie2Hts - summaryData.dischargeErrorCntAtHts - summaryData.dischargeCntHts2Mhr) < 0 ? '0' : (summaryData.dischargeCntAie2Hts - summaryData.dischargeErrorCntAtHts - summaryData.dischargeCntHts2Mhr) }">
										
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>Validation error</li>
													  <li>Authorisation denied</li>
													  <li>SOAP header fault</li>
													  <li>SOAP body fault</li>
													  <li>Metadata validation error</li>
													  <li>Backend system unavailable</li>
													  <li>No metadata found</li>
													</ul>
												</div>"
										>
										${summaryData.dischargeErrorCntAtHips}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.dischargeCntHts2Mhr - summaryData.dischargeErrorCntAtHips - summaryData.successDischargeCntUpload) < 0 ? '0' : (summaryData.dischargeCntHts2Mhr - summaryData.dischargeErrorCntAtHips - summaryData.successDischargeCntUpload)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
								</tr>
							</c:if>
						
							<!-- Pathology Report -->
							<c:if test="${summaryData.lisCntAie2Hts > 0}" >
								<tr>
									<td class="center-cell">
										Pathology Report
									</td>
									<td class="center-cell font-weight-bold">
										${summaryData.lisCntAie2Hts}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold">
										${summaryData.lisCntHts2Mhr}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold text-primary" rowspan="2">${summaryData.successLisCntUpload}</td>
									<td rowspan="2">
										<canvas id="lisStatsChart" style="height: 130px;"></canvas>
										<script>
										var ctx = document.getElementById('lisStatsChart').getContext('2d');
										var data = {
												  labels: ["Pathology Report"],
												  datasets: [
												  {
												    label: "Total count from ${summaryHospital} to HTS",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
												    data: [${summaryData.lisCntAie2Hts}]
												  },
												  {
													    label: "Error count from ${summaryHospital} to HTS",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.lisErrorCntAtHts}]
												  },
												  {
												    label: "Total count from HTS to MHR",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
										           	data: [${summaryData.lisCntHts2Mhr}]
												  }, 
												  {
													    label: "Error count from HTS to MHR",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.lisErrorCntAtHips}]
												  },
												  {
												    label: "Success count from HTS to MyHR",
												    backgroundColor: 'rgba(54, 162, 235, 0.2)',
										            borderColor : 'rgba(54, 162, 235, 1)',
										           	borderWidth : 1,
												    data: [${summaryData.successLisCntUpload}]
												  }
												  ]
												};
										
												var myBarChart = new Chart(ctx, {
												  type: 'bar',
												  data: data,
												  options: {
													responsive: true,
													maintainAspectRatio: false,
													legend: {
														display: false
													},
												    barValueSpacing: 20,
												    scales: {
												      yAxes: [{
												        ticks: {
												          min: 0,
												        }
												      }]
												    }
												  }
												});
			
										</script>
									</td>
									<td rowspan="2">
										<c:if test="${summaryData.lisCntAie2Hts != 0}">
											<canvas id="lisPieChart" style="width:50%;height:50%;"></canvas>
							        		<script>
										        var config = {
										          type: 'doughnutLabels',
										          data: {
										            datasets: [{
										              data: [
										            	  ${summaryData.successLisCntUpload},
										            	  ${summaryData.lisErrorCntAtHts + summaryData.lisErrorCntAtHips}
										              ],
										              backgroundColor: [
										                "#36A2EB",
										                "#FF6384"
										              ],
										              label: 'Dataset 1'
										            }],
										            labels: [
										              "Success",
										              "Failure"
										            ]
										          },
										          options: {
										            responsive: true,
										            maintainAspectRatio: false,
										            legend: {
														display: false
										                },
										            animation: {
										              animateScale: true,
										              animateRotate: true
										            }
										          }
										        };
										        var ctx = document.getElementById("lisPieChart").getContext("2d");
										        new Chart(ctx, config);
									    	</script>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="center-cell">
										<i class="fa fa-exclamation-circle text-danger fa-lg"></i>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>No matching MSH</li>
													  <li>PCEHR not exist</li>
													  <li>Consent not found</li>
													  <li>Invalid ordering provider</li>
													  <li>No DOB for patient</li>
													  <li>No medicare/DVA</li>
													  <li>No matching filler order number</li>
													  <li>Incorrect indigenous status</li>
													  <li>Multiple MR type patient identifiers</li>
													  <li>Patient single name</li>
												  	  <li>No document to remove</li>
													  <li>Base64 encoding error</li>
													  <li>Invalid IHI</li>
													</ul>
												</div>"
										>
										${summaryData.lisErrorCntAtHts}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.lisCntAie2Hts - summaryData.lisErrorCntAtHts - summaryData.lisCntHts2Mhr) < 0 ? '0' : (summaryData.lisCntAie2Hts - summaryData.lisErrorCntAtHts - summaryData.lisCntHts2Mhr)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
												  		<li>Validation error</li>
													  	<li>Authorisation denied</li>
													  	<li>SOAP header fault</li>
													  	<li>SOAP body fault</li>
													  	<li>Metadata validation error</li>
													  	<li>Backend system unavailable</li>
													  	<li>No metadata found</li>
													</ul>
												</div>"
										>
										${summaryData.lisErrorCntAtHips}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.lisCntHts2Mhr - summaryData.lisErrorCntAtHips - summaryData.successLisCntUpload) < 0 ? '0' : (summaryData.lisCntHts2Mhr - summaryData.lisErrorCntAtHips - summaryData.successLisCntUpload)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
								</tr>
							</c:if>
							
							<!-- Radiology Report -->
							<c:if test="${summaryData.risCntAie2Hts > 0}" >
								<tr>
									<td class="center-cell">
										Radiology Report
									</td>
									<td class="center-cell font-weight-bold">
										${summaryData.risCntAie2Hts}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold">
										${summaryData.risCntHts2Mhr}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold text-primary" rowspan="2">${summaryData.successRisCntUpload}</td>
									<td rowspan="2">
										<canvas id="risStatsChart" style="height: 130px;"></canvas>
										<script>
										var ctx = document.getElementById('risStatsChart').getContext('2d');
										var data = {
												  labels: ["Radiology Report"],
												  datasets: [
												  {
												    label: "Total count from ${summaryHospital} to HTS",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
												    data: [${summaryData.risCntAie2Hts}]
												  },
												  {
													    label: "Error count from ${summaryHospital} to HTS",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.risErrorCntAtHts}]
												  },
												  {
												    label: "Total count from HTS to MHR",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
										           	data: [${summaryData.risCntHts2Mhr}]
												  }, 
												  {
													    label: "Error count from HTS to MHR",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.risErrorCntAtHips}]
												  },
												  {
												    label: "Success count from HTS to MyHR",
												    backgroundColor: 'rgba(54, 162, 235, 0.2)',
										            borderColor : 'rgba(54, 162, 235, 1)',
										           	borderWidth : 1,
												    data: [${summaryData.successRisCntUpload}]
												  }
												  ]
												};
										
												var myBarChart = new Chart(ctx, {
												  type: 'bar',
												  data: data,
												  options: {
													responsive: true,
													maintainAspectRatio: false,
													legend: {
														display: false
													},
												    barValueSpacing: 20,
												    scales: {
												      yAxes: [{
												        ticks: {
												          min: 0,
												        }
												      }]
												    }
												  }
												});
			
										</script>
									</td>
									<td rowspan="2">
										<c:if test="${summaryData.risCntAie2Hts != 0}">
											<canvas id="risPieChart" style="width:50%;height:50%;"></canvas>
							        		<script>
										        var config = {
										          type: 'doughnutLabels',
										          data: {
										            datasets: [{
										              data: [
										            	  ${summaryData.successRisCntUpload},
										            	  ${summaryData.risErrorCntAtHts + summaryData.risErrorCntAtHips}
										              ],
										              backgroundColor: [
										                "#36A2EB",
										                "#FF6384"
										              ],
										              label: 'Dataset 1'
										            }],
										            labels: [
										              "Success",
										              "Failure"
										            ]
										          },
										          options: {
										            responsive: true,
										            maintainAspectRatio: false,
										            legend: {
														display: false
										                },
										            animation: {
										              animateScale: true,
										              animateRotate: true
										            }
										          }
										        };
										        var ctx = document.getElementById("risPieChart").getContext("2d");
										        new Chart(ctx, config);
									    	</script>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="center-cell">
										<i class="fa fa-exclamation-circle text-danger fa-lg"></i>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>No matching MSH</li>
													  <li>PCEHR not exist</li>
													  <li>IHI Lookup failed</li>
													  <li>Invalid medicare number</li>
												  	  <li>No legal patient name</li>
												  	  <li>No matching filler order number</li>
												  	  <li>Invalid ordering provider</li>
												  	  <li>Document validation error</li>
												  	  <li>No medicare/DVA</li>
												  	  <li>No document to remove</li>
												  	  <li>Incorrect indigenous status</li>
												  	  <li>Non base64 character</li>
													</ul>
												</div>"
										>
										${summaryData.risErrorCntAtHts}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.risCntAie2Hts - summaryData.risErrorCntAtHts - summaryData.risCntHts2Mhr) < 0 ? '0' : (summaryData.risCntAie2Hts - summaryData.risErrorCntAtHts - summaryData.risCntHts2Mhr)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  	<li>Validation error</li>
													  	<li>Authorisation denied</li>
													  	<li>SOAP header fault</li>
													  	<li>SOAP body fault</li>
													  	<li>Metadata validation error</li>
													  	<li>Backend system unavailable</li>
													  	<li>No metadata found</li>
													</ul>
												</div>"
										>
										${summaryData.risErrorCntAtHips}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.risCntHts2Mhr - summaryData.risErrorCntAtHips - summaryData.successRisCntUpload) < 0 ? '0' : (summaryData.risCntHts2Mhr - summaryData.risErrorCntAtHips - summaryData.successRisCntUpload)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
								</tr>
							</c:if>
							
							
							<!-- PSML -->
							<c:if test="${summaryData.psmlCntAie2Hts > 0}" >
								<tr>
									<td class="center-cell">
										Pharmacist Shared Medicines List
									</td>
									<td class="center-cell font-weight-bold">
										${summaryData.psmlCntAie2Hts}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold">
										${summaryData.psmlCntHts2Mhr}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold text-primary" rowspan="2">${summaryData.successPsmlCntUpload}</td>
									<td rowspan="2">
										<canvas id="psmlStatsChart" style="height: 130px;"></canvas>
										<script>
										var ctx = document.getElementById('psmlStatsChart').getContext('2d');
										var data = {
												  labels: ["Pharmacist Shared Medicines List"],
												  datasets: [
												  {
												    label: "Total count from ${summaryHospital} to HTS",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
												    data: [${summaryData.psmlCntAie2Hts}]
												  },
												  {
													    label: "Error count from ${summaryHospital} to HTS",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.psmlErrorCntAtHts}]
												  },
												  {
												    label: "Total count from HTS to MHR",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
										           	data: [${summaryData.psmlCntHts2Mhr}]
												  }, 
												  {
													    label: "Error count from HTS to MHR",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.psmlErrorCntAtHips}]
												  },
												  {
												    label: "Success count from HTS to MyHR",
												    backgroundColor: 'rgba(54, 162, 235, 0.2)',
										            borderColor : 'rgba(54, 162, 235, 1)',
										           	borderWidth : 1,
												    data: [${summaryData.successPsmlCntUpload}]
												  }
												  ]
												};
										
												var myBarChart = new Chart(ctx, {
												  type: 'bar',
												  data: data,
												  options: {
													responsive: true,
													maintainAspectRatio: false,
													legend: {
														display: false
													},
												    barValueSpacing: 20,
												    scales: {
												      yAxes: [{
												        ticks: {
												          min: 0,
												        }
												      }]
												    }
												  }
												});
			
										</script>
									</td>
									<td rowspan="2">
										<c:if test="${summaryData.psmlCntAie2Hts != 0}">
											<canvas id="psmlPieChart" style="width:50%;height:50%;"></canvas>
							        		<script>
										        var config = {
										          type: 'doughnutLabels',
										          data: {
										            datasets: [{
										              data: [
										            	  ${summaryData.successPsmlCntUpload},
										            	  ${summaryData.psmlErrorCntAtHts + summaryData.psmlErrorCntAtHips}
										              ],
										              backgroundColor: [
										                "#36A2EB",
										                "#FF6384"
										              ],
										              label: 'Dataset 1'
										            }],
										            labels: [
										              "Success",
										              "Failure"
										            ]
										          },
										          options: {
										            responsive: true,
										            maintainAspectRatio: false,
										            legend: {
														display: false
										                },
										            animation: {
										              animateScale: true,
										              animateRotate: true
										            }
										          }
										        };
										        var ctx = document.getElementById("psmlPieChart").getContext("2d");
										        new Chart(ctx, config);
									    	</script>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="center-cell">
										<i class="fa fa-exclamation-circle text-danger fa-lg"></i>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>No matching MSH</li>
													  <li>Missing MSH field</li>
													  <li>Duplicated message</li>
													  <li>Consent withdrawn</li>
													  <li>PCEHR not exist</li>
													  <li>Bad CDA included</li>
													</ul>
												</div>"
										>
										${summaryData.psmlErrorCntAtHts}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.psmlCntAie2Hts - summaryData.psmlErrorCntAtHts - summaryData.psmlCntHts2Mhr) < 0 ? '0' : (summaryData.psmlCntAie2Hts - summaryData.psmlErrorCntAtHts - summaryData.psmlCntHts2Mhr)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
														<li>Invalid medicare number</li>
													  	<li>No legal patient name</li>
													  	<li>No matching filler order number</li>
													  	<li>Invalid ordering provider</li>
													  	<li>Document validation error</li>
													  	<li>No medicare/DVA</li>
													  	<li>No document to remove</li>
													  	<li>Incorrect indigenous status</li>
													  	<li>Non base64 character</li>
													</ul>
												</div>"
										>
										${summaryData.psmlErrorCntAtHips}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.psmlCntHts2Mhr - summaryData.psmlErrorCntAtHips - summaryData.successPsmlCntUpload) < 0 ? '0' : (summaryData.psmlCntHts2Mhr - summaryData.psmlErrorCntAtHips - summaryData.successPsmlCntUpload)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
								</tr>
							</c:if>
							
							<!-- Specialist Letter -->
							<c:if test="${summaryData.shsCntAie2Hts > 0}" >
								<tr>
									<td class="center-cell">
										Specialist Letter
									</td>
									<td class="center-cell font-weight-bold">
										${summaryData.shsCntAie2Hts}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold">
										${summaryData.shsCntHts2Mhr}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold text-primary" rowspan="2">${summaryData.successShsCntUpload}</td>
									<td rowspan="2">
										<canvas id="shsStatsChart" style="height: 130px;"></canvas>
										<script>
										var ctx = document.getElementById('shsStatsChart').getContext('2d');
										var data = {
												  labels: ["Specialist Letter"],
												  datasets: [
												  {
												    label: "Total count from ${summaryHospital} to HTS",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
												    data: [${summaryData.shsCntAie2Hts}]
												  },
												  {
													    label: "Error count from ${summaryHospital} to HTS",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.shsErrorCntAtHts}]
												  },
												  {
												    label: "Total count from HTS to MHR",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
										           	data: [${summaryData.shsCntHts2Mhr}]
												  }, 
												  {
													    label: "Error count from HTS to MHR",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.shsErrorCntAtHips}]
												  },
												  {
												    label: "Success count from HTS to MyHR",
												    backgroundColor: 'rgba(54, 162, 235, 0.2)',
										            borderColor : 'rgba(54, 162, 235, 1)',
										           	borderWidth : 1,
												    data: [${summaryData.successShsCntUpload}]
												  }
												  ]
												};
										
												var myBarChart = new Chart(ctx, {
												  type: 'bar',
												  data: data,
												  options: {
													responsive: true,
													maintainAspectRatio: false,
													legend: {
														display: false
													},
												    barValueSpacing: 20,
												    scales: {
												      yAxes: [{
												        ticks: {
												          min: 0,
												        }
												      }]
												    }
												  }
												});
			
										</script>
									</td>
									<td rowspan="2">
										<c:if test="${summaryData.shsCntAie2Hts != 0}">
											<canvas id="shsPieChart" style="width:50%;height:50%;"></canvas>
							        		<script>
										        var config = {
										          type: 'doughnutLabels',
										          data: {
										            datasets: [{
										              data: [
										            	  ${summaryData.successShsCntUpload},
										            	  ${summaryData.shsErrorCntAtHts + summaryData.shsErrorCntAtHips}
										              ],
										              backgroundColor: [
										                "#36A2EB",
										                "#FF6384"
										              ],
										              label: 'Dataset 1'
										            }],
										            labels: [
										              "Success",
										              "Failure"
										            ]
										          },
										          options: {
										            responsive: true,
										            maintainAspectRatio: false,
										            legend: {
														display: false
										                },
										            animation: {
										              animateScale: true,
										              animateRotate: true
										            }
										          }
										        };
										        var ctx = document.getElementById("shsPieChart").getContext("2d");
										        new Chart(ctx, config);
									    	</script>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="center-cell">
										<i class="fa fa-exclamation-circle text-danger fa-lg"></i>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>No matching MSH</li>
													  <li>Missing MSH field</li>
													  <li>Duplicated message</li>
													  <li>Consent withdrawn</li>
													  <li>PCEHR not exist</li>
													  <li>Bad CDA included</li>
													</ul>
												</div>"
										>
										${summaryData.shsErrorCntAtHts}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.shsCntAie2Hts - summaryData.shsErrorCntAtHts - summaryData.shsCntHts2Mhr) < 0 ? '0' : (summaryData.shsCntAie2Hts - summaryData.shsErrorCntAtHts - summaryData.shsCntHts2Mhr)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
														<li>Invalid medicare number</li>
													  	<li>No legal patient name</li>
													  	<li>No matching filler order number</li>
													  	<li>Invalid ordering provider</li>
													  	<li>Document validation error</li>
													  	<li>No medicare/DVA</li>
													  	<li>No document to remove</li>
													  	<li>Incorrect indigenous status</li>
													  	<li>Non base64 character</li>
													</ul>
												</div>"
										>
										${summaryData.shsErrorCntAtHips}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.shsCntHts2Mhr - summaryData.shsErrorCntAtHips - summaryData.successShsCntUpload) < 0 ? '0' : (summaryData.shsCntHts2Mhr - summaryData.shsErrorCntAtHips - summaryData.successShsCntUpload)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
								</tr>
							</c:if>
				
					
							<!-- Event Summary -->
							<c:if test="${summaryData.esCntAie2Hts > 0}" >
								<tr>
									<td class="center-cell">
										Event Summary
									</td>
									<td class="center-cell font-weight-bold">
										${summaryData.esCntAie2Hts}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold">
										${summaryData.esCntHts2Mhr}
									</td>
									<td></td>
									<td class="center-cell font-weight-bold text-primary" rowspan="2">${summaryData.successEsCntUpload}</td>
									<td rowspan="2">
										<canvas id="esStatsChart" style="height: 130px;"></canvas>
										<script>
										var ctx = document.getElementById('esStatsChart').getContext('2d');
										var data = {
												  labels: ["Event Summary"],
												  datasets: [
												  {
												    label: "Total count from ${summaryHospital} to HTS",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
												    data: [${summaryData.esCntAie2Hts}]
												  },
												  {
													    label: "Error count from ${summaryHospital} to HTS",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.esErrorCntAtHts}]
												  },
												  {
												    label: "Total count from HTS to MHR",
												    backgroundColor: 'rgba(255, 206, 86, 0.2)',
										            borderColor : 'rgba(255, 206, 86, 1)',
												    borderWidth : 1,
										           	data: [${summaryData.esCntHts2Mhr}]
												  }, 
												  {
													    label: "Error count from HTS to MHR",
													    backgroundColor: 'rgba(255, 99, 132, 0.2)',
													    borderColor : 'rgba(255, 99, 132, 1)',
													    borderWidth : 1,
											           	data: [${summaryData.esErrorCntAtHips}]
												  },
												  {
												    label: "Success count from HTS to MyHR",
												    backgroundColor: 'rgba(54, 162, 235, 0.2)',
										            borderColor : 'rgba(54, 162, 235, 1)',
										           	borderWidth : 1,
												    data: [${summaryData.successEsCntUpload}]
												  }
												  ]
												};
										
												var myBarChart = new Chart(ctx, {
												  type: 'bar',
												  data: data,
												  options: {
													responsive: true,
													maintainAspectRatio: false,
													legend: {
														display: false
													},
												    barValueSpacing: 20,
												    scales: {
												      yAxes: [{
												        ticks: {
												          min: 0,
												        }
												      }]
												    }
												  }
												});
			
										</script>
									</td>
									<td rowspan="2">
										<c:if test="${summaryData.esCntAie2Hts != 0}">
											<canvas id="esPieChart" style="width:50%;height:50%;"></canvas>
							        		<script>
										        var config = {
										          type: 'doughnutLabels',
										          data: {
										            datasets: [{
										              data: [
										            	  ${summaryData.successEsCntUpload},
										            	  ${summaryData.esErrorCntAtHts + summaryData.esErrorCntAtHips}
										              ],
										              backgroundColor: [
										                "#36A2EB",
										                "#FF6384"
										              ],
										              label: 'Dataset 1'
										            }],
										            labels: [
										              "Success",
										              "Failure"
										            ]
										          },
										          options: {
										            responsive: true,
										            maintainAspectRatio: false,
										            legend: {
														display: false
										                },
										            animation: {
										              animateScale: true,
										              animateRotate: true
										            }
										          }
										        };
										        var ctx = document.getElementById("esPieChart").getContext("2d");
										        new Chart(ctx, config);
									    	</script>
										</c:if>
									</td>
								</tr>
								
								<tr>
									<td class="center-cell">
										<i class="fa fa-exclamation-circle text-danger fa-lg"></i>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
													  <li>No matching MSH</li>
													  <li>Missing MSH field</li>
													  <li>Duplicated message</li>
													  <li>Consent withdrawn</li>
													  <li>PCEHR not exist</li>
													  <li>Bad CDA included</li>
													</ul>
												</div>"
										>
										${summaryData.esErrorCntAtHts}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.esCntAie2Hts - summaryData.esErrorCntAtHts - summaryData.esCntHts2Mhr) < 0 ? '0' : (summaryData.esCntAie2Hts - summaryData.esErrorCntAtHts - summaryData.esCntHts2Mhr)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
									<td></td>
									<td class="center-cell text-danger">
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true"
										title="<div class='text-left'>Unsuccessful cases can be :<br/><br/>
													<ul style='list-style-type:disc'>
														<li>Invalid medicare number</li>
													  	<li>No legal patient name</li>
													  	<li>No matching filler order number</li>
													  	<li>Invalid ordering provider</li>
													  	<li>Document validation error</li>
													  	<li>No medicare/DVA</li>
													  	<li>No document to remove</li>
													  	<li>Incorrect indigenous status</li>
													  	<li>Non base64 character</li>
													</ul>
												</div>"
										>
										${summaryData.esErrorCntAtHips}
										</a>
										&nbsp;
										<a class="text-danger" href="#" data-toggle="tooltip" data-placement="auto" data-html="true" title="${(summaryData.esCntHts2Mhr - summaryData.esErrorCntAtHips - summaryData.successEsCntUpload) < 0 ? '0' : (summaryData.esCntHts2Mhr - summaryData.esErrorCntAtHips - summaryData.successEsCntUpload)}">
											<i class="fa fa-trash-o text-danger"></i>
										</a>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				<!-- Disclaimer block -->
				<div class="alert alert-info">
					<strong>Disclaimer</strong> This data is intended solely for your reference. It is important to understand that under certain circumstances, such as instances where the MyHR service encounters downtime or technical issues, the data may contain inconsistencies with what you sent. 
				</div>
					
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
			<i class="fa fa-frown-o fa-lg"></i>&nbsp;&nbsp;<span id="validation-error-message">Please make sure <b>Hospital</b>, <b>FromDate</b> & <b>ToDate</b> are filled in.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FromDate can go back up to 2 years, and the date range cannot exceed 6 weeks.</span>
		</div>
	</div>
</div>