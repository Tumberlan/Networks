import model.AppLogic;
import view.FirstFrame;
import controller.SearchButtonController;

public class App {

    public void start() {
        AppLogic appLogic = new AppLogic();

        FirstFrame frame = new FirstFrame();

        SearchButtonController searchButtonController = new SearchButtonController(frame, appLogic);
    }
}
