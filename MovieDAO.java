package com.moviecruiser.movie;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
	
    private String jdbcURL = "jdbc:mysql://localhost:3306/moviecruiser?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "2796";
private static final String SELECT_FAVORITE_SQL="select * from Favorites where fv_mv_id=?";
    private static final String DELETE_FAVORITE_SQL = "delete from Favorites where fv_mv_id= ?;";
    private static final String INSERT_FAVORITE_SQL = "INSERT INTO Favorites" + "  (fv_mv_id,fv_us_id) VALUES " +
            " (?,?);";
    private static final String UPDATE_MOVIES_SQL = "update MOVIE set mv_title = ?,mv_box_office= ?, mv_active =?,mv_date_of_launch=?,mv_genre=?,mv_teaser=?"
    		+ " where mv_id = ?;";
    private static final String SELECT_ALL_MOVIES = "select * from movie";
   
    private static final String SELECT_MOVIE_BY_ID = "select mv_id,mv_title,mv_box_office,mv_active,mv_date_of_launch,mv_genre,mv_teaser from movie where mv_id =?";
    private static final String SELECT_FAVORITE_BY_ID = "select movie.mv_id,movie.mv_title,movie.mv_box_office,movie.mv_active,movie.mv_date_of_launch,movie.mv_genre,movie.mv_teaser from movie,Favorites where movie.mv_id =Favorites.fv_mv_id";
    public MovieDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    public boolean checkFavorite(int id) {
        boolean favoriteExist;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FAVORITE_SQL);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
if(!rs.next())
{
	favoriteExist=false;
}
else
{
	favoriteExist=true;
}
          
        } catch (SQLException e) {
            printSQLException(e);
        }
        return favoriteExist;
    }
  
    public Movie selectMovie(int id) {
        Movie movies = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MOVIE_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	
                String title = rs.getString("mv_title");
                String boxOffice = rs.getString("mv_box_office");
                String active = rs.getString("mv_active");
                String dateOfLaunch = rs.getString("mv_date_of_launch");
                String genre = rs.getString("mv_genre");
                String hasTeaser= rs.getString("mv_teaser");
                movies=new Movie(id,title,boxOffice,active,dateOfLaunch,genre,hasTeaser);  
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return movies;
    }

    public List < Movie > selectAllMovies() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Movie> movies= new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MOVIES);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int id = rs.getInt("mv_id");
                String title = rs.getString("mv_title");
                String boxOffice = rs.getString("mv_box_office");
                String active = rs.getString("mv_active");
                String dateOfLaunch = rs.getString("mv_date_of_launch");
                String genre = rs.getString("mv_genre");
                String hasTeaser= rs.getString("mv_teaser");
                movies.add(new Movie(id,title,boxOffice,active,dateOfLaunch,genre,hasTeaser));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return movies;
    }
    public void insertFavorite(Favorite favorite) throws SQLException {
        System.out.println(INSERT_FAVORITE_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FAVORITE_SQL)) {
            preparedStatement.setInt(1, favorite.getMovieId());
           // preparedStatement.setString(2, favorite.getUserId());
            preparedStatement.setInt(2,2);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_FAVORITE_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateMovie(Movie movie) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_MOVIES_SQL);) {
            statement.setString(1,movie.getTitle());
            statement.setString(2,movie.getBoxOffice());
            statement.setString(3,movie.getActive());
            statement.setString(4,movie.getDateOfLaunch());
            statement.setString(5,movie.getGenre());
            statement.setString(6,movie.getHasTeaser());
            statement.setInt(7, movie.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public List < Movie > selectAllFavorites() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Movie> movies= new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FAVORITE_BY_ID);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int id = rs.getInt("mv_id");
                String title = rs.getString("mv_title");
                String boxOffice = rs.getString("mv_box_office");
                String active = rs.getString("mv_active");
                String dateOfLaunch = rs.getString("mv_date_of_launch");
                String genre = rs.getString("mv_genre");
                String hasTeaser= rs.getString("mv_teaser");
                movies.add(new Movie(id,title,boxOffice,active,dateOfLaunch,genre,hasTeaser));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return movies;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
