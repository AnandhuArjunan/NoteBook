package com.anandhuarjunan.notesfx.helper;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.anandhuarjunan.notesfx.entities.Note;
import com.anandhuarjunan.notesfx.entities.NoteBook;

public class DatabaseController {
    private static final Logger LOGGER = Logger.getLogger(DatabaseController.class.getName());

    private static Connection connection = null;
    private static final String driver = "org.sqlite.JDBC";
    private static final String URL = "jdbc:sqlite:notes.db";
    private static final String USERS_TABLE_NAME = "NOTEBOOK";
    private static final String NOTES_TABLE_NAME = "NOTES";

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            LOGGER.info("Database connection is null, creating connection...");
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Failed to connect to database");
            }

            if (!databaseHasTables()) {
                LOGGER.info("Database is empty, creating tables...");
                createTables();
                registerNoteBook("General");
            }
        }
        return connection;
    }

    private static boolean databaseHasTables() {
        var tableUsersExist = false;
        var tableNotesExist = false;
        try {
            var rs = getConnection().getMetaData().getTables(null, null, USERS_TABLE_NAME, null);
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(USERS_TABLE_NAME)) {
                    LOGGER.info("Table NOTEBOOK exist");
                    tableUsersExist = true;
                    break;
                }
            }
            rs = getConnection().getMetaData().getTables(null, null, NOTES_TABLE_NAME, null);
            while (rs.next()) {
                var tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(NOTES_TABLE_NAME)) {
                    LOGGER.info("Table NOTES exist");
                    tableNotesExist = true;
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to check if tables exist");
            e.printStackTrace();
        }
        return (tableNotesExist && tableUsersExist);
    }

    private static void createTables() throws SQLException {
        var usersTable = "CREATE TABLE IF NOT EXISTS NOTEBOOK (\n" + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " NAME VARCHAR(256) NOT NULL)";
        var usersTableStatement = getConnection().createStatement();
        usersTableStatement.execute(usersTable);
        LOGGER.info("Created Notebook table.");

        var notesTable = "CREATE TABLE IF NOT EXISTS NOTES (\n" + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
        		  + " TITLE TEXT," +  " NOTE TEXT," + " NOTEBOOKID INTEGER NOT NULL," + " WRITEDATE DATE NOT NULL,"
                + " FOREIGN KEY(NOTEBOOKID) REFERENCES USERS(ID))";

        var notesTableStatement = getConnection().createStatement();
        notesTableStatement.execute(notesTable);
        LOGGER.info("Created notes table.");
    }

    public static void registerNoteBook(String name) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO NOTEBOOK(NAME) VALUES(?)");
        statement.setString(1, name);

        statement.execute();
    }

    public static void insertNote(Note note) throws SQLException {
        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO NOTES(TITLE ,NOTE, NOTEBOOKID, WRITEDATE) VALUES(?,?,?,?)");
        statement.setString(1, note.getTitle());
        statement.setString(2, note.getNote());
        
        statement.setInt(3, note.getNotebook().getId());
        // Convert java.util.date to java.sql.date
        var convertedDate = new Date(note.getWriteDate().getTime());
        statement.setDate(4, convertedDate);

        statement.execute();
    }

    public static void deleteNote(Note note) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM NOTES WHERE ID = ?");
        if (note.getId() != 0) {
            statement.setInt(1, note.getId());
            statement.execute();
        } else {
            LOGGER.severe("Note id is invalid (0), failed to insert into database.");
        }
    }
    
    public static void deleteNoteBook(NoteBook noteBook) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM NOTEBOOK WHERE ID = ?");
        if (noteBook.getId() != 0) {
            statement.setInt(1, noteBook.getId());
            statement.execute();
        } else {
            LOGGER.severe("Note id is invalid (0), failed to insert into database.");
        }
    }
    
    public static void renameNoteBook(NoteBook noteBook) throws SQLException {
    	 PreparedStatement statement = getConnection().prepareStatement("UPDATE NOTEBOOK SET NAME = ? WHERE ID = ?");
         statement.setString(1, noteBook.getName());
         statement.setInt(2, noteBook.getId());
         statement.execute();
    }

    public static void updateNote(Note note) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("UPDATE NOTES SET NOTE = ? WHERE ID = ?");
        statement.setString(1, note.getNote());
        statement.setInt(2, note.getId());

        statement.execute();
    }

    public static int getNextNoteValidID() throws SQLException {
        PreparedStatement statement = getConnection()
                .prepareStatement("SELECT seq from sqlite_sequence WHERE name = 'NOTES'");
        ResultSet queryResult = statement.executeQuery();
        if (!queryResult.next()) {
            LOGGER.info("Current max key is not set up yet");
            return 1;
        } else {
            return queryResult.getInt("seq") + 1;
        }
    }

    public static List<Note> getNotesFromNoteBook(int noteBookId) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM NOTES WHERE NOTEBOOKID = ? ORDER BY WRITEDATE");
        statement.setInt(1, noteBookId);
        var foundNotes = statement.executeQuery();

        List<Note> noteList = new ArrayList<>();
        while (foundNotes.next()) {
            var resultID = foundNotes.getInt("ID");
            var title = foundNotes.getString("TITLE");
            var resultNote = foundNotes.getString("NOTE");
            var notebookId = foundNotes.getInt("NOTEBOOKID");
            java.util.Date resultDate = foundNotes.getDate("WRITEDATE");
            noteList.add(new Note(resultID ,title, resultNote, getNoteBook(notebookId), resultDate));
        }
        if (!noteList.isEmpty()) {
            return noteList;
        } else {
            throw new SQLException("No notes found for notebook: " + noteBookId);
        }

    }

    
    public static List<NoteBook> getNoteBooks() throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM NOTEBOOK ");
        var foundNotes = statement.executeQuery();

        List<NoteBook> notebookList = new ArrayList<>();
        while (foundNotes.next()) {
            var resultID = foundNotes.getInt("ID");
            var resultName = foundNotes.getString("NAME");
            notebookList.add(new NoteBook(resultID, resultName));
        }
            return notebookList;

    }
    
    public static NoteBook getNoteBook(int notebookId) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM NOTEBOOK WHERE ID = ?");
        statement.setInt(1, notebookId);
        var foundUsers = statement.executeQuery();
        // From the first, and only expected result, build a User instance.
        var resultName = foundUsers.getString("NAME");
        var resultId = foundUsers.getInt("ID");
        
        return new NoteBook(resultId, resultName);
    }

}