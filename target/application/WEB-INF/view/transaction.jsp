<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #0d6efd; 
            color: white;
        }
        .container {
            background-color: white;
            color: black;
            padding: 20px;
            border-radius: 10px;
            max-width: 500px;
            margin: auto;
            margin-top: 50px;
        }
        .btn-primary, .btn-warning {
            width: 100%;
            margin-bottom: 10px;
        }
        .transaction-image {
		    display: block;
		    max-width: 100%;
		    height: auto;
		    margin: 10px auto;
		    border-radius: 8px; 
		}
    </style>
    <script>
	    document.addEventListener("DOMContentLoaded", function () {
	        var mode = "<c:out value='${mode}'/>".trim(); 
	
	        if (mode === "edit") {
	            console.log("Edit mode detected - Disabling fields except transactionValue");
	
	            var formElements = document.querySelectorAll("form input, form select");
	
	            formElements.forEach(function (element) {
	            	// Disable all except transactionValue
	                if (element.name !== "transactionValue" && element.name !== "transactionID") {
	                    element.setAttribute("disabled", "true"); 
	                }
	            });
	        }
	    });
	</script>
    
</head>
<body>
    <div class="container">
        <h2 class="text-center">
            <c:choose>
                <c:when test="${mode eq 'create'}">New Transaction</c:when>
                <c:when test="${mode eq 'edit'}">Edit Transaction</c:when>
                <c:when test="${mode eq 'view'}">Transaction Details</c:when>
            </c:choose>
        </h2>

        <!-- For create or edit pages -->
        <c:if test="${mode eq 'create' or mode eq 'edit'}">
            <form action="${mode eq 'create' ? 'saveTransaction' : 'updateTransaction'}" method="post">                

                <!-- Transaction Type-->
                <div class="mb-3">
                    <label class="form-label">Transaction Type:</label>
                    <select class="form-select" name="transactionType"}>
                        <option value="Send" ${transaction.type.name() eq 'send' ? 'selected' : ''}>Send</option>
                        <option value="Receive" ${transaction.type.name() eq 'recieve' ? 'selected' : ''}>Receive</option>
                    </select>
                </div>

                <!-- Other Phone Number, set as readonly on edit mode-->
                <div class="mb-3">
                    <label class="form-label">Other Phone Number:</label>
                    <input type="text" class="form-control" name="otherPhoneNumber"
                    	   value="${transaction.otherPhoneNum}" placeholder="Enter phone number" 
                    	   ${mode eq 'edit' ? 'readonly' : ''} required>
                </div>
				<!-- Set the ID and show the transaction date if the mode is edit-->
				<c:if test="${mode eq 'edit'}">
                    <input type="hidden" name="transactionID" value="${transaction.transactionId}">
                    
                    <div class="mb-3">
				        <label class="form-label">Transaction Date:</label>
				        <input type="text" class="form-control" value="${transaction.transactionDate}" readonly>
				    </div>
                </c:if>
                <!-- Transaction Value -->
                <div class="mb-3">
                    <label class="form-label">Transaction Value:</label>
                    <input type="number" class="form-control" name="transactionValue" value="${transaction.transactionValue}" placeholder="Enter amount" required>
                </div>
                <!-- Transaction Reason, set as readonly on edit mode-->
                <div class="mb-3">
                    <label class="form-label">Transaction Reason:</label>
                    <input type="text" class="form-control" name="transactionReason"
                    			value="${transaction.transactionReason}" placeholder="Enter reason"
                    			${mode eq 'edit' ? 'readonly' : ''}>           			
                </div>
                <!-- Submit Button, set as save for create and update for edit -->
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${mode eq 'create'}">Save</c:when>
                        <c:when test="${mode eq 'edit'}">Update</c:when>
                    </c:choose>
                </button>
            </form>
        </c:if>

        <!-- JSP for View Mode -->
        <c:if test="${mode eq 'view'}">
            <div class="mb-3">
                <label class="form-label"><strong>Transaction Type:</strong></label>
                <p>${transaction.type}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Other Phone Number:</strong></label>
                <p>${transaction.otherPhoneNum}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Transaction Value:</strong></label>
                <p>$${transaction.transactionValue}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Transaction Date:</strong></label>
                <p>${transaction.transactionDate}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Transaction Reason:</strong></label>
                <p>${transaction.transactionReason}</p>
            </div>
            <!-- Set the images based on the transaction value -->
			<c:choose>
			    <c:when test="${transaction.transactionValue le 500}">
			        <div class="text-center">
			            <img src="${pageContext.request.contextPath}/resources/images/NoMoney.jpg" class="img-fluid transaction-image">
			        </div>
			    </c:when>
			    <c:when test="${transaction.transactionValue gt 500 and transaction.transactionValue le 1500}">
			        <div class="text-center">
			            <img src="${pageContext.request.contextPath}/resources/images/MoreMoney.jpg" class="img-fluid transaction-image">
			        </div>
			    </c:when>
			    <c:when test="${transaction.transactionValue gt 2000}">
			        <div class="text-center">
			            <img src="${pageContext.request.contextPath}/resources/images/Sigma.jpg" class="img-fluid transaction-image">
			        </div>
			    </c:when>
			    <c:otherwise>
				    <p>No transaction value set</p>
				</c:otherwise>
			</c:choose>
        </c:if>
        
        <!-- Back to home Button -->
        <form action="backToHome" method="get">
            <button type="submit" class="btn btn-primary">Back to Home</button>
        </form>
    </div>
</body>
</html>
