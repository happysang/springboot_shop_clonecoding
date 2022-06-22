package com.shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.Entity.Item;
import com.shop.Entity.QItem;
import com.shop.constant.ItemSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("item save test")
    public void crateItemTest(){
        Item item = new Item();
        item.setItemNm("test item");
        item.setPrice(10000);
        item.setItemDetail("test item detail information");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item saveItem = itemRepository.save(item);
        System.out.println("saveItem.toString() = " + saveItem.toString());
    }

    public void createItemList(){
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setItemNm("test item"+i);
            item.setPrice(10000+i);
            item.setItemDetail("test item detail information"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("ItemList read test")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("test item1");
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("ItemNm, ItemDetail read Test")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("test item1", "test item detail information5");
        for (Item item : itemList){
            System.out.println("item.toString() = " + item.toString());
        }
    }

    @Test
    @DisplayName("price lessthan test")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item:itemList){
            System.out.println("item.toString() = " + item.toString());
        }
    }

    @Test
    @DisplayName("price lessthan test")
    public void findByPriceLessThanTestDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item:itemList){
            System.out.println("item.toString() = " + item.toString());
        }
    }
    
    @Test
    @DisplayName("@Quary read itemList test")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("detail information");
        for (Item item:itemList){
            System.out.println("item.toString() = " + item.toString());
        }
    }
    
    @Test
    @DisplayName("NativeQuary read itemList test")
    public void findByItemDetailByNative(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("detail information");
        for (Item item : itemList){
            System.out.println("item.toString() = " + item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl read test1")
    public void queryDslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"detail information"+"%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for (Item item : itemList){
            System.out.println("item.toString() = " + item.toString());
        }
    }
}