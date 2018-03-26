package entity;

import entity.RequestEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-24T07:06:28")
@StaticMetamodel(PaymentEntity.class)
public class PaymentEntity_ { 

    public static volatile SingularAttribute<PaymentEntity, Double> totalAmount;
    public static volatile SingularAttribute<PaymentEntity, Long> paymentId;
    public static volatile SingularAttribute<PaymentEntity, RequestEntity> requestEntity;

}