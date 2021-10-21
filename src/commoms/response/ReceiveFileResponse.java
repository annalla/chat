/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import commoms.enums.Statuscode;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Xuyen
 */
@Getter
public class ReceiveFileResponse extends Response {

    private String fileName;
    private byte[] content;
    private String senderId;
    private List<String> groupList;

    @Builder
    public ReceiveFileResponse(String fileName, List<String> groupList, byte[] content, Statuscode statusCode, String senderId) {
        this.fileName = fileName;
//        this.content=new byte[content.length];
//         int j=0;
//        for (byte i:content){
//            this.content[j]=i;
//            j++;
//        }
        this.content = content;
        this.groupList = groupList;
        this.senderId = senderId;
        this.statusCode = statusCode;
    }

}
