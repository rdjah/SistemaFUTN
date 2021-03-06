
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FUTN-Add a new Student</title>

<jsp:include page="/includes/bootstrapLinks.jsp"></jsp:include>

<jsp:include page="/JS/passwordValidation.jsp"></jsp:include>

</head>

<body>

	<jsp:include page="/includes/Header.jsp"></jsp:include>

	<div class="container">

		<c:if test="${userAuthenticated!=null}">

			<div class="row row-offcanvas row-offcanvas-right active">

				<jsp:include page="/includes/Menu.jsp"></jsp:include>

				<c:if test="${userAuthenticated.scholar==true}">

					<div class="col-12 col-md-9">

						<h2>Add a new student</h2>

						<form name="UserAddForm" action="AddUser" method="post">

							<div class="form-group">
								<label for="legajo" class="sr-only">Legajo</label> <input
									type="text" pattern="[0-9]{5}" name="legajo" id="legajo"
									placeholder="Legajo" required autofocus>
							</div>

							<div class="form-group">
								<label for="firstName" class="sr-only">First name</label> <input
									type="text" name="firstName" id="firstName"
									placeholder="First name" required>
							</div>

							<div class="form-group">
								<label for="lastName" class="sr-only">Last name</label> <input
									type="text" name="lastName" id="lastName"
									placeholder="Last name" required>
							</div>

							<div class="form-group">
								<label for="adress" class="sr-only">Adress</label> <input
									type="text" name="adress" id="adress" placeholder="Adress"
									required>
							</div>

							<div class="form-group">
								<label for="phone1" class="sr-only">Phone 1</label> <input
									type="tel" name="phone1" id="phone1" placeholder="Phone"
									pattern="[- \d]*" title="Enter a phone number" required>
							</div>

							<div class="form-group">
								<label for="phone2" class="sr-only">Phone 2</label> <input
									type="tel" name="phone2" id="phone2" placeholder="Phone 2"
									pattern="[- \d]*" title="Enter a phone number">
							</div>

							<div class="form-group">
								<label for="mail" class="sr-only">Mail</label> <input
									type="email" name="mail" id="mail" placeholder="Mail" required>
							</div>

							<div class="form-group">
								<label for="credit" class="sr-only">Credit</label> <input
									type="number" name="credit" id="credit" step="1" min="0"
									placeholder="Credit" required>
							</div>

							<div class="form-group">
								<label for="password" class="sr-only">Password</label> <input
									type="password" name="password" id="password"
									placeholder="Password" required>
							</div>

							<div class="form-group">
								<label for="confirmPassword" class="sr-only">Password</label> <input
									type="password" name="confirmPassword" id="confirmPassword"
									placeholder="Confirm password" required>
							</div>

							<div class="form-group">
								<input type="submit" class="btn btn-primary" name="Add" id="Add"
									value="Add">
							</div>

						</form>

						<c:if test="${exceptionMessage !=null}">
							<jsp:include page="/includes/exceptionMessage.jsp"></jsp:include>
						</c:if>

					</div>

				</c:if>

			</div>

		</c:if>

		<c:if
			test="${userAuthenticated==null || userAuthenticated.scholar==false}">
			<jsp:include page="/includes/permissionMessage.jsp"></jsp:include>
		</c:if>

		<jsp:include page="/includes/Footer.jsp"></jsp:include>
	</div>
	<!-- /container -->

</body>
</html>