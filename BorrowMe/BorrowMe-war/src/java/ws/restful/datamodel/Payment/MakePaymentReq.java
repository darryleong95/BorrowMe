/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Payment;//suspense, cliff hanger,

import entity.PaymentEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "makePaymentReq", propOrder = {
    "payment"
})
public class MakePaymentReq {

    private PaymentEntity payment;

    public MakePaymentReq() {

    }

    public MakePaymentReq(PaymentEntity payment) {
        this.payment = payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public PaymentEntity getPayment() {
        return this.payment;
    }

}
