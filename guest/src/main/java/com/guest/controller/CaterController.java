package com.guest.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guest.mapper.ReserverMenuMapper;
import com.guest.pojo.po.Reserver;
import com.guest.pojo.vo.ReserverMenu;
import com.guest.pojo.vo.ReserverOverMenu;
import com.guest.pojo.vo.Response;
import com.guest.pojo.vo.ResponseMsg;
import com.guest.service.*;
import com.guest.service.serviceImpl.ReserverServiceImpl;
import com.guest.utils.JwtUtill;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
            List<Reserver> reservers = reserverService.list();
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


    @PutMapping("/updateReserver")
    @ApiOperation(value="修改客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填后台管理员的token",required=true),
            @ApiImplicitParam(name="reserverName",value="名字",required=true),
            @ApiImplicitParam(name="reserverTel",value="电话号码",required=true),
            @ApiImplicitParam(name="eaterNum",value="人数",required=true),
            @ApiImplicitParam(name="deskNum",value="桌号",required=true)
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public  Response updateReserver(HttpServletRequest request,String reserverName,
                                    String reserverTel,int eaterNum,int deskNum,Double money,int state){
        String num = (String) request.getAttribute("num");
        Reserver reserver=reserverService.getReserverByName(reserverName);
        if(backgroundService.getById(num) != null){
            reserver.setReserverTel(reserverTel);
            reserver.setDeskNum(deskNum);
            reserver.setEaterNum(eaterNum);
            reserver.setMoney(money);
            reserver.setState(state);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("reserver_name",reserverName);
            reserverService.update(reserver,queryWrapper);
            String token=jwtUtill.updateJwt(num);
            return (new Response()).success(token);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

    @GetMapping("/fuzzyReserver")
    @ApiOperation(value="模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "填前台管理员的token", required = true),
            @ApiImplicitParam(name = "reserverName", value = "名字", required = false)
            })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
            })
            public  Response fuzzyReserver(HttpServletRequest request,String reserverName){
            String num = (String) request.getAttribute("num");
            if(backgroundService.getById(num) != null){
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.like("reserver_name",reserverName);
                List<Reserver> list = reserverService.list(queryWrapper);
                String token=jwtUtill.updateJwt(num);
                Map<String,Object> result=new HashMap<>();
                result.put("list",list);
                result.put("token",token);
            return (new Response()).success(result);
}
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
                }



    @GetMapping("/deleteReserver")
    @ApiOperation(value="删除餐桌预定人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "填前台管理员的token", required = true),
            @ApiImplicitParam(name = "reserverName", value = "名字", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据"),
            @ApiResponse(code=40000,message="操作失败")
    })
    public  Response deleteReserver(HttpServletRequest request,String reserverName){
        String num = (String) request.getAttribute("num");
        if(backgroundService.getById(num) != null){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("reserver_name",reserverName);
            if (reserverService.remove(queryWrapper)){
            String token=jwtUtill.updateJwt(num);
            return (new Response()).success(token);
            }
            return new Response(ResponseMsg.FAIL);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

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
    public Response addReserver(Reserver reserver, HttpServletRequest request) {
        String num = (String) request.getAttribute("num");
        if (backgroundService.getById(num) != null) {
            reserver.setMoney(0D);
            reserver.setState(0);
            int i = reserverService.addReserver(reserver);
            String token=jwtUtill.updateJwt(num);
            if (i > 0)
                return (new Response()).success(token);
            return new Response(ResponseMsg.FAIL);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

    @PostMapping("/orderMenu")
    @Transactional
    @ApiOperation(value="点菜+结账")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填后台管理员的token",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据"),
            @ApiResponse(code=40000,message="请求失败")
    })
    public Response orderMenu(String reserverName,String reserverTel,String dishName,
                              Integer num,Double price, HttpServletRequest request) {
        String jwtF = (String) request.getAttribute("num");
        if (backgroundService.getById(jwtF) != null) {
            Double total = num * price;
            ReserverMenu reserverMenu = new ReserverMenu(null,
                    reserverName, reserverTel, dishName, num, price);
            // 点菜并结账
            boolean flag = reserverMenuService.save(reserverMenu);
           // 更新reserver state
            int i = reserverService.updateReserverState(reserverName, reserverTel);
            String token=jwtUtill.updateJwt(jwtF);
            if ( flag && i >0)
                return new Response().success(token);
            return new Response(ResponseMsg.FAIL);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }



    @GetMapping("/seekReserverDetails")
    @ApiOperation(value="显示预定人所点过的菜")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "填后台管理员的token", required = true),
            @ApiImplicitParam(name = "reserverName", value = "名字", required = true),
            @ApiImplicitParam(name = "reserverTel", value = "电话", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据"),
            @ApiResponse(code=40000,message="操作失败")
    })
    public  Response seekReserverDetails(HttpServletRequest request,String reserverName,
                                    String reserverTel){
        String num = (String) request.getAttribute("num");
        if(backgroundService.getById(num) != null){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("reserver_name",reserverName);
            queryWrapper.eq("reserver_tel",reserverTel);
            List<ReserverMenu> list = reserverMenuService.list(queryWrapper);
            // dm -> vo
            List<ReserverOverMenu> ReserverOverMenus = setCommonFiled(list);
            String token=jwtUtill.updateJwt(num);
            Map<String,Object> result=new HashMap<>();
            result.put("list",ReserverOverMenus);
            result.put("token",token);
            return new Response().success(result);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

    public static List<ReserverOverMenu> setCommonFiled(List<ReserverMenu> reserverMenus){
        List<ReserverOverMenu> result = new ArrayList<>();
        for (ReserverMenu reserverMenu : reserverMenus) {
            ReserverOverMenu reserverOverMenu = new ReserverOverMenu(reserverMenu.getDishName(), reserverMenu.getNum(), reserverMenu.getPrice());
            result.add(reserverOverMenu);
        }
        return result;
    }



}
