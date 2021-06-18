/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import java.util.List;
import commoms.enums.Statuscode;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Xuyen
 */
@Getter
public class closeTabResponse extends Response{
    List<String> id;
    @Builder
    public closeTabResponse(List<String> id,Statuscode s) {
        this.id = id;
        this.statusCode=s;
    }

    public List<String> getId() {
        return id;
    }
    
    
}
