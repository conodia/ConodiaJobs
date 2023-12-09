package fr.pandaguerrier.jobs.managers;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import fr.pandaguerrier.jobs.ConodiaJobs;
import org.json.simple.JSONObject;

public class FLevelManager {
   int id;
   int points;
   double xp;
   final Faction faction;
   boolean isSaved = false;

   public FLevelManager(int id, Faction faction, int points, double xp) {
      this.id = id;
      this.faction = faction;
      this.points = points;
      this.xp = xp;
   }

   public void addPoints(int points, double xp) {
      this.xp -= xp;
      this.points += points;
    }

    public void addXP(double xp) {
      this.xp += xp;
    }

    public void create() {
     JSONObject payload = ConodiaGameAPI.getInstance().getApiManager().post("/levels", this.toJson());

      if (payload == null) {
          return;
      }

      this.id = ((Long) payload.get("id")).intValue();
    }

    public void update() {

      if (isSaved) {
        return;
      }

      ConodiaGameAPI.getInstance().getApiManager().put("/levels/" + this.id, this.toJson());

      isSaved = true;

      ConodiaJobs.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(ConodiaJobs.getInstance(), () -> {
        isSaved = false;
      }, 2400L);
    }

    public JSONObject toJson() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("factionName", faction.getTag());
      jsonObject.put("points", points);
      jsonObject.put("xp", xp);
      return jsonObject;
    }

    public static FLevelManager from(JSONObject payload) {
     Faction faction = Factions.getInstance().getByTag((String) payload.get("faction_name"));

      if (faction == null) {
        ConodiaGameAPI.getInstance().getApiManager().destroy("/levels/" + payload.get("id"), new JSONObject());

        return null;
      }

      return new FLevelManager(((Long) payload.get("id")).intValue(), faction, ((Long) payload.get("points")).intValue(), Double.parseDouble(payload.get("xp").toString()));
    }

    public int getPoints() {
        return points;
    }

    public boolean isSaved() {
      return isSaved;
    }

    public double getXp() {
        return xp;
    }

    public Faction getFaction() {
        return faction;
    }

    public int getId() {
        return id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }
}
