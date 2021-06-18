/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.request;

import lombok.Getter;
import lombok.Builder;
import commoms.enums.Action;

/**
 *
 * @author Xuyen
 */
@Getter
public class GetIDRequest extends Request {

    @Builder
    public GetIDRequest(Action action) {
        super(action);
    }
}
