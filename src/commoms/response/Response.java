/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commoms.response;

import java.io.Serializable;
import commoms.enums.Statuscode;
import lombok.Getter;

/**
 *
 * @author Xuyen
 */
@Getter
public abstract class Response implements Serializable {
    protected Statuscode statusCode;
}