<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View All Songs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #0d6efd; 
            color: white;
        }
        .btn-primary, .btn-info, .btn-warning {
            background-color: #cfe2ff;
            border-color: #cfe2ff;
            color: black;
        }
        .btn-danger {
            background-color: red;
            border-color: red;
        }
        .btn-primary:hover, .btn-info:hover, .btn-warning:hover {
            background-color: #a0c4ff;
            border-color: #a0c4ff;
            color: black;
        }
        .btn-danger:hover {
            background-color: darkred;
            border-color: darkred;
        }
        .table {
            background-color: white;
            color: black;
        }
        .table-light {
            background-color: #e9ecef;
        }
    </style>
</head>
<body class="p-4">
    <form>
        <div class="container">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h2>View All Songs</h2>
            </div>
            <div class="mb-3">
                <button class="btn btn-primary" formaction="create" type="submit">Add Song</button>
                <button class="btn btn-info" formaction="show" type="submit">View Song</button>
                <button class="btn btn-warning" formaction="edit" type="submit">Edit</button>
                <button class="btn btn-warning" formaction="remove" type="submit">Remove</button>
                <button class="btn btn-danger" formaction="logout" style="display: inline;" type="submit">Logout</button>
            </div>
            <table class="table table-bordered table-striped">
                <thead class="table-light">
                    <tr>
                        <th>Select</th>
                        <th>Song Genre</th>
                        <th>Artist</th>
                        <th>Song Length in seconds</th>
                        <th>Release Date</th>
                        <th>Song Description</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="transaction" items="${transactions}">
                        <tr>
                            <td>
                                <input type="radio" name="transaction" value="${transaction.transactionId}">
                            </td>
                            <td>${transaction.type}</td>
                            <td>${transaction.otherPhoneNum}</td>
                            <td>$${transaction.transactionValue}</td>
                            <td>${transaction.transactionDate}</td>
                            <td>${transaction.transactionReason}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </form>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
