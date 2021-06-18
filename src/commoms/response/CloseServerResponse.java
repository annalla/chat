/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import commoms.enums.Statuscode;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Xuyen
 */
@Getter
public class CloseServerResponse extends Response{
    private String senderId;


    public String getSenderId() {
        return senderId;
    }

    
    @Builder
    public CloseServerResponse(String senderId, Statuscode statusCode) {
        this.senderId = senderId;
        this.statusCode = statusCode;
    }
    
}
