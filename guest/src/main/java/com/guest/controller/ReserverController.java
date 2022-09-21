package com.guest.controller;

import com.baomidou.mybatisplus.annotation.TableId;
import com.guest.pojo.po.Background;
import com.guest.pojo.po.Reserver;
import com.guest.pojo.vo.Response;
import com.guest.pojo.vo.ResponseMsg;
import com.guest.service.BackgroundService;
import com.guest.service.ReserverService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xinyuan
 * @Version 1.0
 * @date 2022/9/21  上午 9:58
 **/
@RestController
@Api(tags = {"餐桌预定人模块"})
public class ReserverController {

    @Autowired
    ReserverService reserverService;

    @Autowired
    BackgroundService backgroundService;



    // 存预定人信息 防止前端无法存储
    public static Reserver reserver;


    @PostMapping("/addReserver")
    @ApiOperation(value="添加餐桌预定人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填后台管理员的token",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据"),
            @ApiResponse(code=40000,message="请求失败")
    })
    public Response addReserver(@RequestBody Reserver reserver, HttpServletRequest request) {
         String num = (String) request.getAttribute("num");
        if (backgroundService.getById(num) != null) {
            int i = reserverService.addReserver(reserver);
            this.reserver = reserver;
            if (i > 0)
                return Response.success();
            return new Response(ResponseMsg.FAIL);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

}
