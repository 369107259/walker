/*
package alone.walker.cart;

import com.alibaba.fastjson.JSON;
import com.hwariot.api.dao.entity.Product;
import com.hwariot.api.dao.repository.ProductRepository;
import com.hwariot.base.client.component.RedisComponent;
import com.hwariot.base.client.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

*/
/**
 * @Author: huangYong
 * @Date: 2020/6/21 14:32
 *//*

@Service
@Slf4j
public class BuyerCartServiceImpl implements IBuyerCartService {

    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private ProductRepository productRepository;
    private String REDIS_CART_PRE = "buyerCart";

    */
/***
     * 添加商品到购物车
     * @param id
     * @param productId
     * @param quantity
     *//*

    @Override
    public void addCart(Long id, Long productId, Integer quantity) {

        //向redis中添加购物车。
        //数据类型是hash key：用户id field：商品id value：商品信息
        //判断商品是否存在  库存是否充足
        Product product = productRepository.selectByPrimaryKey(productId);
        if (ObjectUtils.isEmpty(product)) {
            throw new ClientException(200, "添加的商品不存在");
        }
        Boolean exists = redisComponent.getHashOperation().hasKey(REDIS_CART_PRE + ":" + id, productId + "");
        if (exists) {
            String json = redisComponent.hGet(REDIS_CART_PRE + ":" + id, productId + "");
            BuyerItem buyerItem = JSON.parseObject(json, BuyerItem.class);
            Integer count = quantity + buyerItem.getQuantity();
            if (count > product.getStockNum()) {
                buyerItem.setIsHave(false);
            }
            buyerItem.setQuantity(count);
            //写回redis
            redisComponent.hPut(REDIS_CART_PRE + ":" + id, productId + "", JSON.toJSONString(buyerItem));
        } else {
            BuyerItem buyerItem = new BuyerItem();
            //设置购物车数据量
            buyerItem.setQuantity(quantity);
            buyerItem.setProductId(productId);
            buyerItem.setProduct(product);
            if (quantity > product.getStockNum()) {
                buyerItem.setIsHave(false);
            }
            //添加到购物车列表
            redisComponent.hPut(REDIS_CART_PRE + ":" + id, productId + "", JSON.toJSONString(buyerItem));
        }
    }

    */
/***
     * 合并购物车
     * @param userId
     * @param cartList
     *//*

    @Override
    public void mergeCart(Long userId, List<BuyerItem> cartList) {
        //遍历商品列表
        //把列表添加到购物车。
        //判断购物车中是否有此商品
        //如果有，数量相加
        //如果没有添加新的商品
        cartList.parallelStream().forEach(buyerItem -> this.addCart(userId, buyerItem.getProductId(), buyerItem.getQuantity()));
    }

    */
/***
     *获取用户购物车列表
     * @param id
     * @return
     *//*

    @Override
    public List<BuyerItem> getCartList(Long id) {
        //获取redis 中用户的购物车列表
        List<String> hValues = redisComponent.getHashOperation().values(REDIS_CART_PRE + ":" + id);
        List<BuyerItem> buyerItems = new ArrayList<>();
        for (String item : hValues) {
            //创建一个BuyerItem对象
            BuyerItem buyerItem = JSON.parseObject(item, BuyerItem.class);
            //添加到列表
            buyerItems.add(buyerItem);
        }
        return buyerItems;
    }

    */
/***
     * 更新购物车商品数量
     * @param id
     * @param productId
     * @param quantity
     *//*

    @Override
    public void updateCartNum(Long id, Long productId, Integer quantity) {
        String json = redisComponent.hGet(REDIS_CART_PRE + ":" + id, productId + "");
        if (StringUtils.isNotBlank(json)) {
            //判断商品是否存在  库存是否充足
            Product product = productRepository.selectByPrimaryKey(productId);
            if (ObjectUtils.isEmpty(product)) {
                throw new ClientException(200, "商品信息不存在");
            }
            BuyerItem buyerItem = JSON.parseObject(json, BuyerItem.class);
            Integer sum = buyerItem.getQuantity() + quantity;
            buyerItem.setQuantity(sum);
            buyerItem.setIsHave(sum <= product.getStockNum());
            redisComponent.hPut(REDIS_CART_PRE + ":" + id, productId + "", JSON.toJSONString(buyerItem));
        } else {
            log.info("商品不存在，无需更新");
        }
    }

    */
/***
     * 删除购物车商品
     * @param id
     * @param productId
     *//*

    @Override
    public void deleteCartItem(Long id, Long productId) {
        redisComponent.hRemove(REDIS_CART_PRE + ":" + id, String.valueOf(productId));
        log.info("购物车商品删除成功，商品ID为：" + productId);
    }

    */
/***
     * 清空购物车
     * @param id
     *//*

    @Override
    public void clearCartList(Long id) {
        redisComponent.delKey(REDIS_CART_PRE + ":" + id);
        log.info("用户购物车信息已清空");
    }


    */
/***
     * 获取购物车中某个商品的规格
     * @param productId
     * @return
     *//*

    @Override
    public Object getCartProduct(Long productId) {
        return null;
    }

    */
/***
     * 获取购物车选择的商品价格
     * @param userId
     * @param productIds
     * @return
     *//*

    @Override
    public BigDecimal getChoosePrice(Long userId, List<Long> productIds) {
        List<String> productIdsStr = productIds.parallelStream().map(Object::toString).collect(Collectors.toList());
        List<String> buyerCart = redisComponent.hGet(REDIS_CART_PRE + ":" + userId, productIdsStr);
        List<BuyerItem> buyerItems = new ArrayList<>();
        buyerCart.parallelStream().forEach(cart->{
            BuyerItem buyerItem = JSON.parseObject(cart, BuyerItem.class);
            buyerItems.add(buyerItem);
        });
        return CartCookieUtil.getProductPrice(buyerItems);
    }
}
*/
