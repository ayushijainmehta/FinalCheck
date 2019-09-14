package com.moviecruiser.movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MovieDAO movieDAO;

	public void init() {
		movieDAO = new MovieDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String action = request.getServletPath();
		try {
			switch (action) {
			case "/favorite":
				addFavorite(request, response);
				break;
			case "/update":
				updateMovie(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/displayFavorite":
				listFavorite(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			default:
				System.out.print("not valid page");
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}

	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		movieDAO.deleteUser(id);
		List<Movie> listMovie = movieDAO.selectAllFavorites();
		boolean exist = listMovie.isEmpty();
		
		if (exist == false) {
		request.setAttribute("listFavorite", listMovie);
		request.getRequestDispatcher("favorite-removed-success.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect("favorites-empty.jsp");
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Movie selectedMovie = movieDAO.selectMovie(id);

		request.setAttribute("movie", selectedMovie);
		RequestDispatcher dispatcher = request.getRequestDispatcher("edit-movie.jsp");

		dispatcher.forward(request, response);

	}

	private void updateMovie(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
	
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String boxOffice = request.getParameter("boxOffice");
		String active = request.getParameter("active");
		String dateOfLaunch = request.getParameter("dateOfLaunch");
		String genre = request.getParameter("genre");
		String hasTeaser = request.getParameter("hasTeaser");
		System.out.println(hasTeaser);
		System.out.print(active);
		Movie movie = new Movie(id, title, boxOffice, active, dateOfLaunch, genre, hasTeaser);

		movieDAO.updateMovie(movie);
		response.sendRedirect("edit-movie-status.jsp");
	}

	private void listFavorite(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Movie> listMovie = movieDAO.selectAllFavorites();
		boolean exist = listMovie.isEmpty();
		System.out.print(exist);
		if (exist == false) {
			request.setAttribute("listFavorite", listMovie);
			request.getRequestDispatcher("favorites.jsp").forward(request, response);
		} else {
			response.sendRedirect("favorites-empty.jsp");
		}

	}

	private void addFavorite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		int movieId = Integer.parseInt(request.getParameter("id"));
		int userId = 1;// need to work on it

		Favorite favorite = new Favorite(movieId, userId);
		movieDAO.insertFavorite(favorite);


		List<Movie> listMovie = movieDAO.selectAllMovies();
		request.setAttribute("listMovie", listMovie);
		request.getRequestDispatcher("favorite-added-success.jsp").forward(request, response);

	}

}