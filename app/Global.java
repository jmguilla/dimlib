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
				@SuppressWarnings("unchecked")
				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
						.load("initial-data.yml");
				// The shoes dimensions
				Ebean.save(all.get("producttypes"));
				// All sizes that will be injected in shoes dimensions
				Ebean.save(all.get("sizes"));
				for (Object size : all.get("sizes")) {
					Ebean.saveAssociation(size, "productType");
				}
				// me... the only user so far :'(
				Ebean.save(all.get("users"));
				for(Object user: all.get("users")){
					Ebean.saveAssociation(user, "measures");
				}

				// Insert brands first
				Ebean.save(all.get("brands"));
				// Insert shoes
				Ebean.save(all.get("items"));
				for (Object item : all.get("items")) {
					// Insert the shoe/brand relation
					Ebean.saveAssociation(item, "brand");
					Ebean.saveAssociation(item, "productTypes");
				}

				// Contributions!!
				// Odds are high that I am the only one :D
				Ebean.save(all.get("contributions"));
				for (Object contrib : all.get("contributions")) {
					Ebean.saveAssociation(contrib, "user");
					Ebean.saveAssociation(contrib, "size");
					Ebean.saveAssociation(contrib, "item");
				}
			}
		}
	}
}
