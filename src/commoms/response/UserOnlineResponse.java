/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import commoms.enums.Statuscode;
import java.util.Map;

/**
 *
 * @author Xuyen
 */
public class UserOnlineResponse extends Response {
    //private Map<String,String> userIds;
    private List<String> idsList;
    private List<String> usernames;

    @Builder
    public UserOnlineResponse(List<String> idsList,List<String> usernames, Statuscode statusCode) {
        this.idsList=idsList;
        this.usernames=usernames;
        this.statusCode = statusCode;
    }

    public List<String> getIdsList() {
        return idsList;
    }

    public List<String> getUsernames() {
        return usernames;
    }
}
