/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

import java.util.List;
import lombok.Builder;
import commoms.enums.Action;

/**
 *
 * @author Xuyen
 */
public class closeTabRequest extends Request{
    List<String> id;
    String sender;
    @Builder
    public closeTabRequest(Action action,List<String> id,String sender) {
        super(action);
        this.id = id;
        this.sender=sender;
    }
    

    public List<String> getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }
    @Override
    public Action getAction() {
        return super.getAction(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
