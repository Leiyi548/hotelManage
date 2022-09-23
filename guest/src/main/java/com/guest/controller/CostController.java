package com.guest.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guest.pojo.po.Cost;
import com.guest.pojo.po.CostType;
import com.guest.pojo.vo.*;
import com.guest.service.*;
import com.guest.utils.JwtUtill;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雪萍
 * @since 2020-11-27
 */
@CrossOrigin
@Transactional
@RestController
@Api(tags={"消费记录"})
public class CostController {
    @Autowired
    private CostService costService;
    @Autowired
    private FrontService frontService;
    @Autowired
    private CostTypeService costTypeService;
    @Autowired
    private JwtUtill jwtUtill;

    public static final int PAGE_SIZE = 6;

    @PostMapping("/addCost")
    @ApiOperation(value="添加/修改消费记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填前台管理员的token",required=true),
            @ApiImplicitParam(name="id",value="消费记录的id,如果是添加记录直接填0，如果是修改就填修改的id",required=true),
            @ApiImplicitParam(name="costTypeId",value="消费类型的id",required=true),
            @ApiImplicitParam(name="roomId",value="房间的id",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response addCost(HttpServletRequest request, int id,int costTypeId,String roomId){
        String num = (String) request.getAttribute("num");
//        if(frontService.getById(num) != null){
        Cost cost = new Cost(id,costTypeId,roomId,1,0);
        costService.saveOrUpdate(cost);
        String token = jwtUtill.updateJwt(num);
        return (new Response()).success(token);
//        }
//        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }



    @DeleteMapping("/deleteCost")
    @ApiOperation(value="删除消费记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填前台管理员的token",required=true),
            @ApiImplicitParam(name="id",value="消费记录的id",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response deleteCost(HttpServletRequest request,Integer id){
        String num = (String) request.getAttribute("num");
//        if(frontService.getById(num) != null){
        costService.removeById(id);
        String token = jwtUtill.updateJwt(num);
        return (new Response()).success(token);
//        }
//        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

    @GetMapping("/getCostById")
    @ApiOperation(value="通过id获取消费记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填前台管理员的token",required=true),
            @ApiImplicitParam(name="id",value="消费记录的id",required=true)
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40002,message="数据不存在"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response getCostById(HttpServletRequest request,Integer id){
        String num = (String) request.getAttribute("num");
        if(frontService.getById(num) != null || frontService.getById(num) != null){
            Cost cost = costService.getById(id);
            if(cost != null ){
                Map<String,Object> resultMap = new HashMap<>();
                String token = jwtUtill.updateJwt(num);
                resultMap.put("cost",cost);
                resultMap.put("token",token);
                return (new Response()).success(resultMap);
            }
            return new Response(ResponseMsg.NO_TARGET);
        }
        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }

    /**
     * 根据roomId查看全部消费记录，计算已结算和未结算的金额和总金额
     * @param request
     * @return
     */
    @PostMapping("/getCostByRoomId")
    @ApiOperation(value="通过roomId查看当前入住客户的消费记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填前台管理员的token",required=true),
            @ApiImplicitParam(name="roomId",value="分配的房间的id",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40002,message="数据不存在"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response getCostByRoomId(HttpServletRequest request,String roomId){
        String num = (String) request.getAttribute("num");
//        if( frontService.getById(num) != null){
        List<Cost> costs = costService.getCostByRoomId(roomId);
        List<Cost> costs1 = new ArrayList<>();
        if(costs != null && costs.size()>0){
            double hasSettle = 0;
            double toll = 0;
            for(Cost cost:costs){
                if(cost.getState() != 11){
                    toll += costTypeService.getById(cost.getCostTypeId()).getMoney()*cost.getNum();
                    if(cost.getState() == 1){
                        hasSettle+=costTypeService.getById(cost.getCostTypeId()).getMoney()*cost.getNum();
                    }
                    costs1.add(cost);
                }
            }
            double needSettle = toll - hasSettle;
            Map<String,Object> resultMap = new HashMap<>();
            String token = jwtUtill.updateJwt(num);
            resultMap.put("costs1",costs1);
            resultMap.put("toll",toll);
            resultMap.put("hasSettle",hasSettle);
            resultMap.put("needSettle",needSettle);
            resultMap.put("token",token);
            return (new Response()).success(resultMap);
        }
        return new Response(ResponseMsg.NO_TARGET);
//        }
//        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }
    /**
     * 根据roomId结算
     * @param request
     * @return
     */
    @PostMapping("/settleCostByRoomId")
    @ApiOperation(value="根据roomId结算")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填前台管理员的token",required=true),
            @ApiImplicitParam(name="roomId",value="房间的id",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response settleCostByRoomId(HttpServletRequest request,String roomId){
        String num = (String) request.getAttribute("num");
//        if(frontService.getById(num) != null){
        //将未结算的设置成已结算的
        costService.settleCostByRoomId(roomId);
        String token = jwtUtill.updateJwt(num);
        return (new Response()).success(token);
//        }
//        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }


    /**
     * 结算单条消费记录
     * @param request
     * @return
     */
    @PostMapping("/settleCostById")
    @ApiOperation(value="结算单条消费记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",value="填前台管理员的token",required=true),
            @ApiImplicitParam(name="id",value="消费记录的id",required=true),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40002,message="数据不存在"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response settleCostById(HttpServletRequest request,int id){
        String num = (String) request.getAttribute("num");
//        if(frontService.getById(num) != null){
        Cost cost = costService.getById(id);
        if(cost != null){
            cost.setState(1);
            costService.saveOrUpdate(cost);
            String token = jwtUtill.updateJwt(num);
            return (new Response()).success(token);

        }
        return new Response(ResponseMsg.NO_TARGET);
//        }
//        return new Response(ResponseMsg.ILLEGAL_OPERATION);
    }



    /*
           输出消费报表
     */
    @RequestMapping("/financialStatement")
    @ApiOperation("消费报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="页数",required=false),
    })
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40002,message="数据不存在"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response financialStatement(Integer page) {
        page = page == null ? 1 : page;
        List<MoneyTable> financialStatements = costService.getFinancialStatement();
        // 计算总金额
        Double total = 0D;
        for (MoneyTable financialStatement : financialStatements) {
            total+=financialStatement.getMoney() * financialStatement.getNum();
        }
        PageInfo pageInfo = pageForData(costService,page);
        Map resultMap = new HashMap();
        resultMap.put("total",total);
        resultMap.put("pageInfo",pageInfo);
        return new Response().success(resultMap);
    }

    public static PageInfo pageForData(CostService costService,Integer page) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<MoneyTable> financialStatements = costService.getFinancialStatement();
        PageInfo<MoneyTable> moneyTablePageInfo = new PageInfo<>(financialStatements);
        return moneyTablePageInfo;
    }




    /*
           输出财务报表
     */
    @RequestMapping("/financialStatement2")
    @ApiOperation("财务报表")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功"),
            @ApiResponse(code=40002,message="数据不存在"),
            @ApiResponse(code=40104,message="非法操作, 试图操作不属于自己的数据")
    })
    public Response financialStatement2() {
        List<MoneyTable> financialStatements = costService.getFinancialStatement();

        // 记录月vo
        List<FinancialStatement> mfs = new ArrayList<>();
        // 记录年total
        Map<Integer,Double> yearMap = new HashMap<>();

        boolean mflag ;
        for (MoneyTable financialStatement : financialStatements) {
            mflag = false;
            // 转换为日历类型 防止Date废弃方法(getYear getMonth)错误 +1900
            Calendar  calendar = Calendar.getInstance();
            calendar.setTime(financialStatement.getTime());
            // 算月金额和年金额
            for (FinancialStatement f : mfs) {

                if(f.getYear() ==  calendar.get(Calendar.YEAR) && f.getMonth() == calendar.get(Calendar.MONTH)){
                    mflag = true;
                    f.setMoneyTotal(f.getMoneyTotal()+(financialStatement.getMoney() * financialStatement.getNum()));
                    yearMap.put(f.getYear(),yearMap.get(calendar.get(Calendar.YEAR)) + (financialStatement.getMoney() * financialStatement.getNum()) );
                }
            }
            if(!mflag) {
                mfs.add(new FinancialStatement(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        (financialStatement.getMoney() * financialStatement.getNum())));

                if (!yearMap.containsKey(calendar.get(Calendar.YEAR))){
                    yearMap.put(calendar.get(Calendar.YEAR),
                            (financialStatement.getMoney() * financialStatement.getNum()));
                }else  {
                    yearMap.put(calendar.get(Calendar.YEAR),yearMap.get(calendar.get(Calendar.YEAR)) + (financialStatement.getMoney() * financialStatement.getNum()));
                }

            }
        }


        Map resultMap = new HashMap();
        resultMap.put("mvo",mfs);
        resultMap.put("ymap",yearMap);
        return new Response().success(resultMap);
    }
}


