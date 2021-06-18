/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

import commoms.enums.Action;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Xuyen
 */
@Getter
public class setUsernameRequest extends Request{

    private String username;
    @Builder
    public setUsernameRequest(Action action, String message) {
        super(action);
        this.username = message;
    }

    @Override
    public Action getAction() {
        return super.getAction(); //To change body of generated methods, choose Tools | Templates.
    }

    public String getUsername() {
        return username;
    }

    
   
    
}
