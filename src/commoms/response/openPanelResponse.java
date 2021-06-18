/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import commoms.enums.Statuscode;
import java.util.List;
import lombok.Builder;

/**
 *
 * @author Xuyen
 */
public class openPanelResponse extends Response {

    List<String> senderId;

    public openPanelResponse() {
    }

    @Builder
    public openPanelResponse(List<String> senderId, Statuscode statusCode) {
        this.senderId = senderId;
        this.statusCode = statusCode;
    }

    public List<String> getSenderId() {
        return senderId;
    }

    
}
