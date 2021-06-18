/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import lombok.Builder;
import commoms.enums.Statuscode;
import java.util.List;

/**
 *
 * @author Xuyen
 */
public class MessageResponse extends Response {

    private String message;
    private String senderId;
    private List<String> group;

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public List<String> getGroup() {
        return group;
    }

    @Builder
    public MessageResponse(String message, String senderId, List<String> group, Statuscode statusCode) {
        this.message = message;
        this.senderId = senderId;
        this.group = group;
        this.statusCode = statusCode;
    }
}
