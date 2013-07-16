import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import com.amazon.associates.sample.SignedRequestsHelper;

public class Main {
	public static void main(String[] args) throws InvalidKeyException,
			UnsupportedEncodingException, NoSuchAlgorithmException {
		SignedRequestsHelper helper = new SignedRequestsHelper();
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemSearch");
		params.put("Brand", "Nike");
		params.put("SearchIndex", "Shoes");
		params.put("AssociateTag", "dimlib-21");
		System.out.println(helper.sign(params));
	}
}
