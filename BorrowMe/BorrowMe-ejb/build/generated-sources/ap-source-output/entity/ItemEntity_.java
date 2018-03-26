package entity;

import entity.CustomerEntity;
import entity.FeedbackEntity;
import entity.RequestEntity;
import java.util.Locale.Category;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-24T07:06:28")
@StaticMetamodel(ItemEntity.class)
public class ItemEntity_ { 

    public static volatile SingularAttribute<ItemEntity, Long> itemId;
    public static volatile SingularAttribute<ItemEntity, CustomerEntity> customerEntity;
    public static volatile ListAttribute<ItemEntity, RequestEntity> requestList;
    public static volatile SingularAttribute<ItemEntity, String> itemTitle;
    public static volatile SingularAttribute<ItemEntity, Double> costPerDay;
    public static volatile SingularAttribute<ItemEntity, Boolean> itemAvailable;
    public static volatile SingularAttribute<ItemEntity, String> itemDescription;
    public static volatile SingularAttribute<ItemEntity, Category> category;
    public static volatile ListAttribute<ItemEntity, FeedbackEntity> feedbackList;

}