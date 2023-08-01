package fr.pandaguerrier.newjob;

import fr.pandaguerrier.newjob.commands.JobsCommand;
import fr.pandaguerrier.newjob.listeners.InventoryListener;
import fr.pandaguerrier.newjob.managers.JobsManager;
import fr.pandaguerrier.newjob.database.Database;
import fr.pandaguerrier.newjob.database.JobsDatabase;
import fr.pandaguerrier.newjob.entities.ConfigEntities;
import fr.pandaguerrier.newjob.entities.PlayersEntitites;
import fr.pandaguerrier.newjob.listeners.JobsListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ConodiaJobs extends JavaPlugin {
    private static ConodiaJobs instance;
    private Connection connection;
    private final HashMap<UUID, PlayersEntitites> playersJobs = new HashMap<>();
    private JobsManager jobsManager;
    public void onEnable() {
        instance = this;

        // loader
        jobsManager = new JobsManager();

        // database
        new Database().connect(this);

        // save jobs every 5 minutes
        new JobsDatabase().saveEvery(5 * 60 * 20);

        // other
        loadCommands();
        loadEvents();
        System.out.println("\n \n-------------------------\n \nLe new ConodiaJobs est connecté !\n \n-------------------------\n \n");
    }

    private void loadCommands() {
        this.getCommand("jobs").setExecutor(new JobsCommand());
    }

    private void loadEvents() {
        //    this.getServer().getPluginManager().registerEvents(new InventoryClick(),  this);
        this.getServer().getPluginManager().registerEvents(new JobsListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    public void onDisable() {
        new JobsDatabase().save();
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n \n-------------------------\n \nLe ConodiaJobs est déconnecté !\n \n-------------------------\n \n");
    }

    /**
     * Getters
     */

    public static ConodiaJobs getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public HashMap<UUID, PlayersEntitites> getPlayersJobs() {
        return playersJobs;
    }

    public JobsManager getJobs() {
        return jobsManager;
    }
}