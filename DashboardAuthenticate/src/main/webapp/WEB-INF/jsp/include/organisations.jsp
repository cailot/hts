<style>
	.tooltip-inner {
	max-width: 500px !important;
	  background-color: #4E73DF;
	  
	}
	.tooltip.bs-tooltip-right .arrow:before {
	  border-right-color: #4E73DF !important;
	}
	.tooltip.bs-tooltip-left .arrow:before {
	 border-left-color: #4E73DF !important;
	}
	.tooltip.bs-tooltip-bottom .arrow:before {
	 border-bottom-color: #4E73DF !important;
	}
	.tooltip.bs-tooltip-top .arrow:before {
	 border-top-color: #4E73DF !important;
	}
	
	.table td {
  	padding: 0;
	}
</style>
	
<div class="row">
	<div class="col-12">
		<div class="card shadow mb-4 border-primary">
            <h6 class="card-header py-3 d-flex flex-row align-items-center justify-content-between m-0 font-weight-bold text-primary">CMS Organisations <i class="fas fa-cog fa-lg" style="cursor: pointer;" onclick="window.open('${console}', '_blank');"></i></h6>
            <!-- Card Body -->
            <div class="card-body">
	            <div class="row col-12">
	            <!-- OrganisationInfo loop -->
					<c:forEach items="${organisationList}" var="org">
		            	<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 mb-5" data-toggle="tooltip" data-html="true" title="<div class='text-left pl-1'><i class='fas fa-user-alt'></i>&nbsp; ${org.person}<br><i class='fas fa-phone-alt'></i>&nbsp;&nbsp; ${org.contact}<br><i class='far fa-envelope'></i>&nbsp; ${org.email}<br><i class='fas fa-home'></i>&nbsp; ${org.address}</div>">
		            		<div class="card border-left-success">
		            			<div class="card-body text-center">
		            				<div class="table-responsive">
		            					<table class="table no-cellpadding table-borderless">
		            						<tbody>
		            							<tr>
		            								<td style="padding-bottom: 0.5em;">
		            									<span class="text-xs font-weight-bold text-success text-uppercase"><c:out value="${org.name}" /></span>
		            								</td>
		            							</tr>
		            							<tr>
		            								<td class="text-center align-middle">
		            									<span class="text-xs font-weight-bold"><c:out value="${org.inboundTotal}" /></span>&nbsp;&nbsp;&nbsp;
		            									<a class="text-decoration-none" href="${pageContext.request.contextPath}/orgDetail?id=${org.id}&acronym=${org.acronym}&name=${org.name}&portfolio=2"> 
											    			<span class="h5 font-weight-bold text-gray-800"><c:out value="${org.acronym}" /></span>&nbsp;&nbsp;&nbsp;
					                        			</a>
					                        			<span class="text-xs font-weight-bold"><c:out value="${org.outboundTotal}" /></span>
		            								</td>
		            							</tr>
		            						</tbody>
		            					</table>
		            				</div>
		            			</div>
		            		</div>
		            	</div>
	            	</c:forEach>
				</div>
       	 	</div> <!-- end of main card body -->
		</div><!-- end of card-->
	</div>
</div><!-- end of first row for PCMS Contents -->
	
