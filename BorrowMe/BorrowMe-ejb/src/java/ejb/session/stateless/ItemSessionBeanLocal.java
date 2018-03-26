/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ItemEntity;
import java.util.List;
import util.exception.InvalidItemException;

public interface ItemSessionBeanLocal {
    public ItemEntity createItem(ItemEntity itemEntity);

    public ItemEntity updateItem(ItemEntity itemEntity) throws InvalidItemException;

    public void deleteItem(Long itemId) throws InvalidItemException;

    public List<ItemEntity> retrieveItemList();

    public ItemEntity retrieveItemById(Long itemId) throws InvalidItemException;
}
