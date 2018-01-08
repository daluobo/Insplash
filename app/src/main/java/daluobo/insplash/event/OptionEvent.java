package daluobo.insplash.event;

import daluobo.insplash.model.OptionItem;

/**
 * Created by daluobo on 2017/12/27.
 */

public class OptionEvent {
    public OptionItem mOptionItem;

    public OptionEvent(OptionItem optionItem) {
        this.mOptionItem = optionItem;
    }
}
