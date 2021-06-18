/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

/**
 *
 * @author Xuyen
 */
import commoms.enums.Action;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class GroupMessageRequest extends Request {
    private String message;
    private List<String> uids;
    private List<String> groupList;
    @Builder
    public GroupMessageRequest(@NonNull Action action, String message, List<String> uids,List groupList) {
        super(action);
        this.uids = uids;
        this.message = message;
        this.groupList=groupList;
    }
}

