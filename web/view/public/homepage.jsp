<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>La Mer Hotel - Home</title>
</head>
<body>

    <jsp:include page="/view/common/navbar.jsp" />

    <h1>La Mer Hotel</h1>

    <p>
        Chào mừng bạn đến với La Mer Hotel.
    </p>

    <h2>Tìm phòng</h2>

    <form action="${pageContext.request.contextPath}/view/public/search-result.jsp" method="get">

        <label>Ngày nhận phòng:</label><br>
        <input type="date" name="checkin"><br><br>

        <label>Ngày trả phòng:</label><br>
        <input type="date" name="checkout"><br><br>

        <label>Số phòng:</label><br>
        <select name="rooms">
            <option value="1">1 phòng</option>
            <option value="2">2 phòng</option>
            <option value="3">3 phòng</option>
        </select><br><br>

        <label>Hạng phòng:</label><br>
        <select name="roomType">
            <option value="">Tất cả hạng phòng</option>
            <option value="1">Standard Cozy Room</option>
            <option value="2">Superior Ocean View</option>
            <option value="3">Deluxe Family Suite</option>
            <option value="4">Premium Executive Club</option>
            <option value="5">La Mer President Suite</option>
        </select><br><br>

        <button type="submit">Tìm phòng trống</button>
    </form>

    <hr>

    <h2>Giới thiệu</h2>

    <p>
        La Mer Hotel là khách sạn nghỉ dưỡng gần biển, cung cấp các hạng phòng phù hợp
        cho khách du lịch, gia đình và khách công tác.
    </p>

    <h2>Dịch vụ nổi bật</h2>

    <ul>
        <li>Phòng nghỉ tiện nghi</li>
        <li>Hồ bơi</li>
        <li>Buffet sáng</li>
        <li>Spa & Massage</li>
        <li>Đưa đón sân bay</li>
    </ul>

</body>
</html>