/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

import commoms.enums.Action;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author Xuyen
 */
@Getter
public class SendFileRequest extends Request {

    private String fileName;
    private byte[] content;
    private List<String> uids;
    private List<String> groupList;

    @Builder
    public SendFileRequest(@NonNull Action action, String fileName, List<String> uids, List groupList, byte[] content) {
        super(action);
        this.uids = uids;
        this.fileName = fileName;
//        this.content= new byte[content.length];
//        int i=0;
//        for( byte bb:content){
//            this.content[i]=bb;
//            i++;
//        }
        this.content = content;
        this.groupList = groupList;
    }

}
