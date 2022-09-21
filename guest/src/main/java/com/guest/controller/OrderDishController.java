/*
package com.guest.controller;

import com.guest.pojo.dto.OrderDish;
import com.guest.pojo.po.Reserver;
import com.guest.pojo.vo.ReserverMenu;
import com.guest.pojo.vo.Response;
import com.guest.pojo.vo.ResponseMsg;
import com.guest.service.BackgroundService;
import com.guest.service.ReserverMenuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * @author xinyuan
 * @Version 1.0
 * @date 2022/9/21  上午 10:48
 **//*

@RestController
@Api(tags = "点菜和结账")
public class OrderDishController {


    @Autowired
    BackgroundService backgroundService;

    @Autowired
    ReserverMenuService  reserverMenuService;


    @PostMapping("/orderDish")
    @ApiOperation(value="点菜")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填后台管理员的token",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据"),
            @ApiResponse(code=40000,message="请求失败")
    })
    public Response orderDish(@RequestBody OrderDish orderDish, HttpServletRequest request) {
        String num = (String) request.getAttribute("num");
        // dto -> dm
        List<String> dish_ids = orderDish.getDish_id();
        List<ReserverMenu> reserverMenus = new ArrayList<>();
        for (String id : dish_ids) {
            ReserverMenu reserverMenu = new ReserverMenu();
            reserverMenu.setReserver_name(orderDish.getReserver_name());
            reserverMenu.setReserver_tel(orderDish.getReserver_tel());
            reserverMenu.setState(0);
            reserverMenu.setDish_id(Integer.parseInt(id));
            // add
            reserverMenus.add(reserverMenu);
        }
        if (backgroundService.getById(num) != null) {
            int i = reserverMenuService.addDishes(reserverMenus);
            if (i > 0)
                return Response.success();
            return new Response(ResponseMsg.FAIL);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }


    @PostMapping("/finishBill")
    @ApiOperation(value="结账")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填后台管理员的token",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据"),
            @ApiResponse(code=40000,message="请求失败")
    })
    public Response finishBill(@RequestBody String tel, HttpServletRequest request) {
        String num = (String) request.getAttribute("num");
        if (backgroundService.getById(num) != null) {
            int i = reserverMenuService.finishBill(tel);
            if (i > 0)
                return Response.success();
            return new Response(ResponseMsg.FAIL);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }
}
*/
