package entity;

import entity.CustomerEntity;
import entity.ItemEntity;
import entity.PaymentEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-24T07:06:28")
@StaticMetamodel(RequestEntity.class)
public class RequestEntity_ { 

    public static volatile SingularAttribute<RequestEntity, CustomerEntity> customerEntity;
    public static volatile SingularAttribute<RequestEntity, Boolean> overdue;
    public static volatile SingularAttribute<RequestEntity, ItemEntity> itemEntity;
    public static volatile SingularAttribute<RequestEntity, Date> endDate;
    public static volatile SingularAttribute<RequestEntity, Long> requestId;
    public static volatile SingularAttribute<RequestEntity, Boolean> accepted;
    public static volatile SingularAttribute<RequestEntity, Boolean> payment;
    public static volatile SingularAttribute<RequestEntity, Date> startDate;
    public static volatile SingularAttribute<RequestEntity, Integer> noOfDays;
    public static volatile SingularAttribute<RequestEntity, PaymentEntity> paymentEntity;

}