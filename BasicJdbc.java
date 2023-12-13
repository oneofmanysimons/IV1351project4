/*
 * The MIT License (MIT)
 * Copyright (c) 2020 Leif Lindbäck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction,including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so,subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package se.kth.iv1351.jdbcintro;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * A small program that illustrates how to write a simple JDBC program.
 */
public class BasicJdbc {
  private static final String instrument = "instrument";
  private static final String rent_instrument = "rent_instrument";
  private static final String terminate = "rent_terminate";
  private static final int price = 300;
  private PreparedStatement listAllInstruments;
  private PreparedStatement rentInstrument;
  private PreparedStatement deleteRent;
  private PreparedStatement rentedInstruments;
  private PreparedStatement terminateRent;
  private PreparedStatement showTerminate;
  private PreparedStatement showStudentId;

  private void accessDB() {
    try (Connection connection = createConnection()) {
      prepareStatements(connection);
      Scanner scan = new Scanner(System.in);
      String x = "";
      //list all possible instrument to rent
      System.out.println("If you want to see which instruments to rent insert v: ");
      System.out.println("If you want to see a list of all rented instruments then insert w: ");
      System.out.println("If you want to see a list of all terminated rents then insert y: ");
      System.out.println("If you want to rent then insert x: ");
      System.out.println("If you want to terminate a rent then insert z: ");
      x = scan.nextLine();
      //List of all instruments to rent
      if (x.equals("v")) {
        setListAllInstruments();
      }
      //List of all rented instruments
      if (x.equals("w")){
        setListAllRentedInstruments();
      }
      //List of all terminated rents
      if (x.equals("y")){
        setListAllTerminatedRents();
      }
      //Renting instruments
      if (x.equals("x")) {
        setListAllInstruments();
        setRent_instrument();
      }
      //Terminating rent
      if (x.equals("z")){
        setListAllRentedInstruments();
        setDeleteRent();
      }
    } catch (SQLException | ClassNotFoundException exc) {
      exc.printStackTrace();
    }
  }

  private Connection createConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/project4.3",
            "postgres", "sco");
  }

  private void setListAllInstruments() throws SQLException{
    try (ResultSet instruments = listAllInstruments.executeQuery()) {
      System.out.println("\nAll instrument possible to rent:");
      while (instruments.next()) {
        System.out.println("instrument_id: " + instruments.getInt(1) +
                " instrument_name: " + instruments.getString(2));
      }
      setListAvailableStudentId();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }
  private void setListAvailableStudentId() throws SQLException{
    try (ResultSet student = showStudentId.executeQuery()){
      System.out.println("\nstudent_id 1-30 are all available except the ones that are on the list below");
      System.out.println("List of all student_id that are not available: ");
      while (student.next()){
        System.out.println("student_id: " + student.getInt(1) +
                ", number of rented instruments: " + student.getInt(2));
      }
    }
  }

  private void setRent_instrument() throws SQLException{
    try {
      Scanner scanner = new Scanner(System.in);
      Integer id = 0;
      String date = "";
      System.out.println("Insert id of instrument: ");
      id = scanner.nextInt();
      rentInstrument.setInt(1, id);
      System.out.println("Insert id of student: ");
      id = scanner.nextInt();
      rentInstrument.setInt(2, id);

      Calendar cal = Calendar.getInstance();
      rentInstrument.setString(3,cal.getTime().toString());
      cal.add(Calendar.MONTH, 1);
      rentInstrument.setString(4,cal.getTime().toString());
      cal.add(Calendar.MONTH, 11);
      rentInstrument.setString(5,cal.getTime().toString());
      rentInstrument.setInt(6, price);
      rentInstrument.executeUpdate();
      System.out.println();
    }catch (SQLException ex){
      ex.printStackTrace();
    }
  }

  private void setDeleteRent() throws SQLException{
    Scanner scan = new Scanner(System.in);
    System.out.println("Insert the instrument_id of the rented instrument to terminate: ");
    int instrument_id = scan.nextInt();
    deleteRent.setInt(1,instrument_id);
    deleteRent.executeUpdate();

    insertTerminate(instrument_id);
  }

  private void insertTerminate(int x) throws SQLException{
    terminateRent.setInt(1, x);
    Calendar cal = Calendar.getInstance();
    terminateRent.setString(2,cal.getTime().toString());
    terminateRent.executeUpdate();

    setListAllTerminatedRents();
  }

  private void setListAllRentedInstruments() throws SQLException{
    try(ResultSet resultSet = rentedInstruments.executeQuery()) {
      System.out.println("All instrument currently rented:");
      while (resultSet.next()) {
        System.out.println("instrument_id: " + resultSet.getInt(1) +
                " student_id: " + resultSet.getInt(2) +
                " date_rent: " + resultSet.getString(3) +
                " date_return: " + resultSet.getString(4) +
                " date_must_return: " + resultSet.getString(5) +
                " price: " + resultSet.getInt(6));
      }
      System.out.println();
    }catch (SQLException sql){
      sql.printStackTrace();
    }
  }

  private void setListAllTerminatedRents() throws SQLException{
    try(ResultSet resultSet = showTerminate.executeQuery()) {
      System.out.println("All Rents that have been terminated:");
      while (resultSet.next()) {
        System.out.println("instrument_id: " + resultSet.getInt(1) +
                " date: " + resultSet.getString(2));
      }
      System.out.println();
    }catch (SQLException sql){
      sql.printStackTrace();
    }
  }

  private void prepareStatements(Connection connection) throws SQLException {
    listAllInstruments = connection.prepareStatement("SELECT * from " + instrument + " AS a LEFT JOIN " +
            rent_instrument + " AS b ON a.instrument_id = b.instrument_id WHERE b.instrument_id IS NULL");
    rentInstrument = connection.prepareStatement("INSERT INTO " + rent_instrument +
            " VALUES (?, ?, ?, ?, ?, ?)");
    deleteRent = connection.prepareStatement("DELETE FROM " + rent_instrument +
            " WHERE instrument_id = ?");
    rentedInstruments = connection.prepareStatement("SELECT * FROM " + rent_instrument);
    terminateRent = connection.prepareStatement("INSERT INTO " + terminate + " VALUES (?,?)");
    showTerminate = connection.prepareStatement("SELECT * FROM rent_terminate");
    showStudentId = connection.prepareStatement("SELECT student_id, COUNT(student_id) FROM " +
            "rent_instrument GROUP BY student_id HAVING COUNT(student_id) > 1");
  }

  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    String name = "y";
    while (name.equals("y")) {
        new BasicJdbc().accessDB();
        System.out.println("wanna start again then insert y/n: ");
        name = scan.nextLine();
    }
  }
}