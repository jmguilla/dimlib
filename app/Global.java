import java.util.List;
import java.util.Map;

import models.Item;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {
  @Override
  public void onStart(Application app) {
    InitialData.insert(app);
  }

  static class InitialData {

    public static void insert(Application app) {
      if (Ebean.find(Item.class).findRowCount() == 0) {
        Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml
            .load("initial-data.yml");
        // Insert brands first
        Ebean.save(all.get("brands"));
        // Insert shoes
        Ebean.save(all.get("items"));
        for (Object shoe : all.get("items")) {
          // Insert the shoe/brand relation
          Ebean.saveAssociation(shoe, "brand");
        }

      }
    }
  }
}
