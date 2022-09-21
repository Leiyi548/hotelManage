package com.guest.controller;

import com.guest.mapper.ReserverMenuMapper;
import com.guest.pojo.po.Reserver;
import com.guest.pojo.vo.Response;
import com.guest.pojo.vo.ResponseMsg;
import com.guest.service.*;
import com.guest.service.serviceImpl.ReserverServiceImpl;
import com.guest.utils.JwtUtill;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DRW
 * @vsrsion 1.0
 */
@CrossOrigin
@Transactional
@RestController
@Api(tags = "餐饮消费管理")
public class CaterController {
    @Autowired
    private JwtUtill jwtUtill;
    @Autowired
    private ReserverService reserverService;
    @Autowired
    ReserverMenuService reserverMenuService;
    @Autowired
    private BackgroundService backgroundService;
    @Autowired
    private FrontService frontService;
    @GetMapping("getAllReservers")
    @ApiOperation(value="获取所有的消费者")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="后/前台管理员的token",required=true)
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40002,message="数据不存在"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response getAllReservers(HttpServletRequest request){
        String num=(String) request.getAttribute("num");
        if(backgroundService.getById(num) != null || frontService.getById(num) != null){
            List<Reserver> reservers=reserverService.list();
            if(reservers!=null && reservers.size()>0){
                Map<String,Object> resultMap=new HashMap<>();
                resultMap.put("reservers",reservers);
                String token =jwtUtill.updateJwt("num");
                resultMap.put("token",token);
                return (new Response()).success(resultMap);
          }
            return new Response(ResponseMsg.NO_TARGET);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }
}
