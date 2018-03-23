/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ItemEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidItemException;

/**
 *
 * @author User
 */
@Stateless
@Local(ItemSessionBeanLocal.class)
public class ItemSessionBean implements ItemSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public ItemEntity createItem(ItemEntity itemEntity){
        em.persist(itemEntity);
        em.flush();
        em.refresh(itemEntity);
        return itemEntity;
    }
    
    @Override
    public ItemEntity updateItem(ItemEntity itemEntity){
        em.merge(itemEntity);
        return itemEntity;
    }
    
    @Override
    public void deleteBid(Long itemId) throws InvalidItemException {
        ItemEntity itemEntity = em.find(ItemEntity.class, itemId);
        try {
            em.remove(itemEntity);
        } catch (NoResultException ex) {
            throw new InvalidItemException("Invalid itemEntity ID. Bid does not exists.");
        }
    }
    
    @Override
    public List<ItemEntity> retrieveItemList(){
        Query query = em.createQuery("SELECT s FROM ItemEntity s");
        return query.getResultList();
    }
    
    @Override
    public ItemEntity retrieveItemById(Long itemId) throws InvalidItemException{
        ItemEntity itemEntity = em.find(ItemEntity.class, itemId);
        if(itemEntity != null){
            return itemEntity;
        } 
        else{
            throw new InvalidItemException("Invalid itemEntity ID");
        }
    }
}
