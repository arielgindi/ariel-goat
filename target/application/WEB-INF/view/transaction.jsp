<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Song</title>
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
                    // disable everything except transactionValue and transactionID
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
                <c:when test="${mode eq 'create'}">New Song</c:when>
                <c:when test="${mode eq 'edit'}">Edit Song</c:when>
                <c:when test="${mode eq 'view'}">Song Details</c:when>
            </c:choose>
        </h2>

        <!-- For create or edit pages -->
        <c:if test="${mode eq 'create' or mode eq 'edit'}">
            <form action="${mode eq 'create' ? 'saveTransaction' : 'updateTransaction'}" method="post">
                
                <!-- Song Genre (Transaction Type) -->
                <div class="mb-3">
                    <label class="form-label">Song Genre:</label>
                    <select class="form-select" name="transactionType">
                        <option value="Send"  ${transaction.type.name() eq 'send'    ? 'selected' : ''}>Send</option>
                        <option value="Receive" ${transaction.type.name() eq 'receive' ? 'selected' : ''}>Receive</option>
                    </select>
                </div>

                <!-- Artist -->
                <!-- Updated: name="artistName" instead of "otherPhoneNumber" -->
                <div class="mb-3">
                    <label class="form-label">Artist:</label>
                    <input type="text" class="form-control" name="artistName"
                           value="${transaction.artistName}" placeholder="Enter artist"
                           ${mode eq 'edit' ? 'readonly' : ''} required>
                </div>

                <!-- Hidden ID + Release Date if editing -->
                <c:if test="${mode eq 'edit'}">
                    <input type="hidden" name="transactionID" value="${transaction.transactionId}">
                    <div class="mb-3">
                        <label class="form-label">Release Date:</label>
                        <input type="text" class="form-control" value="${transaction.transactionDate}" readonly>
                    </div>
                </c:if>

                <!-- Song Length in seconds (Transaction Value) -->
                <div class="mb-3">
                    <label class="form-label">Song Length in seconds:</label>
                    <input type="number" class="form-control" name="transactionValue"
                           value="${transaction.transactionValue}" placeholder="Enter length in seconds" required>
                </div>

                <!-- Song Description (Transaction Reason) -->
                <div class="mb-3">
                    <label class="form-label">Song Description:</label>
                    <input type="text" class="form-control" name="transactionReason"
                           value="${transaction.transactionReason}" placeholder="Enter short description"
                           ${mode eq 'edit' ? 'readonly' : ''}>
                </div>

                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${mode eq 'create'}">Save</c:when>
                        <c:when test="${mode eq 'edit'}">Update</c:when>
                    </c:choose>
                </button>
            </form>
        </c:if>

        <!-- View Mode -->
        <c:if test="${mode eq 'view'}">
            <div class="mb-3">
                <label class="form-label"><strong>Song Genre:</strong></label>
                <p>${transaction.type}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Artist:</strong></label>
                <p>${transaction.artistName}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Song Length in seconds:</strong></label>
                <p>${transaction.transactionValue}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Release Date:</strong></label>
                <p>${transaction.transactionDate}</p>
            </div>
            <div class="mb-3">
                <label class="form-label"><strong>Song Description:</strong></label>
                <p>${transaction.transactionReason}</p>
            </div>

            <!-- Existing images logic -->
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

        <!-- Back to home -->
        <form action="backToHome" method="get">
            <button type="submit" class="btn btn-primary">Back to Home</button>
        </form>
    </div>
</body>
</html>
