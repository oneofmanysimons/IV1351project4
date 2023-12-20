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
  private static final String student = "student_not_available";
  private PreparedStatement listAllInstruments;
  private PreparedStatement rentInstrument;
  private PreparedStatement deleteRent;
  private PreparedStatement rentedInstruments;
  private PreparedStatement terminateRent;
  private PreparedStatement showTerminate;
  private PreparedStatement showStudentId;
  private PreparedStatement giveRentInfo;
  private PreparedStatement studentAvailable;
  private PreparedStatement deletInstrument;
  private Connection connection;

  private void accessDB() {
    try {
      connection = createConnection();
      prepareStatements(connection);
      connection.setAutoCommit(false);
      Scanner scan = new Scanner(System.in);
      String x = "";
      //list all possible instrument to rent
      System.out.println("list of instruments to rent insert 1: ");
      System.out.println("list of all rented instruments insert 2: ");
      System.out.println("list of all terminated rented instruments insert 3: ");
      System.out.println("list of all available student id for rents insert 4: ");
      System.out.println("rent insert r: ");
      System.out.println("terminate a rent then insert t: ");
      System.out.println("quit then insert q: ");
      x = scan.nextLine();
      //List of all instruments to rent
      if (x.equals("1")) {
        setListAllInstruments();
      }
      //List of all rented instruments
      if (x.equals("2")){
        setListAllRentedInstruments();
      }
      //List of all terminated rents
      if (x.equals("3")){
        setListAllTerminatedRents();
      }
      //List of all available student id
      if (x.equals("4")){
        setListAvailableStudentId();
      }
      //Renting instruments
      if (x.equals("r")) {
        setListAllInstruments();
        setListAvailableStudentId();
        setRent_instrument();
      }
      //Terminating rent
      if (x.equals("t")){
        setListAllRentedInstruments();
        setDeleteRent();
      }
      if (!x.equals("q")) {
        accessDB();
      }
    } catch (SQLException | ClassNotFoundException exc) {
      exc.printStackTrace();
    }
  }

  private Connection createConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/project4.6",
            "postgres", "sco");
  }

  private void setListAllInstruments() throws SQLException{
    connection.setAutoCommit(false);
    try{
      Scanner scan = new Scanner(System.in);
      System.out.println("Name of instrument insert: Guitar for example");
      String name = scan.nextLine();
      listAllInstruments.setString(1,name);
      ResultSet instruments = listAllInstruments.executeQuery();
      
      System.out.println("\nAll instrument possible to rent:");
      while (instruments.next()) {
        System.out.println("instrument_id: " + instruments.getInt(1) +
                " instrument_name: " + instruments.getString(2) +
                " brand: " + instruments.getString(3) +
                " price: " + instruments.getInt(4));
      }
      System.out.println();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }
  private void setListAvailableStudentId() throws SQLException{
    try{
      showStudentId.execute();
      ResultSet students = studentAvailable.executeQuery();
      System.out.println("List of all student_id that are available: ");
      while (students.next()){
        System.out.println("student_id: " + students.getInt(1) +
                " name: " + students.getString(2) +
                " email: " + students.getString(3) +
                " phone: " + students.getInt(4) );
      }
    }catch (SQLException ex){
      ex.printStackTrace();
    }
  }

  private void setRent_instrument() throws SQLException{
    connection.setAutoCommit(false);
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
      rentInstrument.setInt(6, price());
      rentInstrument.executeUpdate();
      connection.commit();

      System.out.println();
    }catch (SQLException ex){
      ex.printStackTrace();
      connection.rollback();
    }
  }
  private int price()throws SQLException{
    ResultSet list = listAllInstruments.executeQuery();
    int give = 0;
    while (list.next()){
      give = list.getInt(4);
    }
    return give;
  }

  private void setDeleteRent() throws SQLException{
    try {
      Scanner scan = new Scanner(System.in);
      System.out.println("Insert the instrument_id of the rented instrument to terminate: ");
      int instrument_id = scan.nextInt();
      insertTerminate(instrument_id);
      deleteRent.setInt(1, instrument_id);
      deleteRent.executeUpdate();
      connection.commit();
    }catch (SQLException ex){
      ex.printStackTrace();
      connection.rollback();
    }
  }

  private void insertTerminate(int x) throws SQLException{
    try {
      connection.setAutoCommit(false);
      giveRentInfo.setInt(1, x);
      ResultSet rent = giveRentInfo.executeQuery();

      while (rent.next()) {
        terminateRent.setInt(1, rent.getInt(1));
        terminateRent.setInt(2, rent.getInt(2));
        terminateRent.setString(3, rent.getString(3));
        terminateRent.setString(4, rent.getString(4));
        terminateRent.setString(5, rent.getString(5));
        terminateRent.setInt(6, rent.getInt(6));
      }
      Calendar cal = Calendar.getInstance();
      terminateRent.setString(7, cal.getTime().toString());
      terminateRent.executeUpdate();
      connection.commit();

      setListAllTerminatedRents();
    }catch (SQLException ex){
      ex.printStackTrace();
      connection.rollback();
    }
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
                " student_id: " + resultSet.getInt(2) +
                " date_rent: " + resultSet.getString(3) +
                " date_return: " + resultSet.getString(4) +
                " date_must_return: " + resultSet.getString(5) +
                " price: " + resultSet.getInt(6) +
                " date terminated: " + resultSet.getString(7));
      }
      System.out.println();
    }catch (SQLException sql){
      sql.printStackTrace();
    }
  }

  private void prepareStatements(Connection connection) throws SQLException {
    listAllInstruments = connection.prepareStatement("SELECT * from " + instrument + " AS a LEFT JOIN " +
            rent_instrument + " AS b ON a.instrument_id = b.instrument_id WHERE b.instrument_id IS NULL" +
            " AND instrument_name = ?" +
            " ORDER BY a.instrument_id");
    rentInstrument = connection.prepareStatement( "INSERT INTO " + rent_instrument +
            " VALUES (?, ?, ?, ?, ?, ?)");
    deleteRent = connection.prepareStatement("DELETE FROM " + rent_instrument +
            " WHERE instrument_id = ?");
    rentedInstruments = connection.prepareStatement("SELECT * FROM " + rent_instrument +
            " ORDER BY instrument_id ");
    terminateRent = connection.prepareStatement("INSERT INTO " + terminate + " VALUES (?,?,?,?,?,?,?)");
    showTerminate = connection.prepareStatement("SELECT * FROM " + terminate +
            " ORDER BY instrument_id");
    studentAvailable = connection.prepareStatement("SELECT * FROM student as a LEFT JOIN " +
            student + " AS b ON a.student_id = b.student_id WHERE b.student_id IS NULL" +
            " ORDER BY a.student_id");
    showStudentId = connection.prepareStatement("DROP MATERIALIZED VIEW IF EXISTS " +
            student + " ; \n" +
            "CREATE MATERIALIZED VIEW " + student +
            " AS SELECT student_id, COUNT(student_id) FROM " +
            "rent_instrument GROUP BY student_id HAVING COUNT(student_id) > 1;");
    giveRentInfo = connection.prepareStatement("SELECT * FROM " + rent_instrument +
            " WHERE instrument_id = ?");
  }

  public static void main(String[] args){
        BasicJdbc test = new BasicJdbc();
        test.accessDB();
  }
}