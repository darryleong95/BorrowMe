package entity;

import entity.CustomerEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-23T15:23:07")
@StaticMetamodel(AddressEntity.class)
public class AddressEntity_ { 

    public static volatile SingularAttribute<AddressEntity, CustomerEntity> customerEntity;
    public static volatile SingularAttribute<AddressEntity, String> postalCode;
    public static volatile SingularAttribute<AddressEntity, String> addressLine1;
    public static volatile SingularAttribute<AddressEntity, String> addressLine2;
    public static volatile SingularAttribute<AddressEntity, Boolean> enabled;
    public static volatile SingularAttribute<AddressEntity, Long> addressId;

}