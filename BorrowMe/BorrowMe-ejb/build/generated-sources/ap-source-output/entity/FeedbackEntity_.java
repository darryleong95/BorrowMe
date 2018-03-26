package entity;

import entity.CustomerEntity;
import entity.ItemEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-23T22:35:37")
@StaticMetamodel(FeedbackEntity.class)
public class FeedbackEntity_ { 

    public static volatile SingularAttribute<FeedbackEntity, CustomerEntity> customerEntity;
    public static volatile SingularAttribute<FeedbackEntity, Integer> customerRating;
    public static volatile SingularAttribute<FeedbackEntity, ItemEntity> itemEntity;
    public static volatile SingularAttribute<FeedbackEntity, String> review;
    public static volatile SingularAttribute<FeedbackEntity, Long> feedbackId;
    public static volatile SingularAttribute<FeedbackEntity, Integer> itemRating;

}