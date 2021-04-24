import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.google.gson.Gson;

/**
 * NOT FOR CONCURENT USE
*/
@SuppressWarnings("unchecked")
public class FlattenJson{

	Gson gson=new Gson();
	Map<String, String> flatmap = new HashMap<String, String>();


	public Map<String, String> parse(String value) {        
	    iterableCrawl("", null, (gson.fromJson(value, flatmap.getClass())).entrySet());     
	    return flatmap; 
	}

	private <T> void iterableCrawl(String prefix, String suffix, Iterable<T> iterable) {
	    int key = 0;
	    for (T t : iterable) {
	        if (suffix!=null)
	            crawl(t, prefix+(key++)+suffix);
	        else
	            crawl(((Entry<String, Object>) t).getValue(), prefix+((Entry<String, Object>) t).getKey());
	    }
	}

	private void crawl(Object object, String key) {
	    if (object instanceof ArrayList)
	        iterableCrawl(key+"[", "]", (ArrayList<Object>)object);
	    else if (object instanceof Map)
	        iterableCrawl(key+".", null, ((Map<String, Object>)object).entrySet());
	    else
	        flatmap.put(key, object.toString());
	}

	public static void main(String[] args)
	{
	    String json1 = "{\n" +
	        "   \"Port\":\n" +
	        "   {\n" +
	        "       \"@alias\": \"defaultHttp\",\n" +
	        "       \"Enabled\": \"true\",\n" +
	        "       \"Number\": \"10092\",\n" +
	        "       \"Protocol\": \"http\",\n" +
	        "       \"KeepAliveTimeout\": \"20000\",\n" +
	        "       \"ThreadPool\":\n" +
	        "       {\n" +
	        "           \"@enabled\": \"false\",\n" +
	        "           \"Max\": \"150\",\n" +
	        "           \"ThreadPriority\": \"5\"\n" +
	        "       },\n" +
	        "       \"ExtendedProperties\":\n" +
	        "       {\n" +
	        "           \"Property\":\n" +
	        "           [                         \n" +
	        "               {\n" +
	        "                   \"@name\": \"connectionTimeout\",\n" +
	        "                   \"$\": \"20000\"\n" +
	        "               }\n" +
	        "           ]\n" +
	        "       }\n" +
	        "   }\n" +
	        "}";

	    String json2 = "{\n" +
	        "   \"Student\":\n" +
	        "   {\n" +
		        "       \"name\": \"fred\",\n" +
		        "       \"gpa\": \"2.3\",\n" +
		        "       \"moduleGrades\":\n" +
		        "       {\n" +
		        "           \"programming\": \"A\",\n" +
		        "           \"databases\": \"B\"\n" +
		        "       }\n" +
	        "   }\n" +
	        "}";

		FlattenJson app = new FlattenJson();
		Map<String, String> result = app.parse(json2);	
		
		System.out.println(result);
	}	
}