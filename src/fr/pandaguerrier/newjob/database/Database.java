package fr.pandaguerrier.newjob.database;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.utils.Debug;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import fr.pandaguerrier.newjob.entities.PlayerCache;
import fr.pandaguerrier.newjob.entities.PlayersEntitites;
import fr.pandaguerrier.newjob.interfaces.DatabaseInterface;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Database implements DatabaseInterface {
    ArrayList<JobsAbstract> jobs = ConodiaJobs.getInstance().getJobs().jobs;

    public void connect(ConodiaJobs plugin) {
        File dbFile = new File(plugin.getDataFolder() + "/db/", "database.db");
        Connection connection;
        if (!dbFile.exists()) {
            try {
                dbFile.getParentFile().mkdir();
                dbFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Un problème a encouru en créant le fichier de la bdd !");
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            ConodiaJobs.getInstance().setConnection(connection);
            init(connection);
            initPlayers(connection);
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().severe("Un problème a encouru avec la bdd: " + e);
        }
    }

    public void init(Connection connection) throws SQLException {
        String columns = jobs.stream().map(jobsContract -> jobsContract.getJobName() + "Level INT, " + jobsContract.getJobName() + "Xp DOUBLE").reduce("", (s, s2) -> s + s2 + ", ");
        columns = columns.substring(0, columns.length() - 2);

        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS playersJobs ('player_uuid' VARCHAR(255) PRIMARY KEY NOT NULL, " + columns + " )");
        statement.close();
    }

    public void initPlayers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM playersJobs");
        while (resultSet.next()) {
            UUID uuid = UUID.fromString(resultSet.getString("player_uuid"));
            // JOBS
            int minerLevel = resultSet.getInt("minerLevel");
            double minerXp = resultSet.getDouble("minerXp");

            int constructorLevel = resultSet.getInt("constructorLevel");
            double constructorXp = resultSet.getDouble("constructorXp");

            int lumberjackLevel = resultSet.getInt("lumberjackLevel");
            double lumberjackXp = resultSet.getDouble("lumberjackXp");

            int farmerLevel = resultSet.getInt("farmerLevel");
            double farmerXp = resultSet.getDouble("farmerXp");

            int hunterLevel = resultSet.getInt("hunterLevel");
            double hunterXp = resultSet.getDouble("hunterXp");

            PlayerCache minerStats = new PlayerCache(minerLevel, minerXp);
            PlayerCache constructorStats = new PlayerCache(constructorLevel, constructorXp);
            PlayerCache lumberjackStats = new PlayerCache(lumberjackLevel, lumberjackXp);
            PlayerCache farmerStats = new PlayerCache(farmerLevel, farmerXp);
            PlayerCache hunterStats = new PlayerCache(hunterLevel, hunterXp);

            ConodiaJobs.getInstance().getPlayersJobs().put(uuid, new PlayersEntitites(minerStats, constructorStats, lumberjackStats, farmerStats, hunterStats));
            Debug.info("Player " + uuid + " loaded !");
        }

        resultSet.close();
    }
}
