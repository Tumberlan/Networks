import Controller.SearchButtonController;
import Model.AppLogic;
import View.New.FirstFrame;

public class App {


    public void start() {
        AppLogic appLogic = new AppLogic();

        FirstFrame frame = new FirstFrame();

        SearchButtonController searchButtonController = new SearchButtonController(frame, appLogic);
    }
}
