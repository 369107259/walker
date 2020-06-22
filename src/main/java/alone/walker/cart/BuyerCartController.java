/*
package alone.walker.cart;

import com.alibaba.fastjson.JSON;
import com.hwariot.api.dao.entity.Product;
import com.hwariot.api.service.IBuyerCartService;
import com.hwariot.api.service.IProductService;
import com.hwariot.base.client.bind.annotation.CurrentUser;
import com.hwariot.base.client.client.vo.UserVo;
import com.hwariot.base.client.exception.ClientException;
import com.hwariot.base.client.model.ResultJson;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

*/
/**
 * @Author: huangYong
 * @Date: 2020/6/21 13:52
 *//*

@RestController
public class BuyerCartController {

    @Autowired
    private IBuyerCartService buyerCartService;
    @Autowired
    private IProductService productService;
    */
/**
     * 设置cookie过期时间
     *//*

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;


    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", required = true, dataType = "Long", value = "商品ID"),
            @ApiImplicitParam(name = "quantity", required = true, dataType = "Integer", value = "商品数量"),
            @ApiImplicitParam(name = "userVo", required = true, dataType = "UserVo", value = "当前登录用户"),
    })
    @PostMapping("/cart/add/{productId}/")
    public ResultJson addCart(@PathVariable Long productId, @RequestParam(defaultValue = "1") Integer quantity,
                              HttpServletRequest request, HttpServletResponse response, @CurrentUser UserVo userVo) {
        //若登录,将购物车添加到redis中
        if (userVo != null) {
            buyerCartService.addCart(userVo.getId(), productId, quantity);
            return ResultJson.success();
        }
        //未登录使用cookie
        //从cookie中取购物车列表
        List<BuyerItem> cartListFromCookie = CartCookieUtil.getCartListFromCookie(request);
        //判断商品在商品列表
        boolean flag = false;
        for (BuyerItem buyerItem : cartListFromCookie) {
            //如果商品已存在  则数量相加
            if (buyerItem.getProductId().equals(productId)) {
                flag = true;
                //找到商品,数量相加 quantity和购物车中原来存在的数量
                buyerItem.setQuantity(buyerItem.getQuantity() + quantity);
                break;
            }
        }
        //如果不存在
        if (!flag) {
            Product product = productService.getByPrimaryKey(productId);
            if (ObjectUtils.isEmpty(product)) {
                throw new ClientException(200, "添加的商品不存在");
            }
            BuyerItem buyerItem = new BuyerItem();
            //设置商品数量
            buyerItem.setQuantity(quantity);
            buyerItem.setProduct(product);
            buyerItem.setProductId(product.getId());
            cartListFromCookie.add(buyerItem);
        }
        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JSON.toJSONString(cartListFromCookie), COOKIE_CART_EXPIRE, true);
        return ResultJson.success();
    }


    @ApiOperation("展示购物车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVo", required = true, dataType = "UserVo", value = "当前登录用户"),
    })
    @GetMapping("/cart/list/")
    public ResultJson showCartList(HttpServletRequest request, HttpServletResponse response, @CurrentUser UserVo userVo) {
        //从cookie中取购物车列表
        List<BuyerItem> buyerItems = CartCookieUtil.getCartListFromCookie(request);
        //判断用户是否登录
        if (userVo != null) {
            //如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
            buyerCartService.mergeCart(userVo.getId(), buyerItems);
            //将cookie中的购物车删除
            CookieUtils.deleteCookie(request, response, "cart");
            //从服务端取购物车列表
            buyerItems = buyerCartService.getCartList(userVo.getId());
        }
        return ResultJson.success(buyerItems);
    }


    @ApiOperation("更新购物车商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", required = true, dataType = "Long", value = "商品ID"),
            @ApiImplicitParam(name = "quantity", required = true, dataType = "Integer", value = "商品数量"),
            @ApiImplicitParam(name = "userVo", required = true, dataType = "UserVo", value = "当前登录用户"),
    })
    @PutMapping("/cart/update/num/{productId}/{quantity}/")
    public ResultJson updateCartNum(@PathVariable Long productId, @PathVariable Integer quantity
            , HttpServletRequest request, HttpServletResponse response, @CurrentUser UserVo userVo) {
        if (userVo != null) {
            buyerCartService.updateCartNum(userVo.getId(), productId, quantity);
            return ResultJson.success();
        }
        //从cookie中取购物车列表
        List<BuyerItem> cartListFromCookie = CartCookieUtil.getCartListFromCookie(request);
        for (BuyerItem buyerItem : cartListFromCookie) {
            //如果商品已存在  则数量相加
            if (buyerItem.getProductId().equals(productId)) {
                //找到商品,数量相加 quantity和购物车中原来存在的数量
                buyerItem.setQuantity(buyerItem.getQuantity() + quantity);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JSON.toJSONString(cartListFromCookie), COOKIE_CART_EXPIRE, true);
        return ResultJson.success();
    }

    @ApiOperation("删除购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", required = true, dataType = "Long", value = "商品ID"),
            @ApiImplicitParam(name = "userVo", required = true, dataType = "UserVo", value = "当前登录用户"),
    })
    @DeleteMapping("/cart/delete/{productId}/")
    public ResultJson deleteCartItem(@PathVariable Long productId, HttpServletRequest request,
                                     HttpServletResponse response, @CurrentUser UserVo userVo) {
        //用户已登录
        if (userVo != null) {
            buyerCartService.deleteCartItem(userVo.getId(), productId);
            return ResultJson.success();
        }
        //从cookie中取购物车列表
        List<BuyerItem> buyerItemList = CartCookieUtil.getCartListFromCookie(request);

        if (CollectionUtils.isNotEmpty(buyerItemList)) {
            for (int i = 0; i < buyerItemList.size(); i++) {
                if (productId.equals(buyerItemList.get(i).getProductId())) {
                    buyerItemList.remove(i);
                    break;
                }
            }
            //写入cookie
            CookieUtils.setCookie(request, response, "cart", JSON.toJSONString(buyerItemList), COOKIE_CART_EXPIRE, true);
        }
        return ResultJson.success();
    }


    @ApiOperation("清空购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVo", required = true, dataType = "UserVo", value = "当前登录用户"),
    })
    @DeleteMapping(value = "/cart/clear/")
    public ResultJson clear(HttpServletRequest request,
                            HttpServletResponse response, @CurrentUser UserVo userVo) {
        if (userVo != null) {
            buyerCartService.clearCartList(userVo.getId());
            return ResultJson.success();
        }
        CookieUtils.deleteCookie(request, response, "cart");
        return ResultJson.success();
    }

    @ApiOperation("获取购物车中选择的商品价格")
    @GetMapping("/cart/choose/price/")
    public ResultJson getChoosePrice(HttpServletRequest request, HttpServletResponse response,
                                     @CurrentUser UserVo userVo, @RequestBody List<Long> productIds) {
        //用户已登录
        if (userVo != null) {
            return ResultJson.success(buyerCartService.getChoosePrice(userVo.getId(),productIds));
        }
        //从cookie中取购物车列表
        List<BuyerItem> buyerItemList = CartCookieUtil.getCartListFromCookie(request);
        List<BuyerItem> buyerItems = buyerItemList.parallelStream().filter(buyerItem -> productIds.contains(buyerItem.getProductId())).collect(Collectors.toList());
        BigDecimal productPrice = CartCookieUtil.getProductPrice(buyerItems);
        return ResultJson.success(productPrice);
    }


    @ApiOperation("获取购物车中某个商品的规格,用于重选规格(产品扩展备用)")
    @GetMapping("/getProduct/{productId}/")
    public ResultJson getCartProduct(@PathVariable Long productId) {
        // TODO: 2020/6/22  根据产品获取产品规格信息返回
        return ResultJson.success(buyerCartService.getCartProduct(productId));
    }

    @ApiOperation("修改购物车中商品的规格(备用)")
    @PutMapping("/update/attr/")
    public ResultJson updateAttr(@RequestBody BuyerItem cartItem, @CurrentUser UserVo userVo) {
        //todo 修改购物车商品规格
        if (userVo != null) {
            buyerCartService.updateCartNum(userVo.getId(), cartItem.getProductId(), 1);
        }
        return ResultJson.success();
    }


}
*/
