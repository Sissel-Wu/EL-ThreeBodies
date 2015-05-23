package control;

import io.UserData;
import dto.PreferenceDTO;

/*
 * Preference
 */
public class PreferenceControl {
    
	PreferenceDTO dto = PreferenceDTO.getInstance();
	
    public void savePreference() {
    	UserData.savePreference(dto);
    }
    
    public void loadPreference() {
    	PreferenceDTO old = UserData.loadPreference();
    	PreferenceDTO.init(old);
    }

}
