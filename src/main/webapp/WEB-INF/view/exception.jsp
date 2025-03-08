<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #212529;
            color: #ffffff; 
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #343a40; 
            padding: 25px;
            border-radius: 10px;
            max-width: 500px;
            box-shadow: 0px 0px 15px rgba(255, 0, 0, 0.2); 
            text-align: center;
        }
        .error-icon {
            font-size: 50px;
            color: #dc3545; 
            margin-bottom: 15px;
        }
        .alert {
            color: #fff;
            background-color: #dc3545; 
            border: none;
            padding: 15px;
            border-radius: 5px;
        }
        .btn-primary {
            width: 100%;
            background-color: #007bff; /* Bootstrap primary color */
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-icon">⚠️</div>
        <h2>Error Occurred</h2>
        <div class="alert">
            <strong>Something went wrong.</strong><br>
            ${errorMessage}
        </div>
        <form>
            <button type="submit" class="btn btn-primary" formaction="backHomeException">Back to Home</button>
        </form>
    </div>
</body>
</html>
