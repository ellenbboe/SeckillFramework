package sf.controller;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import sf.entity.Goods;
import sf.entity.User;
import sf.model.SeckillGoodsModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.redis.StringRedisService;
import sf.result.Result;
import sf.service.GoodsService;
import sf.service.UserService;
import sf.util.DateUtil;
import sf.validator.LoginTokenValidator;
import sf.vo.GoodsDetailVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    StringRedisService stringRedisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/goods/list")
    @ResponseBody
    public String toLogin(HttpServletRequest request, HttpServletResponse response,Model model, @LoginTokenValidator User user){
        String html = stringRedisService.getString(RedisKey.getRedisKey(RedisKey.REDIS_PAGE_MODEL,RedisKey.REDIS_PAGENAME_SECKILLLIST,""));
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",userService.usertoModel(user));
        List<Goods>  goodsList = goodsService.GetGoodsList();
        model.addAttribute("goodsList",goodsList);
        //手动渲染
        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html=thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        stringRedisService.setString(RedisKey.getRedisKey(RedisKey.REDIS_PAGE_MODEL,RedisKey.REDIS_PAGENAME_SECKILLLIST,""),html,RedisKey.REDIS_LOGIN_PAGE_EXPICETIME);
        return html;
    }

//    @RequestMapping("/goods_detail/goodsId/{id}")
//    public String toDetail(@PathVariable("id") int id,Model model,@LoginTokenValidator User user)
//    {
//        model.addAttribute("user",userService.usertoModel(user));
//        SeckillGoodsModel seckillGoodsModel = goodsService.GoodsToModel(goodsService.getGoodsById(id));
//        long remainSeconds = seckillGoodsModel.getGoodsStock()>0? DateUtil.secoundToSeckill(seckillGoodsModel.getSeckillStarttime(),seckillGoodsModel.getSeckillEndtime()):-1;
//        model.addAttribute("goods",seckillGoodsModel);
//        model.addAttribute("remainSeconds",remainSeconds);
//        return "goods_detail";
//    }
    @RequestMapping("/goods_detail/goodsId/{id}")
    @ResponseBody
    public Result<GoodsDetailVo> toDetail(@PathVariable("id") int id, Model model, @LoginTokenValidator User user)
    {
        // TODO: 2020/1/12 放入缓存 
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
//        model.addAttribute("user",);
        goodsDetailVo.setUser(userService.usertoModel(user));
        SeckillGoodsModel seckillGoodsModel = goodsService.GoodsToModel(goodsService.getGoodsById(id));
        long remainSeconds = seckillGoodsModel.getGoodsStock()>0? DateUtil.secoundToSeckill(seckillGoodsModel.getSeckillStarttime(),seckillGoodsModel.getSeckillEndtime()):-1;
//        model.addAttribute("goods",seckillGoodsModel);
//        model.addAttribute("remainSeconds",remainSeconds);
        goodsDetailVo.setGoods(seckillGoodsModel);
        // TODO: 2020/1/12 还是会被放到缓存中,单位时间的并发会打到缓存中,设置1秒 
        goodsDetailVo.setRemainSeconds(remainSeconds);
        return Result.success(goodsDetailVo);
    }


}
