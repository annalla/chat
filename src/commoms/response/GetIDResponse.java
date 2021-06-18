/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import lombok.Getter;
import lombok.Builder;
import commoms.enums.Statuscode;

/**
 *
 * @author Xuyen
 */
@Getter
public class GetIDResponse extends Response{
    private String myid;

    
    @Builder
    public GetIDResponse(String myid, Statuscode statusCode) {
        this.myid= myid;
        this.statusCode = statusCode;
    }
}
