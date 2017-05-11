<%@page import="futn.CopyPrice"%>
<%@page import="negocio.CtrlFutn"%>

<html lang="en">
<head>
<meta charset="utf-8">

<title>FUTN-Copy price change</title>

<jsp:include page="/includes/bootstrapLinks.jsp"></jsp:include>

<%
	CtrlFutn ctrl = new CtrlFutn();
	CopyPrice actualCopyPrice = ctrl.getActualCopyPrice();
%>
<!-- realizar validacion front end de que haya cambiado los precios-->


</head>
<body>

	<jsp:include page="/includes/Header.jsp"></jsp:include>

	<div class="container">

		<div class="row row-offcanvas row-offcanvas-right active">

			<jsp:include page="/includes/Menu.jsp"></jsp:include>

			<div class="col-12 col-md-9">

				<h1>Copy price change</h1>

				<h3>Current copy price</h3>

				<p>
					Begin date:
					<%
					out.println(actualCopyPrice.getBeginDate());
				%>
				</p>

				<p>
					Simple copy:
					<%
					out.println(actualCopyPrice.getSimplePrice());
				%>
				</p>

				<p>
					Duplex copy:
					<%
					out.println(actualCopyPrice.getDuplexPrice());
				%>
				</p>

				<h3>New copy price</h3>

				<form name="PriceChangeForm" action="PriceChange" method="post">

					<div class="form-group">
						<label for="simple">Simple</label> <input type="number"
							name="simple" id="simple" step="0.01" min="0"
							value="<%=actualCopyPrice.getSimplePrice()%>" required autofocus>
					</div>

					<div class="form-group">
						<label for="duplex">Duplex</label> <input type="number"
							name="duplex" id="duplex" step="0.01" min="0"
							value="<%=actualCopyPrice.getDuplexPrice()%>" required>
					</div>

					<input type="submit" name="Save" value="Save"
						class="btn btn-primary">

				</form>
			</div>
		</div>
		
	    <jsp:include page="/includes/Footer.jsp"></jsp:include>
		
	</div>
	<!-- /container -->

</body>
</html>