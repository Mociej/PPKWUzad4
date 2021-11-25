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
}
