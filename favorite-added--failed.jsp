<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8" />
<title>Movies List</title>
<link rel="stylesheet" href="style/style.css" contentType="text/css">
</head>
<body>
	<header class="main-header">
		<div>
		<h1>Movie Cruiser</h1>
	<img src="images/logo.png" width="40px" height="40px"/>		
		</div>
		<h2>
		<div>
		<a href="#" class="header-links">Movies</a>
		&nbsp;&nbsp;
		<a href="displayFavorite" class="header-links">Favorites</a>
		</div>
		</h2>
	</header>
	<div class="main-container">
		<h2>Movies</h2>
		<br>
		
		<font style="color: green;font-weight: bold;">
		<p align="center"class="align-self-start">
		Movie already added as Favorite 
			</p>
		</font>
	
		<br>
		<table class="movies-table" id="movies">
			<tr>
				<th>Title</th>
				<th>Box Office</th>
				<th>Genre</th>
				<th>Has Teaser</th>
				<th>Action</th>
			</tr>
			  
              <c:forEach var="movie" items="${listMovie}">
                <tr>
                
                    <td><c:out value="${movie.title}" /></td>
                    <td><c:out value="${movie.boxOffice}" /></td>
                 <td>&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${movie.genre}" /></td>
                    <td><c:out value="${movie.hasTeaser}" /></td>
                    <td>
                      <a href="favorite?id=<c:out value='${movie.id}' />">Add to Favorite</a>
                   &nbsp;&nbsp;&nbsp;&nbsp;                    
                    </td>
                </tr>
            </c:forEach>
		</table>
	</div>
	<footer class="main-footer">
		<p>Copyright &copy; 2019</p>
	</footer>

</body>
</html>