package ppkwuzad4.ppkwuzad4.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ppkwuzad3.ppkwuzad3.Controllers.FileSaverApiController;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ConvertFileTypeApi {
    private static final Pattern TAG_REGEX = Pattern.compile("<string>(.+?)</string>", Pattern.DOTALL);

    @GetMapping("/secondSaveStatsToFile/{filetype}/{input}")
    public String secondSaveStatsToFile(@PathVariable String filetype, @PathVariable String input) {
        FileSaverApiController fileSaverApiController = new FileSaverApiController();
        return fileSaverApiController.saveStatsToFile(filetype, input);
    }

    @GetMapping("/convertTypeOfStats/{newfiletype}/{oldfiletype}/{input}")
    public String convertTypeOfStats(@PathVariable String newfiletype, @PathVariable String oldfiletype, @PathVariable String input) throws JsonProcessingException {
        if (oldfiletype.equals("txt") || oldfiletype.equals("xml") || oldfiletype.equals("json") || oldfiletype.equals("csv")) {
        } else {
            return "wrong input file types";
        }
        if (newfiletype.equals("txt") || newfiletype.equals("xml") || newfiletype.equals("json") || newfiletype.equals("csv")) {
        } else {
            return "wrong input file types";
        }

        String result = "";
        String oldfile = input;
        Map<String, String> elements;

        switch (oldfiletype) {
            case "txt":
                elements = new HashMap();
                String lines[] = oldfile.split("\\r?\\n");

                for (int i = 0; i < lines.length; i++) {
                    String[] parts = lines[i].split(":");
                    elements.put(parts[0], parts[1]);
                }
                break;
            case "json":
                elements = new ObjectMapper().readValue(oldfile, HashMap.class);
                break;
            case "xml":
                elements = new HashMap();
                List<String> tagValues = new ArrayList<String>();
                Matcher matcher = TAG_REGEX.matcher(oldfile);

                while (matcher.find()) {
                    tagValues.add(matcher.group(1));
                }

                String key = "";

                for (int i = 0; i < tagValues.size(); i++) {
                    if (i % 2 == 0)
                        key = tagValues.get(i);
                    else
                        elements.put(key, tagValues.get(i));
                }
                break;
            case "csv":
                String linesCSV[] = oldfile.split("\\r?\\n");
                elements = new HashMap();

                for (int i = 1; i < linesCSV.length; i++) {
                    String arr[] = linesCSV[i].split(",");
                    elements.put(arr[0], arr[1]);
                }
                break;
            default:
                return "File type must be txt, json, xml or csv.";
        }


        switch (newfiletype) {
            case "txt":
                for (Map.Entry<String, String> entry : elements.entrySet()) {
                    result += entry.getKey() + " : " + entry.getValue() + "\n";
                }
                break;
            case "json":
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String json = objectMapper.writeValueAsString(elements);
                    result = json;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case "xml":
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                XMLEncoder xmlEncoder = new XMLEncoder(byteArrayOutputStream);
                xmlEncoder.writeObject(elements);
                xmlEncoder.close();
                result = byteArrayOutputStream.toString();
                break;
            case "csv":
                for (Map.Entry<String, String> entry : elements.entrySet()) {
                    result += entry.getKey() + "," + entry.getValue() + "\n";
                }
                break;
            default:
                return "File type must be txt, json, xml or csv.";
        }
        return result;
    }
}
