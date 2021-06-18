/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

import commoms.enums.Action;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class MessageRequest extends Request {
    private String message;
    private String uid;
    @Builder
    public MessageRequest(Action action, String message, String uid) {
        super(action);
        this.message = message;
        this.uid = uid;
    }

    @Override
    public Action getAction() {
        return super.getAction(); //To change body of generated methods, choose Tools | Templates.
    }

    public String getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }
    
}
