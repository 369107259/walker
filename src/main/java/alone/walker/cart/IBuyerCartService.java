/*
package alone.walker.cart;

import java.math.BigDecimal;
import java.util.List;

*/
/**
 * @Author: huangYong
 * @Date: 2020/6/21 14:32
 *//*

public interface IBuyerCartService {
    */
/***
     * 添加商品到购物车
     * @param id
     * @param itemId
     * @param quantity
     *//*

    void addCart(Long id, Long itemId, Integer quantity);


    */
/***
     * 合并购物车
     * @param userId
     * @param cartList
     *//*

    void mergeCart(Long userId, List<BuyerItem> cartList);

    */
/***
     *获取用户购物车列表
     * @param id
     * @return
     *//*

    List<BuyerItem> getCartList(Long id);

    */
/***
     * 更新购物车商品数量
     * @param id
     * @param itemId
     * @param quantity
     *//*

    void updateCartNum(Long id, Long itemId, Integer quantity);

    */
/***
     * 删除购物车商品
     * @param id
     * @param itemId
     *//*

    void deleteCartItem(Long id, Long itemId);

    */
/***
     * 清空购物车
     * @param id
     *//*

    void clearCartList(Long id);

    Object getCartProduct(Long productId);

    */
/***
     * 获取购物车选择的商品价格
     * @param userId
     * @param productIds
     * @return
     *//*

    BigDecimal getChoosePrice(Long userId, List<Long> productIds);
}
*/
