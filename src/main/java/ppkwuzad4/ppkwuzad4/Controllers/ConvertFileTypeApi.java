package ppkwuzad4.ppkwuzad4.Controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ppkwuzad3.ppkwuzad3.Controllers.FileSaverApiController;

@RestController
public class ConvertFileTypeApi {

    @GetMapping("/secondSaveStatsToFile/{filetype}/{input}")
    public String secondSaveStatsToFile(@PathVariable String filetype, @PathVariable String input) {
        FileSaverApiController fileSaverApiController = new FileSaverApiController();
        return fileSaverApiController.saveStatsToFile(filetype,input);
    }

    @GetMapping("/convertTypeOfStats/{newfiletype}/{oldfiletype}/{input}")
    public String convertTypeOfStats(@PathVariable String newfiletype, @PathVariable String oldfiletype, @PathVariable String input) {
        if(!oldfiletype.equals("txt") || !oldfiletype.equals("xml") || !oldfiletype.equals("json") || !oldfiletype.equals("csv")){
            return "wrong input file types";
        }
        if(!newfiletype.equals("txt") || !newfiletype.equals("xml") || !newfiletype.equals("json") || !newfiletype.equals("csv")){
            return "wrong input file types";
        }

        FileSaverApiController fileSaverApiController = new FileSaverApiController();




        String result = fileSaverApiController.saveStatsToFile(oldfiletype,input);
        return result;
    }

}
