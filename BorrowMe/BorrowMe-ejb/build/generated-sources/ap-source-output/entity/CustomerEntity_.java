package entity;

import entity.FeedbackEntity;
import entity.ItemEntity;
import entity.RequestEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.CustomerTypeEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-24T07:06:28")
@StaticMetamodel(CustomerEntity.class)
public class CustomerEntity_ { 

    public static volatile SingularAttribute<CustomerEntity, String> lastName;
    public static volatile SingularAttribute<CustomerEntity, String> firstName;
    public static volatile SingularAttribute<CustomerEntity, String> password;
    public static volatile SingularAttribute<CustomerEntity, CustomerTypeEnum> customerType;
    public static volatile ListAttribute<CustomerEntity, RequestEntity> requestList;
    public static volatile SingularAttribute<CustomerEntity, Long> customerId;
    public static volatile SingularAttribute<CustomerEntity, String> identificationNo;
    public static volatile ListAttribute<CustomerEntity, ItemEntity> itemList;
    public static volatile ListAttribute<CustomerEntity, FeedbackEntity> feedbackList;
    public static volatile SingularAttribute<CustomerEntity, String> email;
    public static volatile SingularAttribute<CustomerEntity, String> username;
    public static volatile SingularAttribute<CustomerEntity, String> contactNo;

}