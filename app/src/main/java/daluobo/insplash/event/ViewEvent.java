package daluobo.insplash.event;

import daluobo.insplash.model.OptionItem;

/**
 * Created by daluobo on 2017/12/27.
 */

public class ViewEvent {
    public OptionItem mOptionItem;

    public ViewEvent(OptionItem optionItem) {
        this.mOptionItem = optionItem;
    }
}
