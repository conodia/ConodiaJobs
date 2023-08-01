package fr.pandaguerrier.newjob.database;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.jobs.*;
import fr.pandaguerrier.newjob.entities.PlayerCache;
import fr.pandaguerrier.newjob.entities.PlayersEntitites;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class JobsDatabase {
    Connection connection = ConodiaJobs.getInstance().getConnection();
    HashMap<UUID, PlayersEntitites> playersJobs = ConodiaJobs.getInstance().getPlayersJobs();

    public void saveAsync() {
        ConodiaJobs.getInstance().getServer().getScheduler().runTaskAsynchronously(ConodiaJobs.getInstance(), this::save);
    }

    public void save() {
        try {
            saveFunction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveEvery(int ticks) {
        ConodiaJobs.getInstance().getServer().getScheduler().runTaskTimer(ConodiaJobs.getInstance(), this::saveAsync, ticks, ticks);
    }

    private void saveFunction() throws SQLException {
        for (UUID uuid : playersJobs.keySet()) {
            PlayersEntitites playersEntitites = playersJobs.get(uuid);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM playersJobs WHERE player_uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet st = statement.executeQuery();

           // Debug.info("Saving " + uuid + " with " + playersEntitites.getPlayerStats().size() + " jobs");
            if (st.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE playersJobs SET minerLevel = ?, minerXp = ?, constructorLevel = ?, constructorXp = ?, lumberjackLevel = ?, lumberjackXp = ?, farmerLevel = ?, farmerXp = ?, hunterLevel = ?, hunterXp = ? WHERE player_uuid = ?");

                PlayerCache jobStats = playersEntitites.getPlayerStats().get(new Miner().getJobName());
                preparedStatement.setInt(1, jobStats.getLevel());
                preparedStatement.setDouble(2, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Constructor().getJobName());
                preparedStatement.setInt(3, jobStats.getLevel());
                preparedStatement.setDouble(4, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Lumberjack().getJobName());
                preparedStatement.setInt(5, jobStats.getLevel());
                preparedStatement.setDouble(6, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Farmer().getJobName());
                preparedStatement.setInt(7, jobStats.getLevel());
                preparedStatement.setDouble(8, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Hunter().getJobName());
                preparedStatement.setInt(9, jobStats.getLevel());
                preparedStatement.setDouble(10, jobStats.getXp());

                preparedStatement.setString(11, uuid.toString());
                preparedStatement.executeUpdate();

                preparedStatement.close();
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playersJobs (minerLevel, minerXp, constructorLevel, constructorXp, lumberjackLevel, lumberjackXp, farmerLevel, farmerXp, hunterLevel, hunterXp, player_uuid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                PlayerCache jobStats = playersEntitites.getPlayerStats().get(new Miner().getJobName());
                preparedStatement.setInt(1, jobStats.getLevel());
                preparedStatement.setDouble(2, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Constructor().getJobName());
                preparedStatement.setInt(3, jobStats.getLevel());
                preparedStatement.setDouble(4, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Lumberjack().getJobName());
                preparedStatement.setInt(5, jobStats.getLevel());
                preparedStatement.setDouble(6, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Farmer().getJobName());
                preparedStatement.setInt(7, jobStats.getLevel());
                preparedStatement.setDouble(8, jobStats.getXp());

                jobStats = playersEntitites.getPlayerStats().get(new Hunter().getJobName());
                preparedStatement.setInt(9, jobStats.getLevel());
                preparedStatement.setDouble(10, jobStats.getXp());

                preparedStatement.setString(11, uuid.toString());
                preparedStatement.executeUpdate();

                preparedStatement.close();
            }
        }

        System.out.println("Jobs saved !");
    }
}
