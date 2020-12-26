<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.container {
    width: 500px;
    padding: 10px;
}

.message-blue {
    position: relative;
    margin-left: 20px;
    margin-bottom: 10px;
    padding: 10px;
    background-color: #A8DDFD;
    width: 200px;
    height: 50px;
    text-align: left;
    border-radius: 10px;
}

.message-orange {
    position: relative;
    margin-bottom: 10px;
    margin-left: calc(100% - 240px);
    padding: 10px;
    background-color: #f8e896;
    width: 200px;
    height: 50px;
    text-align: left;
    border-radius: 10px;
}

.message-content {
    padding: 0;
    margin: 0;
}

.message-timestamp-right {
    position: absolute;
    font-size: .85em;
    font-weight: 300;
    bottom: 5px;
    right: 5px;
}

.message-timestamp-left {
    position: absolute;
    font-size: .85em;
    font-weight: 300;
    bottom: 5px;
    left: 5px;
}
</style>
</head>
<body>

<div class="container">
    <div class="message-blue">
        <p class="message-content">This is an awesome message!</p>
        <div class="message-timestamp-left">SMS 13:37</div>
    </div>
    
    <div class="message-orange">
        <p class="message-content">I agree that your message is awesome!</p>
        <div class="message-timestamp-right">SMS 13:37</div>
    </div>
    
    <div class="message-blue">
        <p class="message-content">Thanks!</p>
        <div class="message-timestamp-left">SMS 13:37</div>
    </div>
</div>

</body>
</html>