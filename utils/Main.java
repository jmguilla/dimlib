import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.amazon.associates.sample.SignedRequestsHelper;

public class Main {
	private static final int MAX_PRICE = 300;
	private static final int PACE = 10;

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, FileNotFoundException, IOException {
		itemSearch();
	}

	private static void itemLookup() throws MalformedURLException, IOException,
			InvalidKeyException, NoSuchAlgorithmException {
		SignedRequestsHelper helper = new SignedRequestsHelper();
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("AssociateTag", "dimlib-21");
		params.put("ItemId", "B006QFOPI6");
		System.out.println(helper.sign(params));
	}

	private static void itemSearch() throws MalformedURLException, IOException,
			InvalidKeyException, NoSuchAlgorithmException {
		SignedRequestsHelper helper = new SignedRequestsHelper();
		ArrayList<String> brands = findBrands();
		for (String brand : brands) {
			for (int minPrice = 10; minPrice <= MAX_PRICE; minPrice = minPrice
					+ PACE) {
				int maxPrice = minPrice + PACE;
				for (int i = 1; i <= 10; i++) {
					Hashtable<String, String> params = new Hashtable<String, String>();
					params.put("Service", "AWSECommerceService");
					params.put("Operation", "ItemSearch");
					params.put("Brand", brand);
					params.put("SearchIndex", "Shoes");
					params.put("MinimumPrice", Integer.toString(minPrice));
					if (minPrice < MAX_PRICE) {
						params.put("MaximumPrice", Integer.toString(maxPrice));
					}
					// browse node for shoes in FR:
					// http://docs.aws.amazon.com/AWSECommerceService/latest/DG/BrowseNodeIDs.html
					// params.put("BrowseNode", Integer.toString(215934031));
					params.put("AssociateTag", "dimlib-21");
					params.put("ItemPage", Integer.toString(i));
					wget(new URL(helper.sign(params)),
							new File("res" + File.separator + "AmazonItems",
									String.format("%s_%03d_%03d_%02d.xml",
											brand, minPrice, maxPrice, i)));
				}
			}
		}
	}

	private static ArrayList<String> findBrands() throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("res/brands.txt")));
			String line = null;
			while ((line = br.readLine()) != null) {
				result.add(line.trim());
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return result;
	}

	public static void wget(URL url, File target) throws IOException {
		String s;
		BufferedReader br = null;
		PrintStream ps = null;
		try {
			ps = new PrintStream(target);
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((s = br.readLine()) != null) {
				ps.println(s);
			}
		} finally {
			if (br != null) {
				br.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
	}
}
