/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

import java.io.Serializable;
import commoms.enums.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Xuyen
 */
public abstract class Request implements Serializable {
    @NonNull
    protected Action action;

    public Request(Action action) {
        this.action = action;
    }
    
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    
}
