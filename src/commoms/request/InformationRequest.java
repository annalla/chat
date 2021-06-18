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
public class InformationRequest extends Request {
  
   @Builder
    public InformationRequest( Action action) {
        super(action);
    }

    @Override
    public Action getAction() {
        return super.getAction(); //To change body of generated methods, choose Tools | Templates.
    }

  
    
}